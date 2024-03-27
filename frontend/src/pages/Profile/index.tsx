import { Link, Navigate, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store/store";
import { logout } from "../../../src/redux/slices/auth";

const Profile = () => {
  const { user: currentUser } = useSelector((state: RootState) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  if (!currentUser) {
    return <Navigate to="/" />;
  }

  const logOut = () => {
    dispatch(logout());
    navigate("/");
  };

  return (
    <div className="container">
      <div>
        <header className="jumbotron">
          <h3>
            <strong>Email: {currentUser.username}</strong>
          </h3>
        </header>

        <button onClick={() => logOut()}>logout</button>
      </div>
    </div>
  );
};

export default Profile;
