import Navbar from './Navbar';
import { Route, Routes } from 'react-router-dom';
import UserDashboard from './UserDashboard';
import Footer from './Footer';

const UserHomePage=()=> {
  return (
    <div>
      <Navbar/>
      <Routes>
        <Route path='/' element={<UserDashboard/>} />
      </Routes>
      <Footer/>
    </div>
  );
}

export default UserHomePage;