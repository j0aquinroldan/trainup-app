import React, { useEffect, useState } from 'react'

const ElementForm = ({ name, type, id, title, setText, initialValue, required, pattern, errorMessage }) => {

  const [valueText, setValueText] = useState('')
  const [error, setError] = useState('');


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
      <label htmlFor={type} className='bold'>{title}</label>
      <input
        className={`primary-textbar`}
        type={type}
        name={name}
        value={valueText}
        placeholder={name}
        id={id}
        errorMessage={errorMessage}
        pattern={pattern}
        required={required}
        onChange={handlerChange} />
      <span className='error-message'>{errorMessage}</span>
    </div>
  )
}

export default ElementForm