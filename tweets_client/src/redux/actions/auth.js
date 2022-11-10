import {
  LOGIN_FAIL,
  LOGIN_SUCCESS,
  AUTH_SUCCESS,
  AUTH_ERROR,
  USER_LOADED,
  LOGOUT,
} from "../types";

import axios from "axios";

import { setAlert } from "./alert";
import setAuthToken from "../../utils/setAuthToken";
import { useNavigate } from "react-router-dom";

//Load User , returns user data if token matches
export const loadUser = () => async (dispatch) => {
  if (localStorage.token) {
    setAuthToken(localStorage.token);
  }
  try {
    if (localStorage.token) {
      const res = await axios.get(
        `${process.env.REACT_APP_TWEETS_BACKEND}/authorize`
      );
      dispatch({
        type: USER_LOADED,
        payload: res.data,
      });
      if (res.data) {
        dispatch({
          type: AUTH_SUCCESS,
        });
      }
    }
  } catch (err) {
    dispatch({
      type: AUTH_ERROR,
    });
  }
};

//Login User
export const loginUser = (username, password) => async (dispatch) => {
  const body = JSON.stringify({ username, password });
  try {
    const res = await axios({
      method: "post",
      url: `${process.env.REACT_APP_TWEETS_BACKEND}/login`,
      headers: { "Content-Type": "application/json" },
      data: body,
    });
    dispatch({
      type: LOGIN_SUCCESS,
      payload: res.data,
    });
    dispatch(setAlert("Message", "User login successfull", "success"));
  } catch (err) {
    const errors = err.response.data.errorMap; // errors array from backend

    if (errors) {
      Object.keys(errors).map((key, index) => {
        return dispatch(setAlert(key, errors[key], "danger"));
      });
    }
    dispatch({
      type: LOGIN_FAIL,
    });
  }
};

export const registerUser = (user) => async (dispatch) => {
  const body = JSON.stringify(user);
  try {
    const res = await axios({
      method: "post",
      url: `${process.env.REACT_APP_TWEETS_BACKEND}/register`,
      headers: { "Content-Type": "application/json" },
      data: body,
    });
    dispatch(setAlert("Message", res.data, "success"));
    setTimeout(() => useNavigate("/"), 5000);
  } catch (err) {
    const errors = err.response.data.errorMap; // errors array from backend
    if (errors) {
      Object.keys(errors).map((key, index) => {
        return dispatch(setAlert(key, errors[key], "danger"));
      });
    }
  }
};

//Logout
export const logout = () => (dispatch) => {
  dispatch({ type: LOGOUT });
};
