import React from "react";
import "./CarrouselCategories.scss";
import { Link } from "react-router-dom";

const CategoriaCard = ({ data, id }) => {
  return (
    <div key={id} className="card-category">
      <div className="container-img">
        <Link to={`/categoria/${data.titulo}`}>
          <img
            src={data.urlImagen}
            alt={"Imagen de la Categoria " + data.titulo}
          />
        </Link>
      </div>
      <Link to={`/categoria/${data.titulo}`}>
        <h3>{data.titulo}</h3>
        <p>{data.descripcion}</p>
      </Link>
    </div>
  );
};

export default CategoriaCard;
