import {
  LOGIN_FAIL,
  LOGIN_SUCCESS,
  AUTH_SUCCESS,
  AUTH_ERROR,
  LOGOUT,
} from "../types";

import axios from "axios";

import { setAlert } from "./alert";
import setAuthToken from "../../utils/setAuthToken";

// Authenticate token
export const authenticateToken = () => async (dispatch) => {
  if (localStorage.token) {
    setAuthToken(localStorage.token);
  }
  try {
    if (localStorage.token) {
      const res = await axios.post(
        `${process.env.REACT_APP_TWEETS_BACKEND}/authorize`
      );
      if (res.data) {
        dispatch({
          type: AUTH_SUCCESS,
        });
      } else {
        dispatch({
          type: AUTH_ERROR,
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

//Logout
export const logout = () => (dispatch) => {
  dispatch({ type: LOGOUT });
};
