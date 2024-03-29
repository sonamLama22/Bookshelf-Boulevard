import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

const register = (username: string, email: string, password: string) => {
  return axios.post(API_URL + "register", {
    username,
    email,
    password,
  });
};

const login = (email: string, password: string, id: number) => {
  return axios
    .post(API_URL + "login", {
      email,
      password,
      id,
    })
    .then((response) => {
      if (response.data.email) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
  return axios.post(API_URL + "logout").then((res) => {
    return res.data;
  });
};

const getCurrentUser = () => {
  const user = localStorage.getItem("user");
  if (user) return JSON.parse(user);
  return null;
};

const AuthService = {
  register,
  login,
  logout,
  getCurrentUser,
};

export default AuthService;
