import React from "react";
import styles from "./Footer.module.scss";

const Footer = () => {
  return (
    <div className={styles.container}>
      <div className={styles.foot}>
        <p>
          Copyright <strong>&#xA9;</strong> Tweets
        </p>
      </div>
    </div>
  );
};

export default Footer;
