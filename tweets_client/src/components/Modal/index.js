import React, { Fragment } from "react";
import styles from "./modal.module.scss";

//icons
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faWindowClose } from "@fortawesome/free-solid-svg-icons";

const index = ({ open, children, onClose }) => {
  if (!open) return null;
  return (
    <Fragment>
      <div className={styles.overlay} />
      <div className={styles.modal}>
        <div className={styles.close} onClick={onClose}>
          <FontAwesomeIcon className={styles.icon} icon={faWindowClose} />
        </div>
        {children}
      </div>
    </Fragment>
  );
};

export default index;
