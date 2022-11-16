import React, { useState, useEffect } from "react";
import styles from "./TweetCard.module.scss";
import axios from "axios";
import dateformat from "dateformat";

import { useNavigate } from "react-router-dom";

import { setAlert } from "../../../redux/actions/alert";
import { useSelector, useDispatch } from "react-redux";

const TweetCard = ({ tweet }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [formContent, setFormContent] = useState({ content: "" });
  const [toggleComments, setToggleComments] = useState(true);
  const [toggleLike, setToggleLike] = useState(false);

  const authUser = useSelector((state) => state.rootReducer.auth.user);

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
      }, 2000);
    } catch (err) {
      const errors = err.response.data.errorMap; // errors array from backend

      if (errors) {
        Object.keys(errors).map((key, index) => {
          return dispatch(setAlert(key, errors[key], "danger"));
        });
      }
    }
  };

  const onClickLike = async () => {
    if (toggleLike) {
      try {
        let res = await axios({
          method: "delete",
          url: `${process.env.REACT_APP_TWEETS_BACKEND}/${authUser.username}/like/${tweet.id}`,
        });
        dispatch(setAlert("Message", res.data, "success"));
        setTimeout(() => {
          refreshPage();
        }, 2000);
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
        }, 2000);
      } catch (err) {
        const errors = err.response.data.errorMap; // errors array from backend

        if (errors) {
          Object.keys(errors).map((key, index) => {
            return dispatch(setAlert(key, errors[key], "danger"));
          });
        }
      }
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.tweet}>
        <div className={styles.tweetUser}>
          <img src="../../assets/avatar.png" alt="avatar" />
          <p>{tweet.tweetUser.firstName + " " + tweet.tweetUser.lastName}</p>
          <span>{"@" + tweet.tweetUser.username}</span>
        </div>
        <div className={styles.time}>
          <div>
            <p>{dateformat(tweet.createTime, "mmm dS, yyyy, h:MM TT")}</p>
            {tweet.createTime !== tweet.updateTime ? (
              <p>
                edited -{dateformat(tweet.updateTime, "mmm dS, yyyy, h:MM TT")}
              </p>
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
        <p>{tweet.likes.length} Likes</p>
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
        <p onClick={onToggleComment}>Comments</p>
        {toggleComments &&
          tweet.comments.map((comment) => {
            return (
              <div key={comment.id} className={styles.comment}>
                <div className={styles.commentUser}>
                  <img src="../../assets/avatar.png" alt="avatar" />
                  <p>
                    {comment.commentUser.firstName +
                      " " +
                      comment.commentUser.lastName}
                  </p>
                  <span>{"@" + comment.commentUser.username}</span>
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
