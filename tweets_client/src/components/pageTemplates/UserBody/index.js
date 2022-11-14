import React, { useEffect, useState } from "react";
import styles from "./UserBody.module.scss";
import axios from "axios";

import Modal from "../../Modal";

import { useSelector, useDispatch } from "react-redux";

import { setAlert } from "../../../redux/actions/alert";
import TweetCard from "../TweetCard";

const UserBody = ({ currentUser, userData }) => {
  const dispatch = useDispatch();
  const [tweetModal, setTweetModal] = useState(false);

  const [newTweet, setNewTweet] = useState({ content: "" });

  const [tweets, setTweets] = useState([]);

  const authUser = useSelector((state) => state.rootReducer.auth.user);

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

  useEffect(() => {
    if (userData) {
      getAllUserTweets(userData.username);
    }
  }, [userData]);

  const closeTweetModal = () => {
    setTweetModal(false);
  };
  return (
    <div className={styles.container}>
      <div className={styles.banner}>
        <img src="../assets/banner.png" alt="banner" />
      </div>
      <div className={styles.header}>
        <div className={styles.infoContainer}>
          <div className={styles.avatar}>
            <img src="../assets/avatar.jpg" alt="avatar" />
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
      <Modal onClose={closeTweetModal} open={tweetModal}>
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
      </Modal>
    </div>
  );
};

export default UserBody;
