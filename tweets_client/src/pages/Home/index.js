import React, { useEffect, useState } from "react";
import styles from "./Home.module.scss";
import axios from "axios";

import { useSelector, useDispatch } from "react-redux";

import { setAlert } from "../../redux/actions/alert";
import TweetCard from "../../components/pageTemplates/TweetCard";

import { useNavigate } from "react-router-dom";

import Layout from "../../components/Layout";

import { loadUser } from "../../redux/actions/auth";

const Home = () => {
  const dispatch = useDispatch();
  let navigate = useNavigate();

  const [tweets, setTweets] = useState([]);
  const [newTweet, setNewTweet] = useState({ content: "" });

  const userData = useSelector((state) => state.rootReducer.user.userData);

  const authUser = useSelector((state) => state.rootReducer.auth.user);

  const isAuthenticated = useSelector(
    (state) => state.rootReducer.auth.isAuthenticated
  );

  const getAllTweets = async () => {
    try {
      let res = await axios.get(`${process.env.REACT_APP_TWEETS_BACKEND}/all`);
      setTweets(res.data);
    } catch (err) {
      const errors = err.response.data.errorMap; // errors array from backend

      if (errors) {
        Object.keys(errors).map((key, index) => {
          return dispatch(setAlert(key, errors[key], "danger"));
        });
      }
    }
  };

  const onChangeNewTweetForm = (e) => {
    e.preventDefault();
    setNewTweet({ [e.target.name]: e.target.value });
  };

  const postTweet = async () => {
    const body = JSON.stringify(newTweet);
    try {
      let res = await axios({
        method: "post",
        url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/add`,
        headers: { "Content-Type": "application/json" },
        data: body,
      });
      dispatch(setAlert("Message", res.data, "success"));
    } catch (err) {
      const errors = err.response.data.errorMap; // errors array from backend

      if (errors) {
        Object.keys(errors).map((key, index) => {
          return dispatch(setAlert(key, errors[key], "danger"));
        });
      }
    }
  };

  useEffect(() => {
    getAllTweets();
  }, []);

  // Redirect to login if not authenticated
  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, navigate]);

  // authenticate token
  useEffect(() => {
    dispatch(loadUser());
  }, [dispatch]);

  return (
    <Layout>
      <div className={styles.container}>
        <div className={styles.newTweet}>
          <div className={styles.formContainer}>
            <div className={styles.head}>
              <p>Whats on your mind ?</p>
            </div>
            <form>
              <textarea
                className={styles.input}
                type="text"
                rows={5}
                name="content"
                placeholder="Write a tweet..."
                value={newTweet.content}
                onChange={(e) => onChangeNewTweetForm(e)}
                required
              />
            </form>
            <div className={styles.btn}>
              <button onClick={postTweet}>Tweet</button>
            </div>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.tweetsContainer}>
            {tweets &&
              tweets.map((tweet) => {
                return (
                  <div key={tweet.id}>
                    <TweetCard tweet={tweet} />
                  </div>
                );
              })}
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Home;
