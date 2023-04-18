import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import {
  faStar,
  faStarHalf,
  faLocationDot,
} from "@fortawesome/free-solid-svg-icons";

import "./CardRentalGrid.scss";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

const Card = ({
  id,
  imagen,
  img_name,
  categoria,
  titulo,
  ciudad,
  descripcion,
  caracteristicas,
}) => {
  const params = useParams();
  const [frase, setFrase] = useState();
  const [promedio, setPromedio] = useState();
  const [showMore, setShowMore] = useState(false);

  const promedioProducto = (reseñas) => {
    if (reseñas.length == 0) {
      setFrase("Sin Puntuación");
      setPromedio("-");
      return;
    }

    let suma = 0;

    reseñas.forEach((reseña) => {
      suma += reseña.puntuacion;
    });

    const prom = suma / reseñas.length;
    setPromedio(prom);

    if (prom >= 9.5) {
      setFrase("Excelente");
    } else if (prom >= 8.0 && prom < 9.5) {
      setFrase("Muy bueno");
    } else if (prom < 8.0 && prom >= 6.0) {
      setFrase("Bueno");
    } else {
      setFrase("Aceptable");
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      axios(
        `${import.meta.env.VITE_BASE_API_URL}/puntuaciones/idProducto=${id}`
      )
        .then((res) => {
          promedioProducto(res.data);
        })
        .catch((res) => {
          if (res.response.status == 404) {
            console.log(res);
            setFrase("Sin puntuación");
            setPromedio(null);
          }
        });
    };
    fetchData();
  }, []);

  return (
    <div key={id} className="card-rental">
      <div className="card-rental__img">
        <img src={imagen} alt={img_name} className="depto-img" />
      </div>

      <div className="card-rental__info">
        <div className="card-rental__data">
          <div className="card-rental__head">
            <div className="card-rental__head--main">
              <div className="upper-title">
                <h3 className="rental-category text-gray-light">{categoria}</h3>
                <div className="rental-rating--stars">
                  <FontAwesomeIcon icon={faStar} />
                  <FontAwesomeIcon icon={faStar} />
                  <FontAwesomeIcon icon={faStar} />
                  <FontAwesomeIcon icon={faStar} />
                  <FontAwesomeIcon icon={faStarHalf} />
                </div>
              </div>
              <h2 className="rental-name text-dark">{titulo}</h2>
            </div>
            <div className="rental-rating--score">
              <p className="score--number">{promedio}</p>
              <p className="score--description text-dark">{frase}</p>
            </div>
          </div>

          <div className="card-rental__meta">
            <p className="rental-location text-dark">
              <span>
                <FontAwesomeIcon icon={faLocationDot} />
              </span>
              <span>{ciudad}</span>
              <span className="show-map">
                <a href="#">MOSTRAR EN EL MAPA</a>
              </span>
            </p>
            <div className="rental-amenities">
              {caracteristicas &&
                caracteristicas.map((item, i) => {
                  return (
                    <img
                      key={i}
                      src={item.url}
                      className="politica-icon"
                      title={item.titulo}
                    />
                  );
                })}
            </div>
          </div>

          <div className="card-rental__body">
            <p className="text-dark">
              {showMore ? descripcion : `${descripcion.substring(0, 150)}`}
              {descripcion.length > 150 && (
                <span className="more-description">
                  ...
                  <a onClick={() => setShowMore(!showMore)}>
                    {showMore ? "leer más" : "leer menos"}.
                  </a>
                </span>
              )}
            </p>
          </div>
        </div>

        <div className="card-rental__action">
          <Link
            className="button--read-more button-primary button-primary--full"
            to={`/producto/${id}`}
          >
            Ver más
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Card;
