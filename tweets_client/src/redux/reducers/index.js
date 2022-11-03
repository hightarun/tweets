import { combineReducers } from "redux";

//Reducers import
import authReducer from "./authReducer";
import alertReducer from "./alertReducer";

const rootReducer = combineReducers({ auth: authReducer, alert: alertReducer });
export default rootReducer;
