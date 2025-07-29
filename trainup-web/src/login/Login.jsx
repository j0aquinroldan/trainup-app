import { useState } from 'react'
import { useLogin } from '../context/LoginContext'
import '../styles/boxes.css'
import ElementForm from './ElementForm'
import Form from './Form'

const Login = () => {

  const {validateLogin} = useLogin()
  const [usuario, setUsuario] = useState('')
  const [password, setPassword] = useState('')

  const handlerSubmit = (e) => {
    e.preventDefault(); 
    validateLogin(usuario, password);
  };

  return (
    <div className='max-size-vh flx center '>
      <Form name='Iniciar sesion' btnName='Iniciar sesion' handlerSubmit={handlerSubmit}>
        <ElementForm 
          title='Usuario' 
          type='text' 
          id='usuario' 
          name='Usuario' 
          setText={setUsuario}/>
        <ElementForm 
          title='Contraseña' 
          type='password' 
          id='password' 
          name='password'
          setText={setPassword}/>
      </Form>
    </div>
  )
}

export default Login