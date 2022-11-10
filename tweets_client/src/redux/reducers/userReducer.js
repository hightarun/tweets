import { GET_USER, USER_ERROR, CLEAR_USER } from "../types";

const initialState = {
  userData: null,
  loading: true,
};

const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_USER:
      return {
        ...state,
        userData: action.payload,
        loading: false,
      };
    case USER_ERROR:
      return {
        ...state,
        loading: false,
        userData: null,
      };
    case CLEAR_USER:
      return {
        ...state,
        userData: null,
      };
    default:
      return state;
  }
};

export default userReducer;
