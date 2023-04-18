import './GalleryProduct.scss';
import DesktopCarrousel from './Carrousel/DesktopCarrousel';
import { useEffect, useState } from 'react';
import useWindowSize from '../../Hooks/useWindowSize';
import MobileCarrousel from './Carrousel/MobileCarrousel';

function GalleryProduct({ producto, imagenes }) {
  const [carrouselVisible, setCarrouselVisible] = useState(false);

  const [isMobile, setIsMobile] = useState(false);

  const windowSize = useWindowSize();

  useEffect(() => {
    // TODO : traer la variable del bp Tablet a React
    windowSize.width <= 768 ? setIsMobile(true) : setIsMobile(false);
  }, [windowSize]);

  const handleCloseCarrousel = () => {
    setCarrouselVisible(false);
  };

  const handleOpenCarrousel = () => {
    setCarrouselVisible(true);
  };

  return (
    <>
      {!isMobile ? (
        <>
          <section className="gallery container-main">
            {imagenes
              .sort((a, b) => a.id - b.id)
              .slice(0, 5)
              .map((item, i) => {
                return (
                  <img
                    src={item.url}
                    alt={item.titulo}
                    key={i}
                    className={i === 0 ? 'main-picture' : ''}
                  />
                );
              })}
            <button className="open-carrousel" onClick={handleOpenCarrousel}>
              Ver más
            </button>
          </section>
          {carrouselVisible && (
            <DesktopCarrousel
              handleCloseCarrousel={handleCloseCarrousel}
              producto={producto}
              imagenes={imagenes}
            />
          )}
        </>
      ) : (
        <>
          <MobileCarrousel producto={producto} imagenes={imagenes} />
        </>
      )}
    </>
  );
}

export default GalleryProduct;
