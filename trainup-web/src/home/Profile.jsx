import { notification } from 'antd';
import { useEffect, useState } from 'react';
import { actualizarUsuario } from "../api/Api";
import { useLogin } from '../context/LoginContext';
import ElementForm from '../login/ElementForm';
import Form from '../login/Form';
import '../styles/profile.css';
import Loader from '../utils/Loader';

const Profile = () => {
  const { user, setUser } = useLogin();  
  const [profileData, setProfileData] = useState({
    id: '',
    username: '',
    password: '',
    nombre: '',
    edad: '',
    fecNacimiento: '',
    telefono: '',
    genero: '',
    altura: '',
    peso: '',
    objetivo: '',
    rutinasSeguidas: [],
    rutomasCompletadas: []
  });

  const [editField, setEditField] = useState(null);
  const [editData, setEditData] = useState(profileData);
  const [errors, setErrors] = useState({});
  const showButtons = true;

  useEffect(() => {
    if (user) {
      const newProfileData = {
        id: user.id || '',
        username: user.username || '',
        password: user.password || '',
        nombre: user.nombre || '', 
        edad: user.edad || '',   
        fecNacimiento: user.fecNacimiento || '', 
        telefono: user.telefono || '', 
        genero: user.genero || '', 
        altura: user.altura || '', 
        peso: user.peso || '',  
        objetivo: user.objetivo || '',
        rutinasSeguidas: user.rutinasSeguidas || [],
        rutinasCompletadas: user.rutinasCompletadas || []
      };
      setProfileData(newProfileData);
      setEditData(newProfileData);
    }
  }, [user]);

  useEffect(() => {
    // console.log('User data:', user);
  }, [user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (editField) {
      setEditData(prevData => ({
        ...prevData,
        [name]: value,
      }));
      if (name === 'edad') {
        const yearOfBirth = new Date().getFullYear() - Math.max(0, Math.min(99, value));
        setEditData(prevData => ({
          ...prevData,
          fecNacimiento: `${yearOfBirth}-01-01`,
        }));
      }
    }
  };

  const validateFields = () => {
    const newErrors = {};
    
    // Validaciones comunes
    if (!editData.username.trim()) newErrors.username = 'El nombre de usuario no puede estar vacío';
    if (!editData.password.trim()) newErrors.password = 'La contraseña no puede estar vacía';
    if (!editData.nombre.trim()) newErrors.nombre = 'El nombre no puede estar vacío';
    if (!/^\d+$/.test(editData.telefono)) newErrors.telefono = 'El teléfono solo puede contener números';
    if (!['masculino', 'femenino'].includes(editData.genero.toLowerCase())) newErrors.genero = 'El género debe ser masculino o femenino';
    if (editData.edad < 13 || editData.edad > 99) newErrors.edad = 'La edad debe estar entre 13 y 99 años';

    const peso = parseFloat(editData.peso.replace(',', '.'));
    if (isNaN(peso) || peso < 40 || peso > 350) {
      newErrors.peso = 'El peso debe estar entre 40 kg y 500 kg';
    }

    let altura = parseFloat(editData.altura.replace(',', '.'));
    if (isNaN(altura)) {
      newErrors.altura = 'La altura debe ser un número válido';
    } else if (altura > 50) {
      altura = altura / 100; // Si es mayor que 50, lo tomamos como centímetros y lo convertimos a metros
    }

    if (altura < 0.5 || altura > 4) {
      newErrors.altura = 'La altura debe estar entre 0.5 m y 4 m';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSave = async () => {
    if (!validateFields()) {
      notification.error({
        message: 'Error de Validación',
        description: 'Por favor corrija los errores antes de continuar.',
        placement: 'topRight',
      });
      return;
    }

    try {
      await actualizarUsuario(editData);
      setProfileData(editData);
      setEditField(null);
      setUser(editData);
      notification.success({
        message: 'Actualización Exitosa',
        description: 'Tus datos se han actualizado correctamente.',
        placement: 'topRight',
      });
    } catch (error) {
      notification.error({
        message: 'Error al Actualizar',
        description: 'No se pudo actualizar tus datos. Inténtalo de nuevo más tarde.',
        placement: 'topRight',
      });
    }
  };

  const renderField = (fieldName, label, type, pattern, required=false, min) => (
    
      <ElementForm
        type={type}
        id={fieldName}
        name={fieldName}
        initialValue={editData[fieldName] || ''}
        title={label}
        required={required}
        pattern={pattern}
        min={min}
        errorMessage={`${fieldName} no puede ser vacio`}
        />
      );


  return (
    user?
    
    <div className='profile-container '>
      
          <Form name='Editar Perfil' btnName='Guardar' handlerSubmit={handleSave}>
            {renderField('username', 'Usuario', 'text', undefined, true)}
            {renderField('nombre', 'Nombre', 'text', undefined, true)}
            {renderField('fecNacimiento', 'Fecha de Nacimiento', 'date')}
            {renderField('telefono', 'Teléfono','text','[0-9]+')}
            {renderField('genero', 'Género', 'text', '^(masculino|femenino)$')}
            {renderField('altura', 'Altura (en cm)', 'number', '[0-9]+',undefined,1)}
            {renderField('peso', 'Peso (en kg)', 'number', '[0-9]+',undefined)}
            {renderField('objetivo', 'Objetivo')} 
            <button type='button' className=' default-btn-3 mb-1'>
              Cambiar contraseña
            </button>
          </Form>
      </div>
     :
     <Loader/>
  );
};

export default Profile;
