import React from 'react';
import './Title.scss';

const Title = ({ text }) => {
  return (
    <div className="title-container">
      <label className="title-label">{text}</label>
    </div>
  );
};

export default Title;
