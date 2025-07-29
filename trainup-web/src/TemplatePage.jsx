import { Outlet } from 'react-router-dom'
import './styles/templatepage.css'

const TemplatePage = () => {
  return (
    <div className='temp-init'>
        {/* <div className='temp-navbar'></div> */}
        <div className='temp-body'>
            <div className='temp-sidebar'></div>
            <div className='temp-content'>
                {<Outlet/>}
            </div>
        </div>
    </div>
  )
}

export default TemplatePage