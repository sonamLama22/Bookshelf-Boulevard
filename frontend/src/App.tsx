import { BrowserRouter } from "react-router-dom";
import Router from "./pages/router";
import "./App.css";

function App() {
  return (
    <BrowserRouter>
      {/* <Navbar /> */}
      <Router />
    </BrowserRouter>
  );
}

export default App;
