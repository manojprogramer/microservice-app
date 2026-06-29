import { useEffect, useState } from "react";
import { resolvePath, useNavigate, useParams } from "react-router-dom";
import "./ProductDescriptionPage.css";

function ProductDescriptionPage() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [product, setProduct] = useState({});
    const [added, setAdded] = useState(false);
    useEffect(() => {

        const handleProduct = async () => {
            try {

                const response = await fetch(
                    `http://localhost:8082/products/getproducts/${id}`
                );
                const data = await response.json();

                setProduct(data);

                console.log(data);

            } catch (error) {

                console.log(error);
            }
        };

        handleProduct();

    }, [id]);
    const handleLogin = async () => {
        const token = localStorage.getItem("token")
        if (!token) return false;
        try {
            const response = await fetch("http://localhost:8081/user/check", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            console.log(response)
            if (response.ok) {
                return true;
            }
            else {
                localStorage.removeItem("token")
                return false;
            }
        }
        catch (error) {
            console.log(error);
            alert("user failed")
        }

    }
    const handleAddToCart = async () => {
        const isLoggedIn = await handleLogin();
        if (!isLoggedIn) {
            navigate("/login",{
                state:{
                    from :`/product/${id}`
                }
            })
            return
        }
        const token = localStorage.getItem("token")
        try {
            const response = await fetch(`http://localhost:8083/cart/addtocart/${id}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            })
            setAdded(true);
            setTimeout(() => {
                setAdded(false);
            }, 2000);
            const data = await response.text();
            console.log(data)
        }
        catch (error) {
            console.log(error)
            alert("Something went wrong")
            return false;
        }
    }

    return (

        <div className="product-page">

            {/* Left Section */}
            <div className="product-image-section">

                <img
                    src={`http://localhost:8082${product.imageUrl}`}
                    alt={product.productName}
                    className="product-image"
                />

            </div>

            {/* Right Section */}
            <div className="product-details-section">

                <h1 className="product-title">
                    {product.productName}
                </h1>

                <div className="rating-section">

                    ⭐⭐⭐⭐⭐
                    <span>(4.8 Ratings)</span>

                </div>

                <h2 className="product-price">
                    ₹ {product.price}
                </h2>

                <p className="product-description">

                    {product.productDescription}

                </p>

                {/* Features */}
                <div className="features-section">

                    <div className="feature-card">
                        🚚 Free Delivery
                    </div>

                    <div className="feature-card">
                        🔄 7 Days Replacement
                    </div>

                    <div className="feature-card">
                        🛡 1 Year Warranty
                    </div>

                    <div className="feature-card">
                        💳 Secure Payment
                    </div>

                </div>

                {/* Buttons */}
                <div className="button-section">

                    <button className="buy-btn" >
                        Buy Now
                    </button>

                    <button className="cart-btn" onClick={handleAddToCart}>
                        {
                            added ? "Added Successfully ✓" : "Add To Cart"
                        }
                    </button>

                </div>

            </div>

        </div>
    );
}


export default ProductDescriptionPage;