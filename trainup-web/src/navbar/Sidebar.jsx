import {
  faBars,
  faCheck,
  faCompass,
  faHome,
  faPlus,
  faSearch,
  faSignOutAlt,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useLogin } from "../context/LoginContext";
import "../styles/sidebar.css";

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { user, restartLogin } = useLogin();
  const [showSidebar, setShowSidebar] = useState(false);

  return (
    <div className="nes">
      <button className="nes1" onClick={() => setShowSidebar(!showSidebar)}>
        <FontAwesomeIcon icon={faBars} />
      </button>
      <div className={`sidebar ${showSidebar ? `s-show` : `s-noshow`}`}>
        <ul>
          {user?.rol === "ADMIN" && (
            <li>
              <button
                className={`sidebar-btn ${
                  location.pathname === "/es/home/crear/rutina" ? "active" : ""
                }`}
                onClick={() => navigate("/es/home/crear/rutina")}
              >
                <FontAwesomeIcon icon={faPlus} className="icon" />{" "}
                <span>Nueva Rutina</span>
              </button>
            </li>
          )}
          <li>
            <button
              className={`sidebar-btn ${location.pathname === '/es/home' ? 'active' : ''}`}
              onClick={() => {
                navigate("/es/home");
                setShowSidebar(!showSidebar);
              }}
            >
              <FontAwesomeIcon icon={faHome} className="icon" />{" "}
              <span>Inicio</span>
            </button>
          </li>
          <li>
            <button
              className={`sidebar-btn ${location.pathname === '/es/home/explorador' ? 'active' : ''}`}
              onClick={() => {
                setShowSidebar(!showSidebar);
                navigate("/es/home/explorador");
              }}
            >
              <FontAwesomeIcon icon={faCompass} className="icon" />{" "}
              <span>Explorar</span>
            </button>
          </li>
          <li>
            <button
              className={`sidebar-btn ${location.pathname === '/es/home/buscar' ? 'active' : ''}`}
              onClick={() => {
                setShowSidebar(!showSidebar);
                navigate("/es/home/buscar");
              }}
            >
              <FontAwesomeIcon icon={faSearch} className="icon" />{" "}
              <span>Buscar</span>
            </button>
          </li>
          <li>
            <button
              className={`sidebar-btn ${location.pathname === '/es/home/completadas' ? 'active' : ''}`}
              onClick={() => {
                setShowSidebar(!showSidebar);
                navigate("/es/home/completadas");
              }}
            >
              <FontAwesomeIcon icon={faCheck} className="icon" />{" "}
              <span>Completadas</span>
            </button>
          </li>
          <li>
            <button
              className={`sidebar-btn ${location.pathname === '/es/home/profile' ? 'active' : ''}`}
              onClick={() => {
                setShowSidebar(!showSidebar);
                navigate("/es/home/profile");
              }}
            >
              <FontAwesomeIcon icon={faUser} className="icon" />{" "}
              <span>Perfil</span>
            </button>
          </li>
          <li className="sidebar-logout">
            <button className="sidebar-btn" onClick={restartLogin}>
              <FontAwesomeIcon icon={faSignOutAlt} className="icon" />{" "}
              <span>Cerrar sesion</span>
            </button>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Sidebar;
