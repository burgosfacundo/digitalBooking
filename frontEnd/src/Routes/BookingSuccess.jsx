import React from 'react';
import './BookingSuccess.scss';
import ReservaIcon from '../assets/Images/reserva-exito.png';
import { Link } from 'react-router-dom';

const BookingSuccess = () => {
  return (
    <div className="success-container container-page">
      <div className="success-card">
        <img src={ReservaIcon} alt="Reserva Exitosa" />
        <h2>¡Muchas gracias!</h2>
        <p>Su reserva se ha realizado con éxito</p>
        <p>¡Se envio los datos a su correo electronico!</p>
        <Link to={'/home'}>
          <button>Ok</button>
        </Link>
      </div>
    </div>
  );
};

export default BookingSuccess;
