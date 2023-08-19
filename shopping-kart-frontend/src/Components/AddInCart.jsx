import React from 'react'
import axios from "axios";
import { useEffect, useState } from "react";
import FavoriteIcon from '@mui/icons-material/Favorite';

const AddInCart = () => {
  let [content, setContent]=useState([])
  let user=JSON.parse(localStorage.getItem("currentUser"))

  useEffect(()=>{
    let fetchData=()=>{
      axios.get(`http://localhost:8080/products/cart/${user.id}`)
      .then((res)=>{
        console.log(res.data.data);
        setContent(res.data.data)
      })
      .catch(()=>{
        alert("Error")
      })
    }
    fetchData()
  },[])

  return (
    <div className='addincart'>
        <h3>Items in cart</h3>
        {content.map((x)=>{
            return(
                <div className="display">
                    <div className="page">
                        <div className="image">
                            <img src={x.image} alt="" />
                        </div>
                        <div className="details">
                            <hr />
                            <h3>{x.name}</h3>
                            <span>Flat INR 2000 off on ICICI bank credit card...</span>
                            <b>M.R.P: <strike>{x.cost}</strike></b> <br />
                            <h5 id='discount'>₹{x.cost-(x.cost*12/100)}</h5>
                        </div>
                        <div className="buttons">
                          <button className='btn btn-success'>Add to cart</button>
                          <FavoriteIcon/>
                        </div>
                    </div>
                </div>
            )
        })}
    </div>
  )
}

export default AddInCart