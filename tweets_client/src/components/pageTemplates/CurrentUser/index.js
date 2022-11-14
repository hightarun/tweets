import React from "react";
import UserBody from "../UserBody";

const CurrentUser = ({ userData }) => {
  return (
    <div>
      <UserBody currentUser={true} userData={userData} />
    </div>
  );
};

export default CurrentUser;
