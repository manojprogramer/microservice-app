import "./Login.css";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
function Login() {
    const navigate = useNavigate();
    const location = useLocation();
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const handleLogin = async () => {
        const userData = {
            email: email,
            password: password
        }
        try {
            const response = await fetch("http://localhost:8081/user/login", {
                method: "post",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(userData)
            })
            if (!response.ok) {
                alert("username and password is wrong")
                return
            }
            else {
                const data = await response.json();
                console.log(data)
                localStorage.setItem("token", data.token)
                navigate(location.state?.from || "/")
            }
        }
        catch (error) {
            console.log(error);
        }


    }
    return (
        <div className="container">

            <div className="login-box">

                <h1>Login</h1>

                <input
                    type="email"
                    placeholder="Enter Email"
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Enter Password"
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button onClick={handleLogin}>Login</button>
                {/* <p> Already have an account? <Link to="/login">Login</Link></p> */}
                <p>don't have an account? <Link to = "/register">register</Link></p>
            </div>

        </div>
    );
}

export default Login;