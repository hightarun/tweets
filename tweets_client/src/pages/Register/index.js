import React, { useEffect, useState } from "react";
import styles from "./Register.module.scss";

import { useSelector, useDispatch } from "react-redux";

import { useNavigate } from "react-router-dom";

import Layout from "../../components/Layout";

import { registerUser } from "../../redux/actions/auth";
import { setAlert } from "../../redux/actions/alert";

const Register = () => {
  const dispatch = useDispatch();
  let navigate = useNavigate();

  const [formdata, setFormData] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    password: "",
    password1: "",
    contactNumber: "",
  });

  //updating state on change in form
  const onChange = (e) => {
    setFormData({ ...formdata, [e.target.name]: e.target.value });
  };
  const onChangeUname = (e) => {
    setFormData({ ...formdata, [e.target.name]: e.target.value.toLowerCase() });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    if (formdata.password !== formdata.password1) {
      dispatch(setAlert("Message", "Passwords do not match ", "danger"));
    } else {
      //submit form data to server
      const user = {
        username: formdata.username,
        firstName: formdata.firstName,
        lastName: formdata.lastName,
        email: formdata.email,
        contactNumber: formdata.contactNumber,
        password: formdata.password,
      };
      dispatch(registerUser(user));
    }
  };

  return (
    <Layout>
      <div className={styles.container}>
        <div className={styles.wrapper}>
          <div className={styles.txtwrap}>
            <p>Create Your Account</p>
          </div>
          <form className={styles.form} onSubmit={(e) => onSubmit(e)}>
            <div className={styles.username}>
              <p>Tweets</p>
              <input
                className={styles.input_box}
                type="text"
                placeholder="Username"
                name="username"
                pattern="^[a-z_]+([._]?[a-z0-9]+)*$"
                title="username is case-insensative "
                value={formdata.username}
                onChange={(e) => {
                  onChangeUname(e);
                }}
                required
              />
            </div>
            <div>
              <input
                className={styles.input_box}
                type="text"
                placeholder="First Name"
                pattern="^[A-Z][a-z]{0,26}$"
                title="First letter should be in Uppercase, numbers and special characters are not allowed"
                name="firstName"
                value={formdata.firstName}
                onChange={(e) => onChange(e)}
                required
              />
            </div>
            <div>
              <input
                className={styles.input_box}
                type="text"
                placeholder="Last Name"
                pattern="^[A-Z][a-z]{0,26}$"
                title="First letter should be in Uppercase, numbers and special characters are not allowed"
                name="lastName"
                value={formdata.lastName}
                onChange={(e) => onChange(e)}
                required
              />
            </div>
            <div>
              <input
                className={styles.input_box}
                type="email"
                placeholder="Email Address"
                name="email"
                value={formdata.email}
                onChange={(e) => onChange(e)}
                required
              />
            </div>
            <div>
              <input
                className={styles.input_box}
                type="number"
                placeholder="Phone No."
                pattern="[0-9]{10,11}$"
                name="contactNumber"
                value={formdata.contactNumber}
                onChange={(e) => onChange(e)}
                required
              />
            </div>
            <div className={styles.name}>
              <input
                className={styles.input_box}
                type="password"
                placeholder="Password"
                name="password"
                title="Password must be of atleast 8 characters"
                minLength={8}
                value={formdata.password}
                onChange={(e) => onChange(e)}
              />

              <input
                className={styles.input_box}
                type="password"
                placeholder="Confirm Password"
                name="password1"
                title="Password must be of atleast 8 characters"
                minLength={8}
                value={formdata.password1}
                onChange={(e) => onChange(e)}
              />
            </div>
            <div>
              <button className={styles.butn} type="submit">
                <span>Register</span>
              </button>
            </div>
          </form>
          <p>
            Already have an account? <a href="/login">Sign In</a>
          </p>
        </div>
      </div>
    </Layout>
  );
};

export default Register;
