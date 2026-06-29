// ProfilePage.jsx
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ProfilePage.css";

function ProfilePage() {

    const [user, setUser] = useState({});

    const navigate = useNavigate();

    useEffect(() => {

        const fetchProfile = async () => {

            try {

                const token = localStorage.getItem("token");

                const response = await fetch(
                    "http://localhost:8080/getprofile",
                    {
                        method: "GET",
                        headers: {
                            "Authorization": `Bearer ${token}`
                        }
                    }
                );

                const data = await response.json();

                setUser(data);

            } catch (error) {
                console.log(error);
            }
        };

        fetchProfile();

    }, []);

    const handleLogout = () => {

        localStorage.removeItem("token");

        navigate("/", {
            replace: true
        });
    };

    return (

        <div className="profile-page">

            <div className="profile-card">

                <div className="profile-left">

                    <img
                        src={`http://localhost:8080${user.imageUrl}`}
                        alt="profile"
                        className="profile-image"
                    />

                    <h1>
                        {user.email}
                    </h1>

                    <p>
                        Full Stack Developer
                    </p>

                    <button
                        className="logout-btn"
                        onClick={handleLogout}
                    >
                        Logout
                    </button>

                </div>

                <div className="profile-right">

                    <h2>
                        Profile Details
                    </h2>

                    <div className="profile-info">

                        <div>
                            <span>username</span>
                            <h3>{user.email}</h3>
                        </div>
                        <div>
                            <span>Email</span>
                            <h3>{user.email}</h3>
                        </div>

                        <div>
                            <span>Phone</span>
                            <h3>{user.phoneNumber}</h3>
                        </div>

                    </div>

                    <button className="edit-btn">
                        Edit Profile
                    </button>

                </div>

            </div>

        </div>
    );
}

export default ProfilePage;


