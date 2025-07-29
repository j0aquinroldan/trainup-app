import { Form, Input, InputNumber, Modal } from "antd";
import { useEffect } from "react";

const EjercicioFormModal = ({
  open,
  onCancel,
  onSubmit,
  confirmLoading,
  ejercicio,
  editedFields,
  handleFieldChange,
  form,
  isFormInvalid,
}) => {

  useEffect(() => {
    if (ejercicio) {
      form.setFieldsValue(editedFields);
    } else {
      form.resetFields();
    }
  }, [ejercicio, form, editedFields]);

  return (
    <Modal
      title={
        ejercicio
          ? `Editar ejercicio: ${ejercicio.nombre}`
          : "Crear Nuevo Ejercicio"
      }
      open={open}
      onCancel={onCancel}
      onOk={onSubmit}
      confirmLoading={confirmLoading}
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
        >
          <InputNumber min={1} />
        </Form.Item>
        <Form.Item
          label="Peso"
          name="peso"
        >
          <InputNumber min={0}/>
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
    </Modal>
  );
};

export default EjercicioFormModal;
