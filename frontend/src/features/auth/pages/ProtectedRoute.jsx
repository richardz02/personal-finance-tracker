import { Navigate } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";
import { ChildCare } from "@mui/icons-material";

function ProtectedRoute({ children }) {
  const { user } = useAuth();

  // If user hasn't logged in, redirect to login page
  if (!user) return <Navigate to="/login" replace />;

  // If user is present, then open access to dashboard
  return children;
}

export default ProtectedRoute;
