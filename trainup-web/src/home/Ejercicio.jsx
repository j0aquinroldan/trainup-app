import { faPenToSquare, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Form, Modal, notification } from "antd";
import { useState } from "react";
import {
  actualizarEjercicioEnRutina,
  agregarEjercicioARutina,
  completarONoEjercicio,
  eliminarEjercicioDeRutina
} from "../api/Api";
import { useLogin } from "../context/LoginContext";
import "../styles/ejercicio.css";
import EjercicioFormModal from "./EjercicioFormModal";

const Ejercicio = ({
  updateEjercicio,
  deleteEjercicio,
  ejercicio,
  rutinaID,
}) => {
  const { user, actualizarPerfilUsuario } = useLogin();
  const [isOpen, setIsOpen] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isDetailsModalVisible, setIsDetailsModalVisible] = useState(false);
  const [editedFields, setEditedFields] = useState(ejercicio || {});
  const [isUpdating, setIsUpdating] = useState(false);
  const [form] = Form.useForm();
  const [isCreating, setIsCreating] = useState(false);
  const [completado, setCompletado] = useState(ejercicio.completado);

  const showEditModal = (e) => {
    e.stopPropagation()
    setEditedFields(ejercicio);
    setIsModalVisible(true);
  };

  const showDeleteModal = (e) => {
    e.stopPropagation();
    setIsOpen(true);
  };

  const handleCancel = (e) => {
    e.stopPropagation()
    setIsModalVisible(false);
    setIsOpen(false);
  };

  const handleFieldChange = (changedValues) => {
    setEditedFields((prevFields) => ({
      ...prevFields,
      ...changedValues,
    }));
  };

  const handleSaveChanges = async () => {
    try {
      await form.validateFields();
      setIsUpdating(true);
      actualizarEjercicioEnRutina(rutinaID, editedFields);
      // actualizarEjercicio(editedFields).then(({ data }) => {
      //     updateEjercicio(data)
      // });

      notification.success({
        message: "¡Éxito!",
        description: `El ejercicio "${editedFields.nombre}" ha sido actualizado exitosamente.`,
        placement: "topRight",
      });
      setIsModalVisible(false);
    } catch (error) {
      notification.error({
        message: "Error al actualizar",
        description: `No se pudo actualizar el ejercicio.`,
        placement: "topRight",
      });
    } finally {
      setIsUpdating(false);
    }
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

  const handleDeleteExercise = (e) => {
    e.stopPropagation()
    eliminarEjercicioDeRutina(rutinaID, ejercicio.id)
      .then(() => {
        deleteEjercicio(ejercicio);
        notification.success({
          message: "¡Éxito!",
          description: `Ejercicio eliminado con éxito.`,
          placement: "topRight",
        });
      })
      .catch((error) => {
        notification.error({
          message: "Error al eliminar",
          description: `El ejercicio no pudo ser eliminado.`,
          placement: "topRight",
        });
      });
  };

  const isFormInvalid = () => {
    const fieldsTouched = form.isFieldsTouched();
    const hasErrors = form
      .getFieldsError()
      .some(({ errors }) => errors.length > 0);
    return !fieldsTouched || hasErrors;
  };

  const handleCheckboxChange = async () => {
    setCompletado(!completado);

    completarONoEjercicio(user.id, rutinaID, ejercicio.id);
    // }).catch(() => {
    //     notification.error({
    //         message: 'Error al actualizar',
    //         description: `No se pudo actualizar el estado del ejercicio "${ejercicio.nombre}".`,
    //         placement: 'topRight',
    //     });
    // })
  };

  const showDetailsModal = () => {
    if (!isDetailsModalVisible) {
    setIsDetailsModalVisible(true);}
  };

  return (
    <div
      className="exercise-container"
      onClick={showDetailsModal}
    >
      <div className="exercise-header">
        <h3>{ejercicio ? ejercicio.nombre : "Crear Nuevo Ejercicio"}</h3>
      </div>
      <div className="exercise-body">
        {ejercicio ? (
          <p>{ejercicio.descripcion}</p>
        ) : (
          <Button onClick={showEditModal}>Crear Nuevo Ejercicio</Button>
        )}
      </div>
      {ejercicio && (
        <div className="exercise-details">
          <p>
            <strong>Repeticiones:</strong> {ejercicio.repeticiones}
          </p>
          <p>
            <strong>Peso ideal:</strong> {ejercicio.peso} kg
          </p>
          <p>
            <strong>Músculo:</strong> {ejercicio.musculo}
          </p>
        </div>
      )}
      <div className="exercise-footer">
        {ejercicio && user.rol === "ADMIN" ? (
          <>
            <FontAwesomeIcon
              icon={faPenToSquare}
              className="icon edit-icon"
              onClick={showEditModal}
            />
            <FontAwesomeIcon
              icon={faTrash}
              className="icon edit-icon"
              onClick={showDeleteModal}
            />
          </>
        ) : (
          <label
            className="checkbox-container"
            onClick={(e) => e.stopPropagation()}
          >
            <input
              type="checkbox"
              checked={completado}
              onChange={handleCheckboxChange}
            />
            <span className="checkmark"></span> Completado
          </label>
        )}
      </div>

      <EjercicioFormModal
        ejercicio={ejercicio}
        open={isModalVisible}
        onCancel={handleCancel}
        onSubmit={handleSaveChanges }
        confirmLoading={isUpdating}
        editedFields={editedFields}
        handleFieldChange={handleFieldChange}
        form={form}
        isFormInvalid={isFormInvalid}
      />

      {/* <Modal
        title={
          ejercicio
            ? `Editar ejercicio: ${ejercicio.nombre}`
            : "Crear Nuevo Ejercicio"
        }
        open={isModalVisible}
        onCancel={handleCancel}
        onOk={ejercicio ? handleSaveChanges : handleCreateExercise}
        confirmLoading={isUpdating || isCreating}
        okButtonProps={{ disabled: isFormInvalid() }}
        className="custom-modal"
        modalRender={(modal) => (
        <div onClick={(e) => e.stopPropagation()}>{modal}</div>
        )}
      >
        <Form
          layout="vertical"
          initialValues={editedFields}
          onValuesChange={(changedValues) => handleFieldChange(changedValues)}
          form={form}
        >
          <Form.Item
            label="Nombre"
            name="nombre"
            rules={[
              { required: true, message: "El nombre no puede estar vacío." },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Descripción"
            name="descripcion"
            rules={[
              {
                required: true,
                message: "La descripción no puede estar vacía.",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Repeticiones"
            name="repeticiones"
            rules={[
              {
                validator: (_, value) => {
                  if (value !== undefined && value !== null) {
                    if (value <= 0) {
                      return Promise.reject(
                        new Error("Las repeticiones deben ser mayores a 0.")
                      );
                    }
                  }
                  return Promise.resolve();
                },
              },
            ]}
          >
            <Input type="number" />
          </Form.Item>
          <Form.Item
            label="Peso"
            name="peso"
            rules={[
              {
                validator: (_, value) => {
                  if (value !== undefined && value !== null) {
                    if (value < 0) {
                      return Promise.reject(
                        new Error("El peso no puede ser negativo.")
                      );
                    }
                  }
                  return Promise.resolve();
                },
              },
            ]}
          >
            <Input type="number" />
          </Form.Item>
          <Form.Item
            label="Músculo"
            name="musculo"
            rules={[
              { required: true, message: "El músculo no puede estar vacío." },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Equipo"
            name="equipo"
            rules={[
              { required: true, message: "El equipo no puede estar vacío." },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Instrucciones"
            name="instrucciones"
            rules={[
              {
                required: true,
                message: "Las instrucciones no pueden estar vacías.",
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Form>
      </Modal> */}
      <Modal
        title="Confirmar acción"
        open={isOpen}
        onOk={handleDeleteExercise}
        onCancel={handleCancel}
        okText="Eliminar"
        cancelText="Cancelar"
        className="custom-modal"
        modalRender={(modal) => (
        <div onClick={(e) => e.stopPropagation()}>{modal}</div>
        )}
      >
        <p>¿Estás seguro de que deseas eliminar el ejercicio?</p>
      </Modal>
      <Modal
        title={<h1>{ejercicio ? `${ejercicio.nombre}` : ""}</h1>}
        open={isDetailsModalVisible}
        onCancel={() => setIsDetailsModalVisible(false)}
        footer={null}
        className="custom-modal"
        modalRender={(modal) => (
        <div onClick={(e) => e.stopPropagation()}>{modal}</div>
        )}
      >
        <p>
          <strong>Descripcion:</strong> {ejercicio.descripcion}
        </p>
        <p>
          <strong>Repeticiones:</strong> {ejercicio.repeticiones}
        </p>
        <p>
          <strong>Peso ideal:</strong> {ejercicio.peso} kg
        </p>
        <p>
          <strong>Músculo:</strong> {ejercicio.musculo}
        </p>
        <p>
          <strong>Series:</strong> {ejercicio.series}
        </p>
        <p>
          <strong>Segundos de descanso:</strong> {ejercicio.descansoSegundos}
        </p>
        <p>
          <strong>Equipo:</strong> {ejercicio.equipo}
        </p>
        <p>
          <strong>Instrucciones:</strong> {ejercicio.instrucciones}
        </p>

        <div className="exercise-footer">
          {ejercicio && user.rol === "ADMIN" ? (
            <>
              <FontAwesomeIcon
                icon={faPenToSquare}
                className="icon edit-icon"
                onClick={showEditModal}
              />
              <FontAwesomeIcon
                icon={faTrash}
                className="icon edit-icon"
                onClick={showDeleteModal}
              />
            </>
          ) : (
            <label className="checkbox-container">
              <input
                type="checkbox"
                checked={completado}
                onChange={handleCheckboxChange}
              />
              <span className="checkmark"></span> Completado
            </label>
          )}
        </div>
      </Modal>
    </div>
  );
};

export default Ejercicio;
