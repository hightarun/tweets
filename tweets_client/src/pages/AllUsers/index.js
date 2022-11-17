import React, { useEffect, useState } from "react";
import styles from "./AllUsers.module.scss";
import axios from "axios";

import { useDispatch } from "react-redux";

import { setAlert } from "../../redux/actions/alert";

import { useNavigate } from "react-router-dom";

import Layout from "../../components/Layout";

import { loadUser } from "../../redux/actions/auth";
import UserCard from "../../components/pageTemplates/UserCard";

const AllUsers = () => {
  const dispatch = useDispatch();
  let navigate = useNavigate();

  const [users, setUsers] = useState([]);
  const [searchUser, setSearchUser] = useState({});
  const [search, setSearch] = useState("");

  const getAllUsers = async () => {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_TWEETS_BACKEND}/users/all`
      );
      setUsers(res.data);
    } catch (err) {
      const errors = err.response.data.errorMap; // errors array from backend
      if (errors) {
        Object.keys(errors).map((key) => {
          return dispatch(setAlert(key, errors[key], "danger"));
        });
      }
    }
  };

  const onSubmitSearch = async (e) => {
    e.preventDefault();
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_TWEETS_BACKEND}/user/search/${search}`
      );
      setSearchUser(res.data);
    } catch (err) {
      const errors = err.response.data.errorMap; // errors array from backend
      if (errors) {
        Object.keys(errors).map((key) => {
          return dispatch(setAlert(key, errors[key], "danger"));
        });
      }
    }
  };

  const onChangeSearch = (e) => {
    setSearch(e.target.value);
    setSearchUser({});
  };

  const onClearSearch = () => {
    setSearch("");
    setSearchUser({});
  };

  useEffect(() => {
    getAllUsers();
  }, []);

  // authenticate token
  useEffect(() => {
    dispatch(loadUser());
  }, [dispatch]);
  return (
    <Layout>
      <div className={styles.container}>
        <form onSubmit={(e) => onSubmitSearch(e)}>
          <input
            type="text"
            value={search}
            placeholder="Search for username"
            onChange={(e) => onChangeSearch(e)}
          />
          <button type="submit">🔎</button>
          <button type="reset" onClick={onClearSearch}>
            ❌
          </button>
        </form>
        <div className={styles.users}>
          {searchUser.id && (
            <div className={styles.search}>
              <UserCard user={searchUser} />
            </div>
          )}
          {users &&
            users.map((user) => {
              return (
                <div key={user.id}>
                  <UserCard user={user} />
                </div>
              );
            })}
        </div>
      </div>
    </Layout>
  );
};

export default AllUsers;
