// src/hooks/useLogOut.tsx
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { AppRoutes } from '../enums/AppRoutes';

const useLogOut = () => {
  const navigate = useNavigate();
  const logOut = () => {
    Cookies.remove('your-cookie-name'); //This is the first implementation, replace 'your-cookie-name' with the actual cookie name
    navigate(AppRoutes.HOME);
  };
  return logOut;
};

export default useLogOut;