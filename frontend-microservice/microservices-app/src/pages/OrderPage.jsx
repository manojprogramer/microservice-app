import { useEffect, useState } from "react";
import "./OrderPage.css";
import { useNavigate } from "react-router-dom";

function OrderPage() {

    const [orders, setOrders] = useState([]);
    const navigate = useNavigate("")
    useEffect(() => {
        const checkUser = async ()=>{
            const isLoggedIn = await handleLogin();
            if(!isLoggedIn) {
                navigate("/login")
                return
            }
            fetchOrders();
        }
        checkUser();

    }, []);
    const handleLogin = async () => {
        try {
            const token = localStorage.getItem("token")
            const response = await fetch("http://localhost:8081/user/check",{
                method : "GET",
                headers :{
                    "Authorization" : `Bearer ${token}`
                }
            })
            return response.ok
        }
        catch(error) {
            console.log(error)
        }
    }
    const fetchOrders = async () => {

        try {

            const token = localStorage.getItem("token");

            const response = await fetch("http://localhost:8084/getorders", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });

            const data = await response.json();

            setOrders(data);

        } catch (error) {
            console.log(error);
        }
    };
    const cancelOrder = async (productId) => {
        const token = localStorage.getItem("token")
        try {
            const response = await fetch(`http://localhost:8084/cancelorder/${productId}`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                }
            })
            if (!response.ok) {
                alert("Something went wrong")
            }
        }
        catch (error) {
            console.log(error)
        }
        fetchOrders();
    }

    return (

        <div className="orders-page">

            <div className="orders-container">

                <h1 className="orders-title">
                    My Orders
                </h1>

                {
                    orders.length === 0 ? (

                        <div className="empty-orders">

                            <h2>No Orders Yet</h2>

                            <p>
                                Your placed orders will appear here.
                            </p>

                        </div>

                    ) : (

                        orders.map((order) => (

                            <div className="order-card" key={order.id}>

                                <div className="order-top">

                                    <div>
                                        <h2>
                                            Order #{order.id}
                                        </h2>

                                        <p>
                                            Placed Successfully
                                        </p>
                                    </div>

                                    <span className="order-status">
                                        {order.status || "Confirmed"}
                                    </span>

                                </div>

                                <div className="order-middle">

                                    <img
                                        src={`http://localhost:8084${order.imageUrl}`}
                                        alt={order.productName}
                                        className="order-image"
                                    />

                                    <div className="order-details">

                                        <h3>
                                            {order.productName}
                                        </h3>

                                        <p>
                                            Quantity : {order.quantity}
                                        </p>

                                        <p>
                                            Payment : {order.payment}
                                        </p>

                                        <p>
                                            Delivery Address : {order.address}
                                        </p>

                                    </div>

                                    <div className="order-price-section">

                                        <h2>
                                            ₹ {order.amount}
                                        </h2>

                                    </div>

                                </div>

                                <div className="order-bottom">

                                    <button className="track-btn">
                                        Track Order
                                    </button>

                                    <button className="invoice-btn" onClick={() => { cancelOrder(order.productId) }}>
                                        Cancel Order
                                    </button>

                                </div>

                            </div>
                        ))
                    )
                }

            </div>

        </div>
    );
}

export default OrderPage;
