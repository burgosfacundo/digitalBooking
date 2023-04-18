import jwt_decode from 'jwt-decode';

import { createContext, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import useLocalStorage from '../Hooks/useLocalStorage';

export const AuthContext = createContext();

export const AuthContextProvider = ({ children }) => {
  const [auth, setAuth] = useState({});
  const [isLoading, setIsLoading] = useState(true);

  const location = useLocation();

  const {
    storedValue: tokenStored,
    updateStorage: updateToken,
    removeFromStorage: removeTokenFromStorage,
  } = useLocalStorage('token', null);

  const tokenIsBase64Encoded = (token) => {
    try {
      jwt_decode(token);
      return true;
    } catch (error) {
      return false;
    }

    return true;
  };

  const tokenHasValidInfo = (tokenDecoded) => {
    const requiredKeys = ['sub', 'apellido', 'nombre', 'exp', 'iat'];
    return requiredKeys.every((requiredKey) =>
      Object.keys(tokenDecoded).includes(requiredKey)
    );
  };

  const tokenIsNotExpired = (tokenDecoded) => {
    return tokenDecoded.exp > Date.now() / 1000;
  };

  const isValidToken = (token) => {
    if (tokenIsBase64Encoded(token)) {
      const tokenDecoded = jwt_decode(token);
      if (tokenHasValidInfo(tokenDecoded) && tokenIsNotExpired(tokenDecoded)) {
        return true;
      }
    }
    return false;
  };

  const getUserRole = async (userEmail, token) => {
    try {
      const response = await fetch(
        `${import.meta.env.VITE_BASE_API_URL}/usuarios/email=${userEmail}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-type': 'application/json',
          },
        }
      );

      if (response.ok) {
        const data = await response.json();

        return data.rol.id;
      }

      return null;
    } catch (error) {
      console.log(error);
    }
  };

  const login = async (token) => {
    if (!isValidToken(token)) {
      logout();
      return;
    }
    const tokenDecoded = jwt_decode(token);

    const authInfo = {
      userId: tokenDecoded.id,
      userEmail: tokenDecoded.sub,
      userName: tokenDecoded.nombre,
      userLastName: tokenDecoded.apellido,
    };
    authInfo.userRole = await getUserRole(authInfo.userEmail, token);
    setAuth(authInfo);

    setIsLoading(false);
    updateToken(token);
  };

  const logout = () => {
    setAuth({});
    setIsLoading(false);
    removeTokenFromStorage();
  };

  useEffect(() => {
    !isValidToken(tokenStored) && logout();
  }, [location]);

  useEffect(() => {
    login(tokenStored);
  }, []);

  return (
    <AuthContext.Provider value={{ auth, login, logout, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
};
