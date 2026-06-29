import { useEffect } from "react";

function Rough() {
    useEffect(() => {
        const handleLogin = async () => {
            const token = localStorage.getItem("token")
            console.log(token)
            try {
                const response = await fetch("http://localhost:8080/check", {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                })
                console.log("It is entering here")
                if (response.ok) {
                    const data = await response.text();
                    console.log(data)
                }
                else {
                    console.log("error")
                }
            }
            catch(error) {
                console.log(error)
            }
        }
        handleLogin();
    },[]);
    return (
        <div>hi</div>
    );
}
export default Rough;