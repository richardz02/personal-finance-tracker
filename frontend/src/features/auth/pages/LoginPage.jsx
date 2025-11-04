import axios, { HttpStatusCode } from "axios";
import LoginForm from "../components/LoginForm";
import { useNavigate } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";

function LoginPage() {
  const navigate = useNavigate();
  const { login } = useAuth();

  // call backend api to handle user login
  const handleLogin = async ({ username, password }) => {
    try {
      const response = await axios.post(
        "http://127.0.0.1:8080/api/v1/auth/login",
        {
          username,
          password,
        }
      );

      console.log(response.data.message);

      // If login successful, access JWT from response headers
      if (response.status === HttpStatusCode.Ok) {
        const authHeader = response.headers["authorization"];
        if (authHeader && authHeader.startsWith("Bearer ")) {
          const token = authHeader.split(" ")[1];

          // set token in localStorage, and return response data to parent
          login(token, response.data);

          // Navigate to dashboard page
          navigate("/dashboard");
        }
      }
    } catch (err) {
      if (err.response) {
        console.error(err.response.data.message);
      } else {
        console.error(err);
      }
    }
  };

  return (
    <>
      <LoginForm onSubmit={handleLogin} />
    </>
  );
}

export default LoginPage;
