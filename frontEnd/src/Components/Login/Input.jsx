import React from 'react';
import './Input.scss';

const Input = ({ attribute, handleChange, param, value }) => {
  return (
    <div className="input-container">
      <input
        value={value}
        id={attribute.id}
        name={attribute.name}
        placeholder={attribute.placeholder}
        type={attribute.type}
        onChange={(e) => handleChange(e.target.name, e.target.value)}
        className={`input-form ${param ? 'input-error' : ''}`}
      />
    </div>
  );
};

export default Input;
