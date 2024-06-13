import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from '../MainPage/MainPageBeforeLogin/Header';
import Login from '../Login/Login'
import HeaderAfter from '../MainPage/MainPageAfterLogin/HeaderAfter';
// import Playstore from '../MainPage/Playstore';
import About from '../MainPage/MainPageBeforeLogin/About';
import Contact from '../MainPage/MainPageBeforeLogin/Contact';
import Footer from '../MainPage/MainPageBeforeLogin/Footer';
import SigninFirst from "../Signin/SigninFirst";
import Signin from "../Signin/Signin";
import {useState } from "react";
import AddJobs from "../MainPage/MainPageAfterLogin/AddJobs";
import AdminSignup from '../Admin/AdminSignup'
import AdminSignup1 from "../Admin/AdminSignin1";
import AdminLogin from "../Admin/AdminLogin";
import AdminHeader from "../Admin/AdminHeader";
import AdminHome from "../Admin/AdminHome";
import AdminCategoryMainPage from "../Admin/AdminCategoryMainPage";
import AdminJobsPanel from "../Admin/AdminJobsPanel";
import AdminUsersPanel from "../Admin/AdminUsersPanel";
const MainRouterController = () => {

  return (
    <div>
        <Router>
        {/* {isLoggedIn ? <HeaderAfter /> : } */}
        {/* <HeaderAfter/> */}
        <Routes>
        <Route path="/login" element={<><Header /><Login/></>} />
        <Route path="/home" element={<><Header/></>} />
        <Route path="/" element={<><Header/></>} />
        <Route path="/signin-user" element={<><Header /><SigninFirst/></>} />
        <Route path="/signin-second" element={<><Header /><Signin/></>} />
         <Route path="/about" element={<><Header/><About/> </>} />
         <Route path="/contact" element={<><Header/><Contact/> </>} />


         <Route path="/homeAfter" element={<><HeaderAfter/></>} />
         <Route path="/home2" element={<><HeaderAfter/></>} />
         <Route path="/addjob" element={<><HeaderAfter/><AddJobs/> </>} />



         <Route path="/signin-admin" element={<><Header/><AdminSignup1/> </>} />
         <Route path="/admin-signin-second" element={<><Header/><AdminSignup/> </>} />

         <Route path="/login-admin" element={<><Header/><AdminLogin/> </>} />

         <Route path="/logined" element={<><AdminHeader/><AdminHome/> </>} />
         <Route path="/Category" element={<><AdminHeader/><AdminCategoryMainPage/> </>} />
         <Route path="/admin-job" element={<><AdminHeader/><AdminJobsPanel/> </>} />
         <Route path="/admin-user" element={<><AdminHeader/><AdminUsersPanel/> </>} />
         

         






           {/* <Route path="/playstore" element={<><Header /><Playstore/></>} /> */}
          {/* <Route path="/homehomeAfter" element={< />} /> */}
          {/*<Route path="/cart" element={<CartSection />} />
          <Route path="/:levelOne/:levelTwo/:levelThree" element={<Product />} />
          <Route path="/product/:productId" element={<ProductDetails />} />
          <Route path="/checkout" element={<Checkout />} />
          <Route path="/account/order" element={<OrderPage />} />
          <Route path="/account/order/:orderId" element={<OrderDetails />} /> */}
        </Routes>
        <Footer/>
      </Router>
      
    </div>
  )
}

export default MainRouterController
