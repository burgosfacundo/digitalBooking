import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import BannerProductTitle from '../Components/BannerProductTitle';
import Card from '../Components/Cards/CardRentalGrid';
import './CategoryProducts.scss';

function CategoryProducts() {
  const params = useParams();
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    const fetchProductData = async () => {
      axios(`${import.meta.env.VITE_BASE_API_URL}/productos/filterCat=${params.id}`)
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
    <div className="category-products container-page">
      <BannerProductTitle
        titulo={params.id}
        categoria="Categoría"
      />
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

export default CategoryProducts;