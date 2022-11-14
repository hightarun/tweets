import React, { useEffect, useState } from "react";
import styles from "./Users.module.scss";
import { useParams } from "react-router-dom";

import { useSelector, useDispatch } from "react-redux";

import { useNavigate } from "react-router-dom";
import { loadUser } from "../../redux/actions/auth";
import { getCurrentUser } from "../../redux/actions/user";

import CurrentUser from "../../components/pageTemplates/CurrentUser";
import User from "../../components/pageTemplates/User";

import Layout from "../../components/Layout";

const Users = (props) => {
  const { userid } = useParams();
  const dispatch = useDispatch();
  let navigate = useNavigate();

  const [currentUser, setCurrentUser] = useState(false);

  const userData = useSelector((state) => state.rootReducer.user.userData);

  const authUser = useSelector((state) => state.rootReducer.auth.user);

  const isAuthenticated = useSelector(
    (state) => state.rootReducer.auth.isAuthenticated
  );

  // authenticate token
  useEffect(() => {
    dispatch(loadUser());
  }, [dispatch]);

  useEffect(() => {
    if (userid) {
      dispatch(getCurrentUser(userid));
      if (authUser) {
        if (userid === authUser.username) {
          setCurrentUser(true);
        }
      }
    }
  }, [userid, authUser]);

  return (
    <Layout>
      <div className={styles.container}>
        {userData && currentUser ? (
          <CurrentUser userData={userData} />
        ) : (
          <User userData={userData} />
        )}
      </div>
    </Layout>
  );
};

export default Users;
