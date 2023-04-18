import axios from 'axios';
import es from 'date-fns/locale/es';
import { parse, format } from 'date-fns';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import Card from '../Components/Cards/CardRentalGrid';
import './Search.scss';
import HomeSearch from '../Components/Body/HomeSearch';

function Search() {
  const [params, setParams] = useSearchParams();
  const [productos, setProductos] = useState([]);
  const [searchTitle, setSearchTitle] = useState("");

  useEffect(() => {
    const processDates = (fechaInicio, fechaFin) => {
      try {
        fechaInicio = parse(params.get("fechaInicio"), "dd/MM/yyyy", new Date());
        fechaFin = parse(params.get("fechaFin"), "dd/MM/yyyy", new Date());

        if (fechaInicio > fechaFin) {
          fechaFin = fechaInicio;
        }
      } catch (e) {
        fechaInicio = new Date();
        fechaFin = new Date();
      }

      return {
        "fechaInicio": format(fechaInicio, "dd/MM/yyyy"),
        "fechaFin": format(fechaFin, "dd/MM/yyyy"),
        "displayFechaInicio": format(fechaInicio, "dd MMM yyyy", { locale: es }),
        "displayFechaFin": format(fechaFin, "dd MMM yyyy", { locale: es })
      }
    }

    const fetchProductData = async () => {
      const ciudad = params.get("ciudad");
      const fechaInicio = params.get("fechaInicio");
      const fechaFin = params.get("fechaFin");
      let urlSearch = "";

      if (ciudad && fechaInicio && fechaFin) {
        const fechas = processDates(fechaInicio, fechaFin);
        setSearchTitle(ciudad + " en las fechas: "+ fechas.displayFechaInicio + " a " + fechas.displayFechaFin);

        urlSearch = `${import.meta.env.VITE_BASE_API_URL}/productos/filterCityFechas=${ciudad}?fechaInicio=${fechas.fechaInicio}&fechaFin=${fechas.fechaFin}`;
      } else if (ciudad) {
        setSearchTitle("Ciudad: " + ciudad);

        urlSearch = `${import.meta.env.VITE_BASE_API_URL}/productos/filterCity=${ciudad}`;
      } else {
        const fechas = processDates(fechaInicio, fechaFin);
        setSearchTitle("En las fechas: "+ fechas.displayFechaInicio + " a " + fechas.displayFechaFin);

        urlSearch = `${import.meta.env.VITE_BASE_API_URL}/productos/filter?fechaInicio=${fechas.fechaInicio}&fechaFin=${fechas.fechaFin}`;
      }

      axios(urlSearch)
        .then((res) => {
          if (typeof res.data === "string") {
            res.data = [];
          }
          setProductos(res.data);
        })
    };

    fetchProductData();
  }, []);

  return (
    <div className="search-products container-page">
      <HomeSearch isSearch={true} titleSearch={searchTitle.trim()} />
      <div className="container-main product-list">
        <div className="grid-rentals__grid">
          {productos.map((item) => {
            return (
            <Card
              key={item.id}
              id={item.id}
              imagen={item.imagenes[0].url}
              img_name={item.imagenes[0].titulo}
              categoria={item.categoria.titulo}
              titulo={item.titulo}
              ciudad={item.ciudad.nombre}
              descripcion={item.descripcion}
              caracteristicas={item.caracteristicas}
            />
            );
          })}
          {productos.length > 0 && productos.length % 2 != 0 && <div className='card-rental empty'></div>}
        </div>
      </div>
    </div>
  );
}

export default Search;