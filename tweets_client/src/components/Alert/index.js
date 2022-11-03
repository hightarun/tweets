import React from "react";
import styles from "./Alert.module.scss";

import { useSelector, useDispatch } from "react-redux";

import { FaWindowClose } from "react-icons/fa";
import { removeAlert } from "../../redux/actions/alert";

const Alert = () => {
  const dispatch = useDispatch();
  const alerts = useSelector((state) => state.rootReducer.alert);
  return (
    alerts != null &&
    alerts.map((alert) => (
      <div key={alert.id} className={styles.container}>
        <div className={`${styles[alert.alertType]} ${styles.box}`}>
          <div
            className={styles.close}
            onClick={() => dispatch(removeAlert(alert.id))}
          >
            <FaWindowClose className={styles.icon} />
          </div>
          <div className={styles.title}>
            <p>{alert.title}</p>
          </div>
          <div className={styles.msg}>
            <p>{alert.msg}</p>
          </div>
        </div>
      </div>
    ))
  );
};

export default Alert;
