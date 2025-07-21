import React, { createContext, useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { logearUsuario, actualizarUsuario, obtenerUsuarioPorID, obtenerUsuarioPorToken } from '../api/Api';
import { notification } from 'antd';
import 'antd/dist/reset.css';
import axios from 'axios';

const LoginContext = createContext();

export const LoginProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [authChecked, setAuthChecked] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    
    if (storedToken) {

      axios.defaults.headers.common["Authorization"] = `Bearer ${storedToken}`

      obtenerUsuarioPorToken(storedToken)
        .then(( data ) => {
          console.log(`stored user ${data}`)
          console.log(`stored token ${storedToken}`)
          setUser(data);
        })
        .catch(() => {
          localStorage.clear(); 
          navigate('/init'); 
        })
        .finally(() => {
          setAuthChecked(true); 
        });
    } else {
      setAuthChecked(true); 
    }
  }, [navigate]);

  const validateLogin = async (username, password) => {

    try{
      const {data} = await logearUsuario(username, password);
      const {token} = data;
      
      localStorage.setItem('token', token);
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`

      const user = await obtenerUsuarioPorToken()
      localStorage.setItem('username', user.username)
      setUser(user);
      console.log(`usuario = ${user}`)
      
      navigate('/es/home');

    }catch(e) {
        console.log(e)
        notification.error({
          message: 'Error de Autenticación',
          description: 'El nombre de usuario o la contraseña son incorrectos.',
          placement: 'topRight',
        });
      };
  };

  const restartLogin = () => {
    delete axios.defaults.headers.common["Authorization"]
    setUser(null);
    localStorage.clear();
    navigate('/init');
    notification.success({
      message: 'Sesión Cerrada',
      description: 'Has cerrado la sesión correctamente.',
      placement: 'topRight',
    });
  };

  const actualizarPerfilUsuarioTemp = (datos) => {
    setUser(datos);
  }

  const actualizarPerfilUsuario = (datos) => {
    actualizarUsuario(datos).then(({datos}) => {
      setUser(datos)
    }).catch((error) => {
      notification.error({
        message: 'Error al Actualizar',
        description: 'No se pudo actualizar tus datos. Inténtalo de nuevo más tarde.',
        placement: 'topRight',
      });
    });
  };

  return (
    <LoginContext.Provider value={{ user, setUser, restartLogin, validateLogin, actualizarPerfilUsuario, actualizarPerfilUsuarioTemp, authChecked }}>
      {children}
    </LoginContext.Provider>
  );
};

export const useLogin = () => useContext(LoginContext);
