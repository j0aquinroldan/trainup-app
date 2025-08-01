import { BrowserRouter, Navigate, Route, Routes, useLocation } from 'react-router-dom'
import './App.css'
import { LoginProvider } from './context/LoginContext'
import Buscar from './home/Buscar'
import CrearEjercicio from './home/CrearEjercicio'
import CrearRutina from './home/CrearRutina'
import EditarRutina from './home/EditarRutina'
import ExploradorPage from './home/ExploradorPage'
import HomePage from './home/HomePage'
import Profile from './home/Profile'
import Rutina from './home/Rutina'
import RutinasCompletadas from './home/RutinasCompletadas'
import LandingPage from './landingPage/LandingPage'
import Login from './login/Login'
import Register from './login/Register'
import Navbar from './navbar/Navbar'
import Sidebar from './navbar/Sidebar'
import './styles/buttons.css'
import './styles/default.css'
import './styles/textbar.css'
import TemplatePage from './TemplatePage'

const Layout = () => {
  const location = useLocation()
  const hideNavbarRoutes = ['/login', '/init', '/register']
  const shouldShowSide = !hideNavbarRoutes.includes(location.pathname)
  const shouldShowNavbar = !location.pathname.startsWith('/es');


  return (
    <>
      {shouldShowNavbar && <Navbar />}
      {shouldShowSide && <Sidebar />}
    </>
  )
}

function App() {
  return (
    <BrowserRouter>
      <LoginProvider>
      <Layout/>
      <Routes>
        <Route path='/' element={ <Navigate to='/init'/>}/>
        <Route path='/init' element={<LandingPage/>} />
        <Route path='/es' element={<TemplatePage/>} >
          <Route path='home' element={<HomePage/>} />
          <Route path='home/rutina' element={<Rutina/>}/>
          <Route path='home/profile' element={<Profile/>}/>
          <Route path='home/completadas' element={<RutinasCompletadas/>}/>
          <Route path='home/crear/rutina' element={<CrearRutina/>}/>
          <Route path='home/crear/ejercicio' element={<CrearEjercicio/>}/>
          <Route path='home/explorador' element={<ExploradorPage/>}/>
          <Route path='home/rutina/editar' element={<EditarRutina/>}/>
          <Route path='home/buscar' element={<Buscar/>}/>
        </Route>
        <Route path='/login' element={<Login/>} />
        <Route path='/register' element={<Register/>} />

          <Route path='*' element={<Navigate to='/init' />} />
        </Routes>
      </LoginProvider>
    </BrowserRouter>
  )
}

export default App
