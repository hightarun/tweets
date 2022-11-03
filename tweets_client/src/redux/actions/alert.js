import { SET_ALERT, REMOVE_ALERT } from "../types";

import { v4 as uuid } from "uuid";

export const setAlert = (title, msg, alertType) => (dispatch) => {
  const id = uuid();
  dispatch({
    type: SET_ALERT,
    payload: {
      title,
      msg,
      alertType,
      id,
    },
  });
  if (alertType === "success") {
    setTimeout(() => dispatch({ type: REMOVE_ALERT, payload: id }), 5000);
  } else if (alertType === "danger") {
    setTimeout(() => dispatch({ type: REMOVE_ALERT, payload: id }), 12000);
  }
};

export const removeAlert = (id) => (dispatch) => {
  dispatch({ type: REMOVE_ALERT, payload: id });
};
