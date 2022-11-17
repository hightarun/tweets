import React, { useState, useEffect } from "react";
import styles from "./TweetCard.module.scss";
import axios from "axios";
import dateformat from "dateformat";
import moment from "moment";
import NewTweet from "./NewTweet";

import { useNavigate } from "react-router-dom";
import { setAlert } from "../../../redux/actions/alert";
import { useSelector, useDispatch } from "react-redux";

import { MdDeleteForever, MdOutlineEmojiEvents } from "react-icons/md";
import { FaRegEdit } from "react-icons/fa";

const TweetCard = ({ tweet }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [formContent, setFormContent] = useState({ content: " " });
  const [toggleComments, setToggleComments] = useState(false);
  const [toggleLike, setToggleLike] = useState(false);
  const [updateTweet, setUpdateTweet] = useState({ content: "" });
  const [editTweetModal, setEditTweetModal] = useState(false);

  const authUser = useSelector((state) => state.rootReducer.auth.user);

  const isAuthenticated = useSelector(
    (state) => state.rootReducer.auth.isAuthenticated
  );

  const refreshPage = () => {
    navigate(0);
  };

  const checkLike = () => {
    if (authUser) {
      tweet.likes.map((like) => {
        if (like.likeUserId === authUser.id) {
          return setToggleLike(true);
        }
      });
    }
  };

  useEffect(() => {
    checkLike();
  }, [tweet]);

  const onToggleComment = () => {
    setToggleComments(!toggleComments);
  };

  const onChangeReply = (e) => {
    e.preventDefault();
    setFormContent(e.target.value);
  };

  const onSubmitReply = async (e) => {
    e.preventDefault();
    const body = JSON.stringify(formContent);
    if (isAuthenticated) {
      try {
        let res = await axios({
          method: "post",
          url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/reply/${tweet.id}`,
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
    } else {
      dispatch(setAlert("Message", "Login to continue", "danger"));
    }
  };

  const onClickLike = async () => {
    if (isAuthenticated) {
      if (toggleLike) {
        try {
          let res = await axios({
            method: "delete",
            url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/like/${tweet.id}`,
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
      } else {
        try {
          let res = await axios({
            method: "post",
            url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/like/${tweet.id}`,
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
      }
    } else {
      dispatch(setAlert("Message", "Login to continue", "danger"));
    }
  };

  const onDeleteTweet = async () => {
    try {
      let res = await axios({
        method: "delete",
        url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/delete/${tweet.id}`,
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

  const onDeleteComment = async (commentId) => {
    try {
      let res = await axios({
        method: "delete",
        url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/reply/${commentId}`,
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

  const onEditTweet = async () => {
    const body = JSON.stringify(updateTweet);
    try {
      let res = await axios({
        method: "put",
        url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/update/${tweet.id}`,
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
    if (tweet) {
      setUpdateTweet({ content: tweet.content });
    }
  }, [tweet]);

  return (
    <div className={styles.container}>
      <div className={styles.tweet}>
        <div className={styles.tweetInfo}>
          <div className={styles.tweetUser}>
            <img src="../../assets/avatar.png" alt="avatar" />
            <p>{tweet.tweetUser.firstName + " " + tweet.tweetUser.lastName}</p>
            <span>{"@" + tweet.tweetUser.username}</span>
          </div>
          {authUser && authUser.id === tweet.tweetUser.user_id ? (
            <div className={styles.actions}>
              <div
                className={styles.editTweet}
                onClick={() => setEditTweetModal(true)}
              >
                <FaRegEdit />
              </div>
              <div className={styles.deleteTweet} onClick={onDeleteTweet}>
                <MdDeleteForever />
              </div>
            </div>
          ) : (
            <div></div>
          )}
          <NewTweet
            closeModal={setEditTweetModal}
            toggleModal={editTweetModal}
            tweetData={updateTweet}
            setTweetData={setUpdateTweet}
            submitTweet={onEditTweet}
          />
        </div>
        <div className={styles.time}>
          <div>
            <p>{dateformat(tweet.createTime, "mmm dS, yyyy, h:MM TT")}</p>
            {tweet.createTime !== tweet.updateTime ? (
              <p>edited -{moment(tweet.updateTime).fromNow()}</p>
            ) : (
              <p></p>
            )}
          </div>
        </div>
        <div className={styles.tweetContent}> {tweet.content}</div>
      </div>
      <div className={styles.likes}>
        <div>
          {toggleLike ? (
            <p onClick={onClickLike}>🧡</p>
          ) : (
            <p onClick={onClickLike}>🤍</p>
          )}
        </div>
        <p>{" " + tweet.likes.length} Likes</p>
      </div>
      <div className={styles.reply}>
        <div>
          <form onSubmit={(e) => onSubmitReply(e)}>
            <input
              type="text"
              required
              placeholder="Reply..."
              value={formContent.content}
              onChange={(e) => onChangeReply(e)}
            />
            <button type="submit">Reply</button>
          </form>
        </div>
      </div>
      <div className={styles.comments}>
        <p onClick={onToggleComment}>Comments {" " + tweet.comments.length}</p>
        {toggleComments &&
          tweet.comments.map((comment) => {
            return (
              <div key={comment.id} className={styles.comment}>
                <div className={styles.commentInfo}>
                  <div className={styles.commentUser}>
                    <img src="../../assets/avatar.png" alt="avatar" />
                    <p>
                      {comment.commentUser.firstName +
                        " " +
                        comment.commentUser.lastName}
                    </p>
                    <span>{"@" + comment.commentUser.username}</span>
                    <p>{" ( " + moment(comment.createTime).fromNow() + " )"}</p>
                  </div>
                  {authUser && authUser.id === comment.commentUser.id ? (
                    <div
                      className={styles.deleteComment}
                      onClick={() => onDeleteComment(comment.id)}
                    >
                      <MdDeleteForever />
                    </div>
                  ) : (
                    <div></div>
                  )}
                </div>
                <div className={styles.commentContent}>{comment.content}</div>
              </div>
            );
          })}
      </div>
    </div>
  );
};

export default TweetCard;
