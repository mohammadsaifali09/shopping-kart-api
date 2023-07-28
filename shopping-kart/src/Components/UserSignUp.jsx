import React from 'react'
import Form from 'react-bootstrap/Form';
import "../styles/usersignup.css"
import axios from 'axios';
import { useState } from 'react';

const UserSignUp = () => {
    let [name,setName]=useState("")
    let [phone,setPhone]=useState("")
    let [email,setEmail]=useState("")
    let [password,setPassword]=useState("")

    const handleSubmit=(event)=>{
        let user={name,phone,email,password}
        axios.post("http://localhost:8080/users",user)
        .then(()=>{
            alert("Data has been added")
        })
        .catch(()=>{
            alert("Invalid data")
        })
        event.preventDefault()
    }

  return (
    <div className='usersignup'>
      <Form id='form' action=''>
            <Form>
                <Form.Group className="mb-3" controlId="formGroupEmail">
                    <Form.Label>Name</Form.Label>
                    <Form.Control value={name} onChange={(n)=>{setName(n.target.value)}} type="text" placeholder="Enter name" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formGroupEmail">
                    <Form.Label>Phone Number</Form.Label>
                    <Form.Control value={phone} onChange={(p)=>{setPhone(p.target.value)}} type="tel" placeholder="Enter phone" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formGroupEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control value={email} onChange={(e)=>{setEmail(e.target.value)}} type="email" placeholder="Enter email" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formGroupPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control value={password} onChange={(p)=>{setPassword(p.target.value)}} type="password" placeholder="Enter password" />
                </Form.Group>
            </Form>
            <button onClick={handleSubmit} className="btn btn-success px-5">Register</button>
        </Form>
    </div>
  )
}

export default UserSignUp