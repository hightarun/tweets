import React, { useEffect, useState } from "react";
import styles from "./Login.module.scss";

import { useSelector, useDispatch } from "react-redux";

import { useNavigate } from "react-router-dom";

import Layout from "../../components/Layout";

import { loginUser } from "../../redux/actions/auth";

const Login = () => {
  const dispatch = useDispatch();
  let navigate = useNavigate();

  const isAuthenticated = useSelector(
    (state) => state.rootReducer.auth.isAuthenticated
  );

  const [formdata, setFormData] = useState({
    username: "",
    password: "",
  });

  // On change of input fields
  const onChange = (e) =>
    setFormData({ ...formdata, [e.target.name]: e.target.value });

  // login user by calling loginUser action -> stores token in localStorage
  const onSubmit = (e) => {
    e.preventDefault();
    dispatch(loginUser(formdata.username, formdata.password));
  };

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/");
    }
  }, [isAuthenticated, navigate]);

  return (
    <Layout>
      <div className={styles.container}>
        <div className={styles.wrapper}>
          <div className={styles.wel_text}>
            <p>Tweets</p>
          </div>
          <form className={styles.form} onSubmit={(e) => onSubmit(e)}>
            <div>
              <input
                className={styles.input}
                type="text"
                placeholder="Username"
                name="username"
                value={formdata.username}
                onChange={(e) => onChange(e)}
                required
              />
            </div>
            <div>
              <input
                className={styles.input}
                type="password"
                placeholder="Password"
                name="password"
                value={formdata.password}
                onChange={(e) => onChange(e)}
              />
            </div>
            <button className={styles.button} value="login" type="submit">
              <span>Login</span>
            </button>
            <button
              className={styles.button}
              onClick={() => navigate("/register")}
            >
              <span>Register</span>
            </button>
          </form>
        </div>
      </div>
    </Layout>
  );
};

export default Login;
