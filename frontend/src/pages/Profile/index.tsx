import { Link, Navigate, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store/store";
import { logout } from "../../../src/redux/slices/auth";
import Header from "../../components/Header";
import AdminDashboard from "../../containers/Admin/Dashboard";
import UserDashboard from "../../containers/User/Dashboard";
import { BsFillCartDashFill, BsNutFill } from "react-icons/bs";

const Profile = () => {
  const { user: currentUser } = useSelector((state: RootState) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const logOut = () => {
    dispatch(logout());
    navigate("/");
  };

  if (!currentUser) {
    return <Navigate to="/" />;
  }

  return (
    <div>
      {currentUser.role === "ADMIN" ? (
        <div>
          <Header
            username={currentUser.email.split("@")[0]}
            logout={logOut}
            cartIcon={null}
          />
          <AdminDashboard />
        </div>
      ) : (
        <div>
          <Header
            username={currentUser.email.split("@")[0]}
            logout={logOut}
            cartIcon={<BsFillCartDashFill />}
          />
          <UserDashboard />
        </div>
      )}
    </div>
  );
};

export default Profile;
