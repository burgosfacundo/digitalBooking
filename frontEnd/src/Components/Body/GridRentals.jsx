import Card from '../Cards/CardRentalGrid';
import { useEffect, useState } from 'react';
import axios from 'axios';

import './GridRentals.scss';

function GridRentals() {
  const ENDPOINT_GET_PRODUCTOS = `${
    import.meta.env.VITE_BASE_API_URL
  }/productos/todasRandom`;

  const [Deptos, setDeptos] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      axios(ENDPOINT_GET_PRODUCTOS).then((res) => setDeptos(res.data));
    };
    fetchData();
  }, []);

  return (
    <section className="grid-rentals">
      <div className="container-main">
        <h2 className="text-dark section-header">Recomendaciones</h2>
        <div className="grid-rentals__grid">
          {Deptos.map((item) => {
            return (
              <Card
                key={item.id}
                id={item.id}
                imagen={item.imagenes.sort((a, b) => a.id - b.id)[0].url}
                img_name={item.imagenes.sort((a, b) => a.id - b.id)[0].titulo}
                categoria={item.categoria.titulo}
                titulo={item.titulo}
                ciudad={item.ciudad.nombre}
                descripcion={item.descripcion}
                caracteristicas={item.caracteristicas}
              />
            );
          })}
        </div>
      </div>
    </section>
  );
}

export default GridRentals;
