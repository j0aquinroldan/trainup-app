import { useNavigate } from "react-router-dom";
import ListRutinas from "./ListRutinas";

import { useLogin } from "../context/LoginContext";
import "../styles/rutinasCompletadas.css";
import Loader from "../utils/Loader";

const RutinasCompletadas = () => {
  const { user } = useLogin();
  const navigate = useNavigate();

  if (!user || !user.rutinasCompletadas) {
    return (
      <div className="rutinas-completadas-container">
        <Loader />
      </div>
    );
  }

  const rutinasCount = user.rutinasCompletadas.length;

  return (
    <div className="rutinas-completadas-container">
      <div className="header-section">
        <h1 className="rutinas-completadas-title">Rutinas Completadas</h1>
        {rutinasCount > 0 && (
          <>
            <p className="rutinas-completadas-summary">
              ¡Felicidades! Has completado{" "}
              <span className="count">{rutinasCount}</span>{" "}
              {rutinasCount === 1 ? "rutina" : "rutinas"}.
            </p>
            <button
              className="explore-btn"
              onClick={() => navigate("/es/home/inProgress")}
            >
              Ver Más Rutinas
            </button>
          </>
        )}
      </div>
      <div className="list-rutinas-container">
        <ListRutinas rutinas={user.rutinasCompletadas} esCompletada={true} />
      </div>
    </div>
  );
};

export default RutinasCompletadas;
