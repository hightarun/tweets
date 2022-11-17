import React, { useEffect, useState } from "react";
import styles from "./UserBody.module.scss";
import axios from "axios";

import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import { setAlert } from "../../../redux/actions/alert";
import TweetCard from "../TweetCard";
import NewTweet from "../TweetCard/NewTweet";

const UserBody = ({ currentUser, userData }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [tweetModal, setTweetModal] = useState(false);

  const [newTweet, setNewTweet] = useState({ content: "" });

  const [tweets, setTweets] = useState([]);

  const authUser = useSelector((state) => state.rootReducer.auth.user);

  const refreshPage = () => {
    navigate(0);
  };

  const getAllUserTweets = async (username) => {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_TWEETS_BACKEND}/${username}`
      );
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
      setTimeout(() => {
        refreshPage();
      }, 1000);
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
    if (userData) {
      getAllUserTweets(userData.username);
    }
  }, [userData]);

  return (
    <div className={styles.container}>
      <div className={styles.banner}>
        <img src="../assets/banner.jpg" alt="banner" />
      </div>
      <div className={styles.header}>
        <div className={styles.infoContainer}>
          <div className={styles.avatar}>
            <img src="../assets/avatar.png" alt="avatar" />
          </div>
          <div className={styles.info}>
            <p>{userData && userData.firstName + " " + userData.lastName}</p>
            <i>@{userData && userData.username}</i>
          </div>
        </div>
        {currentUser ? (
          <div
            className={styles.ntweet}
            onClick={() => {
              setTweetModal(true);
            }}
          >
            <div>New Tweet</div>
          </div>
        ) : (
          <div></div>
        )}
        <NewTweet
          closeModal={setTweetModal}
          toggleModal={tweetModal}
          tweetData={tweets}
          setTweetData={setNewTweet}
          submitTweet={postTweet}
        />
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
  );
};

export default UserBody;
