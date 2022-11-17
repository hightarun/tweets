import React from "react";
import styles from "./UserCard.module.scss";

import { useNavigate } from "react-router-dom";

const UserCard = ({ user }) => {
  const navigate = useNavigate();

  const redirectToUser = (username) => {
    navigate(`/user/${username}`);
  };
  return (
    <div className={styles.container}>
      <div className={styles.user}>
        <div className={styles.avatar}>
          <img src="../../assets/avatar.png" alt="avatar" />
        </div>
        <div className={styles.name}>
          <p>{user.firstName + " " + user.lastName}</p>
        </div>
      </div>
      <div className={styles.link}>
        <button onClick={() => redirectToUser(user.username)}>
          @{user.username}
        </button>
      </div>
    </div>
  );
};

export default UserCard;
