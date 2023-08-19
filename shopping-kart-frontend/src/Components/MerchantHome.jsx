import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MerchantDashboard from './MerchantDashboard'
import AddItem from './AddItem'
import MerchantNavbar from './MerchantNavbar'
import UpdateMerchant from './UpdateMerchant'
import ViewProductMerchant from './ViewProductMerchant'

const MerchantHomePage = () => {
  return (
    <div className='merchanthome'>
      <MerchantNavbar/>
      <Routes>
        <Route path='/' element={<MerchantDashboard/>} / >
        <Route path='/additem' element={<AddItem/>} />
        <Route path='/updatemerchant' element={<UpdateMerchant/>}></Route>
        <Route path='/viewproductmerchant' element={<ViewProductMerchant/>} />
      </Routes>
    </div>
  )
}

export default MerchantHomePage