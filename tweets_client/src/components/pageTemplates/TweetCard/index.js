import React from "react";
import styles from "./TweetCard.module.scss";

import dateformat from "dateformat";

const TweetCard = ({ tweet }) => {
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
      <div className={styles.comments}>
        <p>Comments</p>
        {tweet.comments.map((comment) => {
          return (
            <div key={comment.id} className={styles.comment}>
              <div className={styles.commentUser}>
                <img src="../../assets/avatar.png" alt="avatar" />
                <p>
                  {tweet.tweetUser.firstName + " " + tweet.tweetUser.lastName}
                </p>
                <span>{"@" + tweet.tweetUser.username}</span>
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
