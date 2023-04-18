import { Navigate, useLocation } from 'react-router';
import useAuth from '../Hooks/useAuth';

function ProtectedRoutes({ children, allowedRoles }) {
  const { auth, isLoading } = useAuth();

  const location = useLocation();

  if (isLoading) return;

  if (auth?.userEmail) {
    if (allowedRoles.includes(auth.userRole)) {
      return children;
    } else {
      return <Navigate to="/unauthorized" replace={true} />;
    }
  }
  return <Navigate to="/login" replace={true} />;
}

export default ProtectedRoutes;
