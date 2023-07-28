import Form from 'react-bootstrap/Form';
import "../styles/userlogin.css"
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

const UserLogin=()=>{
    let [uname,setUname]=useState("")
    let [pwd,setPwd]=useState("")
    let navigate=useNavigate()
    let handleSubmit=()=>{
        if(uname=="user" && pwd==123){
            navigate("/userhome")
        }
        else{
            alert("Data is Invalid")
        }
    }

    return(
        <div className="userlogin">
            <div className="input_group">
                <Form action="">
                    <Form>
                        <h2>User</h2>
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
                    <Link to="/usersignup"><button className="btn btn-danger">Sign up</button></Link>
                    <Link to="/setpassworduser">Forgot Password</Link>
                </Form>   
            </div>
        </div>
    );
}
export default UserLogin;