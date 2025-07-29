import { useLogin } from '../context/LoginContext';
import '../styles/home.css';
import Loader from '../utils/Loader';
import Favoritas from './Favoritas';
import ListRutinas from './ListRutinas';

const HomePage = () => {
  const { user } = useLogin();

  console.log(`homeuser ${user}`)

  if (!user || !user.rutinasSeguidas) {
    return (
      <div className="home-container">
        <h1 className="home-title">Bienvenido a tu página de inicio</h1>
        <Loader/>
      </div>
    );
  }

  

  return (
    <div className="home-container">
      <h1 className="home-title">Inicio</h1>
      {/* <p className="home-subtitle">Aquí encontrarás las rutinas que sigues. ¡Explora y mantente activo!</p> */}
      <div className="list-rutinas-container gap-m">
        <h1 className="ta-left">Seguidas</h1>
        <ListRutinas rutinas={user.rutinasSeguidas} esCompletada={false}/>
        <hr className="separator" />
        <h1 className="ta-left">Favoritas</h1>
        <Favoritas rutinas={user.rutinasFavoritas}/>
      </div>
    </div>
  );
};

export default HomePage;
