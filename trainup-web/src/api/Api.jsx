import { notification } from 'antd';
import 'antd/dist/reset.css';
import axios from 'axios';

// URL base de la API 
axios.defaults.baseURL = "http://localhost:8080/api"

export const handleError = (error) => {
  let errorMessage = 'Ocurrió un error inesperado';

  if (error.response) {
    errorMessage = error.response.data?.msg || 'Error en la respuesta del servidor';
  } else if (error.request) {
    errorMessage = 'No se pudo conectar al servidor. Inténtelo de nuevo más tarde.';
  }

  notification.error({
    message: 'Error',
    description: errorMessage,
    placement: 'topRight',
  });
  throw new Error(errorMessage);
};

/*
 * Funciones relacionadas con "Rutinas"
*/

export const crearRutina = (body) => axios.post(`/rutinas`, body)
export const obtenerRutinas = (page) => axios.get(`/rutinas?page=${page}`)
export const actualizarRutina = (body) => axios.put(`/rutinas`, body)
export const eliminarRutina = (id) => axios.delete(`/rutinas/${id}`)
export const obtenerRutinaPorId = (id) => axios.get(`/rutinas/${id}`);
export const obtenerCategorias = () => axios.get(`rutinas/categorias`)
export const obtenerRutinasPorCategoria = (categoria) => axios.get(`rutinas/categoria/${categoria}`)
export const buscarRutina = (nombre, dificultad) => axios.get(`/rutinas/buscar`, { params: { nombre: nombre, dificultad: dificultad || undefined } })
export const agregarRutinaFavorita = (usuarioID, rutinaID) => axios.put(`/usuario/${usuarioID}/favorita/${rutinaID}`)


/* 
* Funciones relacionadas con "Ejercicios"
*/


export const agregarEjercicioARutina = (rutinaID, ejercicio) => axios.post(`/rutinas/${rutinaID}/ejercicios`, ejercicio);
export const completarONoEjercicio = (usuarioID, rutinaID, ejercicioID) => axios.put(`/usuario/${usuarioID}/completarONoEjercicio/${rutinaID}/${ejercicioID}`);
export const eliminarEjercicioDeRutina = (rutinaID, ejercicioID) => axios.delete(`/rutinas/${rutinaID}/ejercicios/${ejercicioID}`);
export const actualizarEjercicioEnRutina = (id, ejercicio) => axios.put(`/rutinas/${id}/ejercicio/actualizar`, ejercicio)


// const actualizarEjercicio = (ejercicio) => axios.put(`/ejercicios/actualizar`, ejercicio)

// const eliminarEjercicio = (ejercicioID) => {
//   return axios.delete(`/ejercicios/${ejercicioID}`);
// };



/*
 * Funciones de auth
 */

export const logearUsuario = (username, password) => {

  delete axios.defaults.headers.common["Authorization"]
  
  return axios.post('/auth/login', { username, password })
}

export const crearUsuario = async (usuarioDTO) => {
  try {
    const response = await axios.post(`/auth/register`, usuarioDTO);
    return response.data;
  } catch (error) {
    handleError(error);
  }
};

/*
 * Funciones relacionadas con "Usuarios"
 */

export const obtenerUsuarioPorToken = async() =>{
  try {
    const response = await axios.get(`/usuario/me`);
    return response.data;
  } catch (error) {
    handleError(error);
  }
}


export const obtenerUsuarioPorUsername = async (username) => {
  try {
    const response = await axios.get(`/usuario/username/${username}`);
    return response.data;
  } catch (error) {
    handleError(error);
  }
};

export const obtenerUsuarioPorID = (id) => axios.get(`usuario/id/${id}`)

export const obtenerUsuarios = async () => {
  try {
    const response = await axios.get(`/usuario`);
    return response.data;
  } catch (error) {
    handleError(error);
  }
};

export const actualizarUsuario = (usuarioDTO) => axios.put(`/usuario`, usuarioDTO);

export const eliminarUsuario = async (id) => {
  try {
    await axios.delete(`/${id}`);
    return;
  } catch (error) {
    handleError(error);
  }
};


export const completarRutina = (userId, rutinaId) => {
  axios.post(`/usuario/completarRutina/${userId}/${rutinaId}`).then((response) => response.data).catch(handleError);
};

export const isFollowing = (userId, rutinaID) => {
  return axios.get(`/usuario/isFollowing/${userId}/${rutinaID}`);
}

export const seguirRutina = (userId, rutinaID) => {
  return axios.put(`/usuario/follow/${userId}/${rutinaID}`);
}


