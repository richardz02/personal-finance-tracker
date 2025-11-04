import axios, { HttpStatusCode } from "axios";
import SignupForm from "../components/SignupForm";
import { useNavigate } from "react-router-dom";

function SignupPage() {
  const navigate = useNavigate();

  // call backend api here to handle user sign up
  const handleSignup = async ({ username, password }) => {
    try {
      const response = await axios.post(
        "http://127.0.0.1:8080/api/v1/auth/signup",
        {
          username,
          password,
        }
      );

      console.log(response.data.message);

      if (response.status === HttpStatusCode.Created) {
        navigate("/login");
      }
    } catch (err) {
      if (err.response) {
        alert(err.response.data.message);
      } else {
        console.error(err);
      }
    }
  };

  return (
    <>
      <SignupForm onSubmit={handleSignup} />
    </>
  );
}

export default SignupPage;
