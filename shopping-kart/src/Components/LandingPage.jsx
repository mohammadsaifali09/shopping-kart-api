import { Link } from "react-router-dom";
import "../styles/landingpage.css"
import user from "./image/userlogin.png"
import merchant from "./image/merchantlogin.png"

const LandingPage=()=>{
    return(
        <div className="landingpage">
            <div className="landing">
                <div className="user">
                    <Link to="/userlogin">
                        <img src={user} />
                        <h2>User Login</h2>
                    </Link>
                </div>
                <div className="merchant">
                    <Link to="/merchantlogin">
                        <img src={merchant} />
                        <h2>Merchant Login</h2>
                    </Link>
                </div>
            </div>
        </div>
    );
}
export default LandingPage;