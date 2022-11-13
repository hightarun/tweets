import { GET_USER, USER_ERROR } from "../types";

import axios from "axios";
//import api from "../../utils/api";
import { setAlert } from "./alert";

// get current user
export const getCurrentUser = (uname) => async (dispatch) => {
  try {
    const res = await axios.get(
      `${process.env.REACT_APP_TWEETS_BACKEND}/user/search/${uname}`
    );
    dispatch({
      type: GET_USER,
      payload: res.data,
    });
  } catch (err) {
    const errors = err.response.data.errorMap; // errors array from backend

    if (errors) {
      Object.keys(errors).map((key, index) => {
        return dispatch(setAlert(key, errors[key], "danger"));
      });
    }
    dispatch({
      type: USER_ERROR,
    });
  }
};
