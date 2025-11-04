import { useState } from "react";
import AuthContext from "../contexts/AuthContext";

function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  const login = (token, userInfo) => {
    localStorage.setItem("jwt", token);
    setUser(userInfo);
  };

  const logout = () => {
    localStorage.removeItem("jwt");
    setUser(null);
  };

  const value = { user, login, logout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export default AuthProvider;
