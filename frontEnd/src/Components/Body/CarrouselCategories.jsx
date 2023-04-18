import { useEffect, useState } from 'react';
import './CarrouselCategories.scss';
import CategoriaCard from './CategoriaCard';
import axios from 'axios';

function CarrouselCategories() {
  const [Categorias, setCategorias] = useState([]);
  const [error, setError] = useState();

  const ENDPOINT_GET_CATEGORIAS = `${import.meta.env.VITE_BASE_API_URL}/categoria/todas`

  useEffect(() => {
    const fetchData = async () => {
      axios(ENDPOINT_GET_CATEGORIAS)
      .then((res) => setCategorias(res.data))
    };
    fetchData();
  }, []);

  return (
    <section className="section__categories">
      <div className="container-main">
        <h2 className="text-dark section-header">
          Buscar por tipo de alojamiento
        </h2>
        <div className="container-categories">
          {Categorias.map((item) => {
            <CategoriaCard key={item.id} data={item} id={item.id} />
          })
          }
          {!error ? (
            Categorias
              .slice(0, 4)
              .map((categoria) => (
                <CategoriaCard key={categoria.id} data={categoria} id={categoria.id} />
              ))
          ) : (
            <p className="text-dark">{error}</p>
          )}
        </div>
      </div>
    </section>
  );
}

export default CarrouselCategories;
