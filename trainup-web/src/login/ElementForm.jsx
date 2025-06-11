import React, { useEffect, useState } from 'react'

const ElementForm = ({ name, type, id, title, setText, initialValue, required, pattern, min, errorMessage,  }) => {

  const [valueText, setValueText] = useState('')

  useEffect(() => {
  
      setValueText(initialValue || '');
  
  }, [initialValue])


  const handlerChange = (e) => {
    const value = e.target.value;
    setValueText(value);
    setText(value);
  };

  return (
    <div className='default-box form-element'>
      <label htmlFor={type} >{title} {required && <span style={{ color: 'red' }}> *</span>}</label>
      <input
        className={`primary-textbar`}
        type={type}
        name={name}
        value={valueText}
        placeholder={name}
        id={id}
        pattern={pattern}
        min={min}
        required={required}
        onChange={handlerChange} 
        />
      {/* <span className='error-message'>{errorMessage}</span> */}
    </div>
  )
}

export default ElementForm