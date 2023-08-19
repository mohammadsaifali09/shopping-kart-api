import axios from 'axios'
import React, { useEffect, useState } from 'react'
import FavoriteIcon from '@mui/icons-material/Favorite';
import "../styles/product.css"

const ViewProductUser = () => {
    let [data,setdata] = useState([])
    let user=JSON.parse(localStorage.getItem("currentUser"))
    useEffect(()=>{
        let fetchData =()=>{
        axios.get(`http://localhost:8080/products/${user.id}`)
        .then((res)=>{
            console.log(res.data.data);
            setdata(res.data.data)
        })
        .catch(()=>{
            alert("Not a good request")
        })
        }
        fetchData()
    },[])

    let addToCart=(id)=>{
        axios.post(`http://localhost:8080/products/${id}/${user.id}`)
        .then(()=>{
            alert("added to wishlist")
        })
        .catch(()=>{
            console.log("dshg")
        })
    }

    let addToFavourite=(id)=>{
        axios.post(`http://localhost:8080/products/add/${id}/${user.id}`)
        .then(()=>{
            alert("added to wishlist")
        })
        .catch(()=>{
            console.log("dshg")
        })
    }

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
                            <p>M.R.P: <strike>{x.cost}</strike></p>
                            <h5 id='discount'>Discount price: ₹{x.cost-(x.cost*12/100)}</h5>
                        </div>
                        <div className="buttons">
                            <button id="addtocart"  className="btn btn-success" onClick={()=>{addToCart(x.id)}}>Add to cart</button>
                            <FavoriteIcon onClick={()=>{addToFavourite(x.id)}}/>
                        </div>
                    </div>
                </div>
            )
        })}
    </div>
  )
}

export default ViewProductUser