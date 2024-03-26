import { routerType } from "../types/router.types";
import About from "./About";
import Home from "./Home";
import Login from "./Login";
import Signup from "./Signup";

const pagesData: routerType[] = [
  {
    path: "",
    element: <Home />,
    title: "home",
  },
  {
    path: "about",
    element: <About />,
    title: "about",
  },
  {
    path: "signup",
    element: <Signup />,
    title: "signup",
  },
  {
    path: "login",
    element: <Login />,
    title: "login",
  },
];

export default pagesData;
