import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login"
import Register from "./pages/Register"
import Homepage from "./pages/Homepage";
import CartPage from "./pages/CartPage";
import Rough from "./pages/rough";
import ProductDescriptionPage from "./pages/ProductDescriptionPage";
import ProfilePage from "./pages/ProfilePage";
import CheckoutPage from "./pages/CheckoutPage";
import OrderPage from "./pages/OrderPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/" element={<Homepage />}></Route>
        <Route path="/home" element={<Homepage />}></Route>
        <Route path="/register" element={<Register />}></Route>
        <Route path="/cart" element={<CartPage />}></Route>
        <Route path="/product/:id" element={<ProductDescriptionPage />}></Route>
        <Route path = "/profile" element = {<ProfilePage/>}></Route>
        <Route path = "/checkout" element = {<CheckoutPage/>}></Route>
        <Route path = "/order" element = {<OrderPage/>}></Route>
      </Routes>
    </BrowserRouter>);
}
export default App;