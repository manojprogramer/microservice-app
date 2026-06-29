import { useEffect, useState } from "react";
import "./Register.css";
import { useLocation, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
function RegisterPage() {
    const navigate = useNavigate("")
    const location = useLocation("")
    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        phoneNumber: ""
    });

    const handleChange = (e) => {

        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    };

    const handleRegister = async (e) => {

        e.preventDefault();

        try {

            const response = await fetch("http://localhost:8081/user/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            });

            const data = await response.text();

            navigate( "/")

            console.log(data);

        } catch (error) {
            console.log(error);
        }
    };

    return (

        <div className="register-page">

            <form className="register-form" onSubmit={handleRegister}>

                <h1>Create Account</h1>

                <div className="input-row">

                    <input
                        type="text"
                        name="firstName"
                        placeholder="First Name"
                        value={user.firstName}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="text"
                        name="lastName"
                        placeholder="Last Name"
                        value={user.lastName}
                        onChange={handleChange}
                        required
                    />

                </div>

                <input
                    type="email"
                    name="email"
                    placeholder="Email Address"
                    value={user.email}
                    onChange={handleChange}
                    required
                />

                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={user.password}
                    onChange={handleChange}
                    required
                />

                <input
                    type="text"
                    name="phoneNumber"
                    placeholder="Phone Number"
                    value={user.phoneNumber}
                    onChange={handleChange}
                    required
                />

                <button className="register-button" type="submit">
                    Register
                </button>
                <p> Already have an account? <Link to="/login">Login</Link>
                </p>
            </form>

        </div> 
    );
}


export default RegisterPage;