import { Route, Routes } from 'react-router-dom';
import AddInCart from "./AddInCart"
import AddInWishlist from "./AddInWishlist"
import UserNavbar from './UserNavbar';
import UserDashboard from './UserDashboard'

const UserHome=()=> {
  return (
    <div className='userhome'>
      <UserNavbar/>
      <Routes>
        <Route path='/' element={<UserDashboard/>} />
        <Route path='/addincart' element={<AddInCart/>} />
        <Route path='/addinwishlist' element={<AddInWishlist/>} />
      </Routes>
    </div>
  );
}

export default UserHome;