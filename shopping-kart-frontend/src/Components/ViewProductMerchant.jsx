import axios from 'axios'
import React, { useEffect, useState } from 'react'
import "../styles/product.css"

const ViewProductMerchant = () => {
    let [data, setData]=useState([])
    let merchant=JSON.parse(localStorage.getItem("currentMerchant"))

    useEffect(()=>{
        let fetchData=()=>{
            axios.get(`http://localhost:8080/products/${merchant.id}`)
            .then((res)=>{
                setData(res.data.data)
                console.log(res.data.data);
            })
            .catch(()=>{
                alert("Not a good request")
            })
        }
        fetchData()
    },[])
  return (
    <div className='product'>
        {data.map((x)=>{
            return(
                <div className="display">
                    <div className="page">
                        <div className="image">
                            <img src={x.image} alt="" />
                        </div>
                        <div className="details">
                            <hr />
                            <h3>{x.name}</h3>
                            <span id='offer'>Flat INR 2000 off on ICICI bank credit card...</span>
                            <b>M.R.P: <strike>{x.cost}</strike></b>
                            <h5 id='discount'>Discount price: ₹{x.cost-(x.cost*12/100)}</h5>
                        </div>
                        {/* <div className="buttons">
                            <button id="addtocart"  className="btn btn-success">Add To Cart</button>
                            <FavoriteIcon/>
                        </div> */}
                    </div>
                </div>
            )
        })}
    </div>
  )
}

export default ViewProductMerchant