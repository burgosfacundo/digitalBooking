import React from 'react';
import './ReserveCard.scss';
import { Link } from 'react-router-dom';

const ReserveCard = ({ reserva }) => {
  return (
    <div className="reserve-card">
      <div>
        <img src={reserva.imagenes[0].url} alt={reserva.tituloProducto} />
      </div>
      <div className="reserve-details">
        <h2>{reserva.tituloProducto}</h2>
        <p>
          Check-In: <span>{reserva.fechaInicial}</span>
        </p>
        <p>
          Check-Out: <span>{reserva.fechaFinal}</span>
        </p>
        <p>
          Horario de la reserva: <span>{reserva.horaComienzo}</span>
        </p>
        <Link to={`/producto/${reserva.idProducto}`}>
          <button>Detalles</button>
        </Link>
      </div>
    </div>
  );
};

export default ReserveCard;
