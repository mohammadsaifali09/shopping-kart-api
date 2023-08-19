import Form from 'react-bootstrap/Form';
import "../styles/merchantlogin.css"
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import axios from 'axios';

const MerchantLogin=()=>{
    let [email,setEmail]=useState("")
    let [password,setPassword]=useState("")
    let navigate=useNavigate()
    let handleSubmit=(e)=>{
        e.preventDefault()
        axios.post(`http://localhost:8080/merchants/verify?email=${email}&password=${password}`)
        .then((response)=>{
            console.log(response.data.message);
            console.log(response.data.data);
            let vemail=response.data.data.email
            let vpassword=response.data.data.password
            if (vemail==email && vpassword==password) {
                localStorage.setItem("currentMerchant", JSON.stringify(response.data.data))
                navigate("/merchanthome")
            }
            else{
                alert("Invalid email or password")
            }
        })
        .catch(()=>{
            alert("Invalid email or password")
        })
    }
    return(
        <div className="merchantlogin">
            <div className="input_group1">
                <Form action="">
                    <h3>Merchant</h3>
                    <Form.Group className="mb-3" controlId="formGroupEmail">
                        <Form.Label>Email address</Form.Label>
                        <Form.Control value={email} onChange={(u)=>{setEmail(u.target.value)}} type="email" placeholder="Enter email" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formGroupPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control value={password} onChange={(p)=>{setPassword(p.target.value)}} type="password" placeholder="Password" />
                    </Form.Group>
                    <button onClick={handleSubmit} className="btn btn-primary">Sign in</button>
                    <Link to="/merchantsignup"><button className="btn btn-danger">Sign up</button></Link>
                    <Link to="/setpasswordmerchant">Forgot Password</Link>
                </Form> 
            </div>
        </div>
    );
}
export default MerchantLogin;