import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import "./Homepage.css"
function Homepage() {

    const [products, setProducts] = useState([]);

    useEffect(() => {

        const fetchProducts = async () => {
            const response = await fetch("http://localhost:8082/products/getproducts")
            const data = await response.json();
            setProducts(data);
        }
        fetchProducts();

    }, []);

    return (
        <div className="home-container">
            {/* Navbar */}
            <nav className="navbar">

                <h1 className="logo">MyStore</h1>

                <ul className="nav-links">
                    <li>
                        <Link to="/cart">Home</Link>
                    </li>
                    <li>
                        <Link to="/cart">Cart</Link>
                    </li>
                    <li>
                        <Link to="/order">Orders</Link>
                    </li>
                    <li>
                        <Link to="/cart">contact</Link>
                    </li>
                    <li>
                        <Link to="/profile">Profile</Link>
                    </li>
                </ul>

            </nav>

            {/* Hero Section */}
            <div className="hero-section">

                <h1>Welcome to MyStore</h1>

                <p>
                    Buy the latest products with best prices.
                </p>

            </div>

            {/* Products Section */}
            <div className="products-section">

                <h1 className="product-title">
                    Our Products
                </h1>

                <div className="products-container">

                    {
                        products.map((product) => (
                            <Link to={`/product/${product.id}`} key={product.id}>
                                <div className="product-card" key={product.id}>
                                    <img src={`http://localhost:8082${product.imageUrl}`} alt={product.productName}
                                        className="product-image" />
                                    <h2>{product.productName}</h2>
                                    <p>₹ {product.price}</p>
                                    <p>{product.productDescription}</p>

                                </div>
                            </Link>
                        ))
                    }

                </div>

            </div>

        </div>
    );
}

export default Homepage;