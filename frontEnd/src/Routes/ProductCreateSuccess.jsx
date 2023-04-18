import React from 'react';
import './ProductCreateSuccess.scss';
import ReservaIcon from '../assets/Images/reserva-exito.png';
import { Link } from 'react-router-dom';

const AdminSuccess = ({ mode }) => {
  return (
    <div className="productSuccess-container container-page">
      <div className="productSuccess-card">
        <img src={ReservaIcon} alt="Icono Exitoso" />
        <p>{`Tu propiedad se ha ${
          mode === 'create' ? 'creado' : 'modificado'
        } con éxito`}</p>
        <Link to={'/home'}>
          <button>Ok</button>
        </Link>
      </div>
    </div>
  );
};

export default AdminSuccess;
