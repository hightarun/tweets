import React from "react";
import styles from "./NewTweet.module.scss";
import Modal from "../../../Modal";

const NewTweet = ({
  closeModal,
  toggleModal,
  tweetData,
  setTweetData,
  submitTweet,
}) => {
  const onChangeTweetContent = (e) => {
    e.preventDefault();
    setTweetData({ [e.target.name]: e.target.value });
  };
  return (
    <Modal onClose={() => closeModal(false)} open={toggleModal}>
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
            value={tweetData.content}
            onChange={(e) => onChangeTweetContent(e)}
            required
          />
        </form>
        <div className={styles.btn}>
          <button onClick={submitTweet}>Tweet</button>
        </div>
      </div>
    </Modal>
  );
};

export default NewTweet;
