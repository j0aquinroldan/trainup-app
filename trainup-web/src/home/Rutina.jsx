import { Form } from "antd";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { obtenerRutinaPorId } from "../api/Api";
import { useLogin } from "../context/LoginContext";
import Loader from "../utils/Loader";
import Ejercicio from "./Ejercicio";
import EjercicioFormModal from "./EjercicioFormModal";
import FollowBtn from "./FollowBtn";

const Rutina = () => {
  const location = useLocation();
  const { rutinaID, nombre } = location.state || {};
  const { user } = useLogin();
  const [form] = Form.useForm();
  const [ isOpen, setIsOpen ] = useState(false);
  const [isFollowed, setIsFollowed] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [listaDeEjercicios, setListaDeEjercicios] = useState([]);
  const [dificultad, setDificultad] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [imgSrc, setImgSrc] = useState("");
  const [categoria, setCategoria] = useState("");
  const [editedFields, setEditedFields] = useState({});
  const [isCreating, setIsCreating] = useState(false);

  const handleFieldChange = (changedValues) => {
    setEditedFields((prevFields) => ({
      ...prevFields,
      ...changedValues,
    }));
  };

  const isFormInvalid = () => {
    const requiredFields = [
      "nombre",
      "descripcion",
      "musculo",
      "equipo",
      "instrucciones",
    ];
    return requiredFields.some((field) => !editedFields[field]);
  };

  const handleCreateExercise = async () => {
      try {
        await form.validateFields();
        setIsCreating(true);
        const newExercise = await agregarEjercicioARutina(editedFields);
        notification.success({
          message: "¡Éxito!",
          description: `El ejercicio "${newExercise.nombre}" ha sido creado exitosamente.`,
          placement: "topRight",
        });
        setIsModalVisible(false);
      } catch (error) {
        notification.error({
          message: "Error al crear",
          description: `No se pudo crear el ejercicio.`,
          placement: "topRight",
        });
      } finally {
        setIsCreating(false);
      }
    };

  useEffect(() => {
    obtenerRutinaPorId(rutinaID)
      .then(({ data }) => {
        setListaDeEjercicios(data.ejercicios);
        setDescripcion(data.descripcion);
        setDificultad(data.dificultad);
        setImgSrc(data.img);
        setCategoria(data.categoria);
      })
      .catch((error) => {
        console.log(error);
      });

    if (user) {
      const sigueRutina = user.rutinasSeguidas.some(
        (rutina) => rutina.id === rutinaID
      );
      setIsFollowed(sigueRutina);
      setIsLoading(false);
    }
  }, [user, rutinaID]);

  if (isLoading) {
    return (
      <div className="rutinas-completadas-container">
        <Loader />
      </div>
    );
  }


  const deleteEjercicio = (ejercicio) => {
    const ejercicios = listaDeEjercicios.filter((e) => e.id !== ejercicio.id);
    setListaDeEjercicios(ejercicios);
  };

  const updateEjercicio = (ejercicio) => {
    const ejercicios = listaDeEjercicios.map((e) => {
      if (e.id === ejercicio.id) {
        return ejercicio;
      }
      return e;
    });
    setListaDeEjercicios(ejercicios);
  };

  console.log(imgSrc);

  return (
    <div className="rutina-header">
      <div className="rutina-container">
        {/* <div className="rutina-info"> */}
        {imgSrc && (
          <img
            src={imgSrc}
            className="rutina-img"
            alt={`imagen de rutina ${nombre}`}
          />
        )}
        <h1 className="rutina-title">{nombre}</h1>
        <h2 className="rutina-subtitle">{descripcion}</h2>
        <div className="card-footer">
          <p className="category">{categoria}</p>
          <p className={"category " + `${dificultad.toLowerCase()}`}>
            {dificultad}
          </p>
        </div>

        {user.rol === "ADMIN" ? (
          <></>
        ) : (
          <FollowBtn initFollow={isFollowed} rutinaID={rutinaID} />
        )}
        {/* </div> */}
      </div>

      <div className="container-boxinfo overflow-y-auto padding-r-s flex-column">
        <div className="flex gap-m ai-center">
          <h1 className="rutina-title">Ejercicios</h1>
          {user.rol === "ADMIN" ? (
            <button onClick={() => setIsOpen(true)} className="default-btn-3">
              Agregar ejercicio
            </button>
          ) : (
            <></>
          )}
        </div>
        <EjercicioFormModal
          open={isOpen}
          onCancel={() => setIsOpen(false)}
          onSubmit={handleCreateExercise}
          confirmLoading={isCreating}
          ejercicio={null}
          editedFields={{}}
          handleFieldChange={handleFieldChange}
          form={form}
          isFormInvalid={isFormInvalid}
        />
        {listaDeEjercicios.map((ejercicio) => (
          <Ejercicio
            key={ejercicio.id}
            updateEjercicio={updateEjercicio}
            deleteEjercicio={deleteEjercicio}
            ejercicio={ejercicio}
            rutinaID={rutinaID}
          />
        ))}
      </div>
    </div>
  );
};

export default Rutina;
