import React, { useEffect, useState } from "react";
import styles from "./Login.module.scss";
import axios from "axios";

import { useSelector, useDispatch } from "react-redux";

import { useNavigate } from "react-router-dom";

import Layout from "../../components/Layout";

import { loginUser } from "../../redux/actions/auth";

import { setAlert } from "../../redux/actions/alert";

import Modal from "../../components/Modal";

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

  const [resetFormData, setResetFormData] = useState({
    username: "",
    code: "",
    password: "",
  });

  const [toggleForgot, setToggleForgot] = useState(false);
  const [forgotUser, setForgotUser] = useState("");
  const [forgotModal, setForgotModal] = useState(false);

  const toggleForgotModal = () => {
    setForgotModal(!forgotModal);
  };

  // On change of input fields
  const onChange = (e) =>
    setFormData({ ...formdata, [e.target.name]: e.target.value });

  const onChangeReset = (e) =>
    setResetFormData({ ...resetFormData, [e.target.name]: e.target.value });

  const setResetFormUser = (key, value) =>
    setResetFormData({ ...resetFormData, [key]: value });

  const submitForgot = async (e) => {
    e.preventDefault();
    setForgotModal(true);
    setResetFormUser(
      e.target.elements.username.name,
      e.target.elements.username.value
    );
    try {
      let res = await axios({
        method: "get",
        url: `${process.env.REACT_APP_TWEETS_BACKEND}/${forgotUser}/forgot`,
      });
      dispatch(setAlert("Message", res.data, "success"));
    } catch (err) {
      const errors = err.response.data.errorMap; // errors array from backend

      if (errors) {
        Object.keys(errors).map((key, index) => {
          return dispatch(setAlert(key, errors[key], "danger"));
        });
      }
    }
  };

  // login user by calling loginUser action -> stores token in localStorage
  const onSubmit = (e) => {
    e.preventDefault();
    dispatch(loginUser(formdata.username, formdata.password));
  };

  const onSubmitReset = async (e) => {
    e.preventDefault();
    const body = JSON.stringify(resetFormData);
    try {
      let res = await axios({
        method: "post",
        url: `${process.env.REACT_APP_TWEETS_BACKEND}/reset-password`,
        headers: { "Content-Type": "application/json" },
        data: body,
      });
      dispatch(setAlert("Message", res.data, "success"));
      setForgotModal(false);
    } catch (err) {
      const errors = err.response.data.errorMap; // errors array from backend

      if (errors) {
        Object.keys(errors).map((key, index) => {
          return dispatch(setAlert(key, errors[key], "danger"));
        });
      }
    }
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
        <div className={styles.forgot}>
          <p onClick={() => setToggleForgot(!toggleForgot)}>
            Forgot password ?
          </p>
          {toggleForgot ? (
            <div className={styles.contain}>
              <form onSubmit={submitForgot}>
                <div className={styles.user}>
                  <input
                    className={styles.input}
                    type="text"
                    placeholder="Username"
                    name="username"
                    required
                    value={forgotUser}
                    onChange={(e) => setForgotUser(e.target.value)}
                  />
                </div>
                <p> A code will be send to your registered email</p>
                <button type="submit"> Send Code</button>
              </form>
              <Modal open={forgotModal} onClose={toggleForgotModal}>
                <div className={styles.forgotModal}>
                  <p>RESET PASSWORD</p>
                  <p>Enter the code sent to the registered email</p>
                  <form onSubmit={(e) => onSubmitReset(e)}>
                    <input
                      className={styles.input}
                      type="text"
                      placeholder="Username"
                      name="username"
                      required
                      readOnly
                      value={forgotUser}
                    />
                    <input
                      className={styles.input}
                      type="text"
                      name="code"
                      placeholder="reset code"
                      required
                      value={resetFormData.code}
                      onChange={(e) => onChangeReset(e)}
                    />
                    <input
                      className={styles.input}
                      type="text"
                      name="password"
                      placeholder="password"
                      required
                      value={resetFormData.password}
                      onChange={(e) => onChangeReset(e)}
                    />
                    <button type="submit">Submit</button>
                  </form>
                </div>
              </Modal>
            </div>
          ) : (
            <div></div>
          )}
        </div>
      </div>
    </Layout>
  );
};

export default Login;
