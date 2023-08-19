import React, { useState } from 'react'
import InputGroup from 'react-bootstrap/InputGroup';
import Form from 'react-bootstrap/Form';
import "../styles/additem.css"
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const AddItem = () => {
    let [name, setName]=useState("")
    let [brand, setBrand]=useState("")
    let [category, setCategory]=useState("")
    let [description, setDescription]=useState("")
    let [cost, setCost]=useState("")
    let [image, setImage]=useState("")
    let navigate=useNavigate()

    let handleSubmit=(e)=>{
        e.preventDefault()
        let product={name,brand,category,description,cost,image}
        let merchant=JSON.parse(localStorage.getItem("currentMerchant"))
        axios.post(`http://localhost:8080/products/${merchant.id}`,product)
        .then(()=>{
            alert("Product has been added")
            navigate("/merchanthome")
        })
        .catch(()=>{
            alert("Invalid data")
        })
    }

  return (
    <div className='additem'>
        <br/>
        <InputGroup className='mb-3'>
            <InputGroup.Text id='inputGroup-sizing-default'>
                Product Name :
            </InputGroup.Text>
            <Form.Control value={name} onChange={(e)=>{setName(e.target.value)}}
                placeholder="Enter product name"
                aria-label="Default"
                aria-describedby="inputGroup-sizing-default"
            />
        </InputGroup>
        <br/>
        <InputGroup className='mb-3'>
            <InputGroup.Text id='inputGroup-sizing-default'>
                Brand :
            </InputGroup.Text>
            <Form.Control value={brand} onChange={(e)=>{setBrand(e.target.value)}}
                placeholder="Enter product brand"
                aria-label="Default"
                aria-describedby="inputGroup-sizing-default"
            />
        </InputGroup>
        <br/>
        <Form.Select value={category} onChange={(e)=>{setCategory(e.target.value)}} aria-label="Default select example">
            <option>Mobiles, Computers</option>
            <option>TV, Appliances, Electronics</option>
            <option>Men's Fashion</option>
            <option>Women's Fashion</option>
            <option>Home, Kitchen, Pets</option>
            <option>Beauty, Health, Grocery</option>
            <option>Sports, Fitness, Bags, Luggage</option>
            <option>Toys, Baby Products, Kids' Fashion</option>
            <option>Car, Motorbike, Industrial</option>
            <option>Books</option>
            <option>Movies, Music & Video Games</option>
        </Form.Select>
        <br/>
        <InputGroup className='mb-3'>
            <InputGroup.Text id='inputGroup-sizing-default'>
                Description :
            </InputGroup.Text>
            <Form.Control value={description} onChange={(e)=>{setDescription(e.target.value)}}
                placeholder="Enter product description"
                aria-label="Default"
                aria-describedby="inputGroup-sizing-default"
            />
        </InputGroup>
        <br/>
        <InputGroup className='mb-3'>
            <InputGroup.Text id='inputGroup-sizing-default'>
                Price :
            </InputGroup.Text>
            <Form.Control value={cost} onChange={(e)=>{setCost(e.target.value)}}
                placeholder="Enter product price"
                aria-label="Default"
                aria-describedby="inputGroup-sizing-default"
            />
        </InputGroup>
        <br/>
        <InputGroup className='mb-3'>
            <InputGroup.Text id='inputGroup-sizing-default'>
                Image :
            </InputGroup.Text>
            <Form.Control value={image} onChange={(e)=>{setImage(e.target.value)}}
                placeholder="Enter product image"
                aria-label="Default"
                aria-describedby="inputGroup-sizing-default"
            />
        </InputGroup>
        <br />
        <button onClick={handleSubmit} className='btn btn-success px-5 m-3'>Add Item</button>
    </div>
  )
}

export default AddItem