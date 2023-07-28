import Form from 'react-bootstrap/Form';
import "../styles/merchantlogin.css"
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

const MerchantLogin=()=>{
    let [uname,setUname]=useState("")
    let [pwd,setPwd]=useState("")
    let navigate=useNavigate()
    let handleSubmit=()=>{
        if(uname=="admin" && pwd==123){
            navigate("/merchanthome")
        }
        else{
            alert("Data is Invalid")
        }
    }
    return(
        <div className="merchantlogin">
            <div className="input_group1">
                <Form action="">
                    <Form>
                        <h3>Merchant</h3>
                        <Form.Group className="mb-3" controlId="formGroupEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control value={uname} onChange={(u)=>{setUname(u.target.value)}} type="email" placeholder="Enter email" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formGroupPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control value={pwd} onChange={(p)=>{setPwd(p.target.value)}} type="password" placeholder="Password" />
                        </Form.Group>
                    </Form>
                    <button onClick={handleSubmit} className="btn btn-primary">Sign in</button>
                    <Link to="/merchantsignup"><button className="btn btn-danger">Sign up</button></Link>
                    <Link to="/setpasswordmerchant">Forgot Password</Link>
                </Form>   
            </div>
        </div>
    );
}
export default MerchantLogin;