import React from "react";
import UserBody from "../UserBody";

const User = ({ userData }) => {
  return (
    <div>
      <UserBody currentUser={false} userData={userData} />
    </div>
  );
};

export default User;
