import React, { useEffect } from "react";

import { loadUser } from "../redux/actions/auth";
import setAuthToken from "./setAuthtoken";
import { useSelector, useDispatch } from "react-redux";

//this component is for setting the global auth-token header with token value in local storage
//loadUser action will get the user from the database if the token verifies at the server
const UserLoad = () => {
  const dispatch = useDispatch();
  useEffect(() => {
    const load = async () => {
      if (localStorage.token) {
        setAuthToken(localStorage.token);
      }
      dispatch(loadUser());
    };
    load();
  });
  return <div></div>;
};

export default UserLoad;
