import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LandingPage from './Components/LandingPage';
import UserLogin from './Components/UserLogin';
import MerchantLogin from './Components/MerchantLogin';
import 'bootstrap/dist/css/bootstrap.min.css';
import MerchantHome from './Components/MerchantHome';
import MerchantSignUp from './Components/MerchantSignUp';
import UserHome from './Components/UserHome';
import UserSignUp from './Components/UserSignUp'
import Error from './Components/Error';
import MerchantForgotPassword from './Components/MerchantForgotPassword';
import UserForgotPassword from './Components/UserForgotPassword';
import AddItem from './Components/AddItem';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
        <Route path='/' element={<LandingPage/>}/>
        <Route path='/userlogin' element={<UserLogin/>}/>
        <Route path='/userhome' element={<UserHome/>}/>
        <Route path='/usersignup' element={<UserSignUp/>}/>
        <Route path='/merchantlogin' element={<MerchantLogin/>}/>
        <Route path='/merchanthome/*' element={<MerchantHome/>}/>
        <Route path='/merchantsignup' element={<MerchantSignUp/>}/>
        <Route path='*' element={<Error/>}/>
        <Route path='/merchantforgotpassword' element={<MerchantForgotPassword/>}/>
        <Route path='/userforgotpassword' element={<UserForgotPassword/>}/>
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
