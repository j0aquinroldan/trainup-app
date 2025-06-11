import React from 'react'
import '../styles/boxes.css'


const Form = ({children, name, btnName, handlerSubmit}) => {
  
  return (
    <div className='secondary-box form-container' >
      <h2 className='ta-center'>{name}</h2>
        <form  onSubmit={handlerSubmit} action="">
        <div className='form-info'>
            {children}
          </div>
        <button 
          className='default-btn primary-btn' 
          type='submit'>{btnName}</button>
      </form>
    </div>
  )
}

export default Form