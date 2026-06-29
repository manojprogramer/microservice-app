import { useEffect, useState } from "react";
import "./CheckoutPage.css";
import { useNavigate } from "react-router-dom";

function CheckoutPage() {

    const navigate = useNavigate("")
    const [cartItems, setCartItems] = useState([]);
    const [fullName, setFullName] = useState("")
    const [phoneNumber, setPhoneNumber] = useState("")
    const [address, setAddress] = useState("")
    const [city, setCity] = useState("")
    const [pincode, setPincode] = useState("")
    const [paymentMethod, setPaymentMethod] = useState("")
    const [totalItems,setTotalItems] = useState();

    // FETCH ORDERS
    const handleOrders = async () => {

        try {

            const token = localStorage.getItem("token");

            const response = await fetch("http://localhost:8083/cart/getcart", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });

            const data = await response.json();

            setCartItems(data);
            setTotalItems(data.totalItems);
            console.log(data);

        } catch (error) {
            console.log(error);
        }
    };

    const placeOrder = async () => {

        if(!fullName || !phoneNumber || !address || !city || !pincode || !paymentMethod){
            alert("please fill all the fileds ")
            return
        }
        const userData = {
            fullName: fullName,
            phoneNumber: phoneNumber,
            address: address,
            city: city,
            pincode: pincode,
            paymentMethod: paymentMethod,
            totalAmount: totalAmount
        }
        try {
            const token = localStorage.getItem("token")
            const response = await fetch("http://localhost:8084/order", {
                method: "POST",
                headers: {
                    "Content-Type" :"application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(userData)
            });
            if (!response.ok) {
                alert("Someting went wrong")
            }
            const data = await response.json();
            console.log(data)
        }
        catch (error) {
            console.log(error)
        }
        navigate("/")
    }

    // CALL API WHEN PAGE LOADS
    useEffect(() => {
        handleOrders();
    }, []);

    // TOTAL PRICE
    const totalAmount = cartItems.reduce(
        (total, item) => total + item.productPrice * item.quantity,
        0
    );
    return (

        <div className="checkout-page">

            <div className="checkout-container">

                {/* LEFT SECTION */}
                <div className="checkout-left">

                    <h1>Checkout</h1>

                    <div className="form-section">

                        <h2>Shipping Address</h2>

                        <div className="input-group">
                            <input type="text" placeholder="Full Name" onChange={(e) => setFullName(e.target.value)} />
                            <input type="text" placeholder="Phone Number" onChange={(e) => setPhoneNumber(e.target.value)} />
                        </div>

                        <input
                            type="text"
                            placeholder="Address"
                            className="full-input"
                            onChange={(e) => setAddress(e.target.value)}
                        />

                        <div className="input-group">
                            <input type="text" placeholder="City" onChange={(e) => setCity(e.target.value)} />
                            <input type="text" placeholder="Pincode" onChange={(e) => setPincode(e.target.value)} />
                        </div>

                    </div>

                    <div className="form-section">

                        <h2>Payment Method</h2>

                        <div className="payment-options">

                            <label>
                                <input type="radio" name="payment" value="COD" onChange={(e) => setPaymentMethod(e.target.value)} />
                                Cash On Delivery
                            </label>

                            <label>
                                <input type="radio" name="payment" value = "UPI" onChange={(e) => setPaymentMethod(e.target.value)} />
                                UPI
                            </label>

                            <label>
                                <input type="radio" name="payment" value = "credit Card" onChange={(e) => setPaymentMethod(e.target.value)} />
                                Credit / Debit Card
                            </label>

                        </div>

                    </div>

                </div>

                {/* RIGHT SECTION */}
                <div className="checkout-right">

                    <h2>Order Summary</h2>

                    {
                        cartItems.map((item) => (

                            <div className="checkout-item" key={item.id}>

                                <img
                                    src={`http://localhost:8083${item.imageUrl}`}
                                    alt={item.productName}
                                />

                                <div>
                                    <h3>{item.productName}</h3>
                                    <p>
                                        Quantity : {item.quantity}
                                    </p>
                                </div>

                                <h4>
                                    ₹ {item.productPrice}
                                </h4>

                            </div>
                        ))
                    }

                    <div className="price-details">

                        <div>
                            <span>Items Total</span>
                            <span>₹ {totalItems}</span>
                        </div>

                        <div>
                            <span>Delivery</span>
                            <span className="free">Free</span>
                        </div>

                        <div className="final-total">
                            <span>Total Amount</span>
                            <span>₹ {totalAmount}</span>
                        </div>

                    </div>

                    <button
                        className="place-order-btn"
                        onClick={placeOrder}
                    >
                        Place Order
                    </button>

                </div>

            </div>

        </div>
    );
}

export default CheckoutPage;