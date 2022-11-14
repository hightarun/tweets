import React, { useState } from "react";
import styles from "./Navbar.module.scss";

import { useNavigate, Link } from "react-router-dom";

import { useSelector, useDispatch } from "react-redux";
import { logout } from "../../redux/actions/auth";

const Navbar = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isAuthenticated = useSelector(
    (state) => state.rootReducer.auth.isAuthenticated
  );

  const user = useSelector((state) => state.rootReducer.auth.user);

  const logoutHandler = () => {
    dispatch(logout());
    navigate("/login");
  };

  const homeHandler = () => {
    navigate("/");
  };

  const profileHandler = () => {
    user && navigate(`/user/${user.username}`);
  };

  // navbar links when logged in.
  const authLinks = (
    <div className={styles.menu}>
      <div onClick={homeHandler}>
        <p>Home</p>
      </div>
      <div onClick={profileHandler}>
        <p>Profile</p>
      </div>
      <div onClick={logoutHandler}>
        <p>Logout</p>
      </div>
    </div>
  );
  // navbar links when not logged in.
  const guestLinks = (
    <div className={styles.home} onClick={homeHandler}>
      <p>Home</p>
    </div>
  );
  return (
    <div className={styles.navbar}>
      <div onClick={() => navigate("/")} className={styles.logo}>
        <p>Tweets</p>
      </div>
      <div className={styles.links}>
        {isAuthenticated ? authLinks : guestLinks}
      </div>
    </div>
  );
};

export default Navbar;
