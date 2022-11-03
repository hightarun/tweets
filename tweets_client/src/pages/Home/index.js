import React, { useEffect } from "react";
import styles from "./Home.module.scss";

import { useSelector, useDispatch } from "react-redux";

import { useNavigate } from "react-router-dom";

import Layout from "../../components/Layout";

import { authenticateToken } from "../../redux/actions/auth";

const Home = () => {
  const dispatch = useDispatch();
  let navigate = useNavigate();

  const isAuthenticated = useSelector(
    (state) => state.rootReducer.auth.isAuthenticated
  );

  // Redirect to login if not authenticated
  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, navigate]);

  // authenticate token
  useEffect(() => {
    dispatch(authenticateToken());
  }, [dispatch]);

  return (
    <Layout>
      <div className={styles.container}>
        <h2>Tweets</h2>
        <div className={styles.wrap}></div>
      </div>
    </Layout>
  );
};

export default Home;
