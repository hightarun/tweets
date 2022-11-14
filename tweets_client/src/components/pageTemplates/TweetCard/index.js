import React from "react";
import styles from "./TweetCard.module.scss";

import dateformat from "dateformat";

const TweetCard = ({ tweet }) => {
  return (
    <div className={styles.container}>
      <div className={styles.tweet}>
        <div className={styles.tweetUser}>
          <img src="../../assets/avatar.jpg" alt="avatar" />
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
        {tweet.comments.map((comment) => {
          return <div>Comment</div>;
        })}
      </div>
    </div>
  );
};

export default TweetCard;
