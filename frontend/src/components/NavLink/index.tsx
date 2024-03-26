import { Link } from "react-router-dom";

type Props = {
  to: string;
  text: string;
};

const NavLink = ({ to, text }: Props) => {
  return (
    <Link to={to} className="hover:text-gray-500 font-medium">
      {text}
    </Link>
  );
};

export default NavLink;
