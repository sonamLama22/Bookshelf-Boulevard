import PostForm from "../components/Form/PostForm";
import AdminDashboard from "../containers/Admin/Dashboard";
import { routerType } from "../types/router.types";
import About from "./About";
import Home from "./Home";
import Login from "./Login";
import Profile from "./Profile";
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
  {
    path: "profile",
    element: <Profile />,
    title: "profile",
  },
  {
    path: "profile/:id",
    element: <Profile />,
    title: "profile",
  },
  {
    path: "getAllBooks",
    element: <AdminDashboard />,
    title: "getAllBooks",
  },
  {
    path: "addBook",
    element: <PostForm />,
    title: "addBook",
  },
  {
    path: "deleteBook/:id",
    element: <AdminDashboard />,
    title: "deleteBook",
  },
  {
    path: "updateBook/:id",
    element: <AdminDashboard />,
    title: "updateBook",
  },
];

export default pagesData;
