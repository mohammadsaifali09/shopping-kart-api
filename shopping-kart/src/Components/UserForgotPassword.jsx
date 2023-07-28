import axios from 'axios'
import React, { useState } from 'react'
import Form from 'react-bootstrap/Form';
import '../styles/userforgotpassword.css'

const UserForgotPassword = () => {
  let [email, setEmail]=useState("")

  let handleclick=()=>{
    axios.post('http://localhost:8080/merchants/verify')
    .then(()=>{
      alert(`OTP has been sent to your ${email}`)
    })
    .catch(()=>{
      alert("please enter the valid email")
    })
  }

  return (
    <div>
        <Form id='form'>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                <Form.Label>Email address</Form.Label>
                <Form.Control value={email} onChange={(e)=>{setEmail(e.target.value)}} type="email" placeholder="name@example.com" />
            </Form.Group>
            <button onClick={handleclick} className='btn btn-success'>Rest</button>
        </Form>
    </div>
  )
}

export default UserForgotPassword