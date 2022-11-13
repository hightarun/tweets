import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import styles from "./UserBody.module.scss";
import axios from "axios";

import { useSelector, useDispatch } from "react-redux";

import { AiFillCloseSquare } from "react-icons/ai";

import { setAlert } from "../../../redux/actions/alert";

const UserBody = ({ currentUser, userData }) => {
  const dispatch = useDispatch();
  const [tweetModal, setTweetModal] = useState(false);

  const [tweets, setTweets] = useState({});

  const getAllTweets = async (username) => {
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

  useEffect(() => {
    if (userData) {
      getAllTweets(userData.username);
    }
  }, [userData]);

  const closeTweetModal = () => {
    setTweetModal(false);
  };
  return (
    <div className={styles.container}>
      {userData && console.log(tweets)}
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
      <div className={styles.content}></div>
      <Modal
        isOpen={tweetModal}
        onRequestClose={closeTweetModal}
        ariaHideApp={false}
      >
        <div className={styles.modalContainer}>
          <div className={styles.close} onClick={closeTweetModal}>
            <AiFillCloseSquare className={styles.icon} />
          </div>
          <div className={styles.formContainer}>
            <form>
              <input type="text" name="content" placeholder="Write tweet" />
            </form>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default UserBody;
