import React from "react";
import styles from "./Layout.module.scss";

import Navbar from "../Navbar";
import Footer from "../Footer";
import Alert from "../Alert";

const Layout = ({ children }) => {
  return (
    <div className={styles.container}>
      <Alert />
      <Navbar />
      {children}
      <Footer />
    </div>
  );
};

export default Layout;
