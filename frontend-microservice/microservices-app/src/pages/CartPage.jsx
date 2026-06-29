import { useEffect, useState } from "react";
import "./CartPage.css";
import { useNavigate } from "react-router-dom";

function CartPage() {

    const [cart, setCart] = useState([]);
    const navigate = useNavigate();
    const [message, setMessage] = useState("")

    const fetchCart = async () => {

        try {

            const token = localStorage.getItem("token");

            const response = await fetch(
                "http://localhost:8083/cart/getcart",
                {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }
            );
            
            const data = await response.json();
            console.log(data)
            setCart(data);

        } catch (error) {

            console.log(error);
        }
    };
    const handleLogin = async () => {
        const token = localStorage.getItem("token")
        const response = await fetch("http://localhost:8081/user/check", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        return response.ok;

    }
    useEffect(() => {
        const checkLogin = async () => {
            const isLogin = await handleLogin();
            if (isLogin)
                fetchCart();
            else {
                navigate("/login", {
                    state: {
                        from: "/cart"
                    }
                })
            }
        }
        checkLogin();

    }, []);

    const totalPrice = cart.reduce(
        (total, item) => total + (item.productPrice * item.quantity),

        0);
    const totalItems = cart.reduce(
    (total, item) => total + item.quantity,
    0
    );

    
    const increaseProductCount = async (id) => {
        const token = localStorage.getItem("token");
        try {
            console.log(id)
            const response = await fetch(`http://localhost:8083/cart/increment/${id}`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            
            fetchCart();
        }
        catch (error) {
            console.log("error")
        }
    }
    const decrementProductCount = async (id) => {
        const token = localStorage.getItem("token")
        try {
            const response = await fetch(`http://localhost:8083/cart/decrement/${id}`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            fetchCart();
        }
        catch (error) {
            console.log(error)
        }
    }
    const deleteProduct = async (id) => {
        const token = localStorage.getItem("token")
        try {
            const response = await fetch(`http://localhost:8083/cart/delete/${id}`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            if (!response.ok)
                throw new Error("Deletion Failed")
            fetchCart();
        }
        catch (error) {
            console.log("URL Not Found");
        }
    }
    const checkCart = async () => {
        if (cart.length == 0) {
            setMessage("Cart is empty add products")
            setTimeout(() => {
                navigate("/")
            }, 2000)
        }
        else {
            navigate("/checkout")
        }
    }

    return (

        <div className="cart-page">

            <div className="cart-container">

                {/* Left Side */}
                <div className="cart-items-section">

                    <h1 className="cart-title">
                        My Cart
                    </h1>

                    {
                        cart.map((item) => (

                            <div className="cart-card" key={item.id}>

                                <img
                                    src={`http://localhost:8083${item.imageUrl}`}
                                    alt={item.productName}
                                    className="cart-image"
                                />

                                <div className="cart-details">

                                    <h2>
                                        {item.productName}
                                    </h2>

                                    <p className="cart-description">
                                        Premium Quality Product
                                    </p>

                                    <h3>
                                        ₹ {item.productPrice}
                                    </h3>

                                </div>

                                <div className="cart-actions">

                                    <div className="quantity-box">

                                        <button onClick={() => { decrementProductCount(item.productId)}}>
                                            -
                                        </button>

                                        <span>
                                            {item.quantity}
                                        </span>

                                        <button onClick={() => { increaseProductCount(item.productId) }}>
                                            +
                                        </button>

                                    </div>

                                    <button className="remove-btn" onClick={() => { deleteProduct(item.productId) }}>
                                        Remove
                                    </button>

                                </div>

                            </div>
                        ))
                    }

                </div>
                {
                    message && (
                        <div className="cart-alert">
                            {message}
                        </div>
                    )
                }

                {/* Right Side */}
                <div className="summary-section">

                    <h2>
                        Order Summary
                    </h2>

                    <div className="summary-row">

                        <span>Total Items</span>

                        <span>
                            {totalItems}
                        </span>

                    </div>

                    <div className="summary-row">

                        <span>Delivery</span>

                        <span className="free">
                            Free
                        </span>

                    </div>

                    <div className="summary-total">

                        <span>Total</span>

                        <span>
                            ₹ {totalPrice}
                        </span>

                    </div>

                    <button className="checkout-btn" onClick={checkCart}>
                        Proceed To Checkout
                    </button>

                </div>

            </div>

        </div>
    );
}

export default CartPage;