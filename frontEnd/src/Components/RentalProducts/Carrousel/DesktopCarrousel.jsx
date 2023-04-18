import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Thumbs, FreeMode, Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/thumbs';
import 'swiper/css/free-mode';
import './DesktopCarrousel.scss';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

import { useState } from 'react';

function DesktopCarrousel({ handleCloseCarrousel, producto, imagenes }) {
  const [thumbsSwiper, setThumbsSwiper] = useState(null);

  return (
    <section className="carrousel-modal">
      <div className="carrousel-container">
        <div className="carrousel">
          <FontAwesomeIcon
            icon={faXmark}
            className="close-carrousel"
            onClick={handleCloseCarrousel}
          />
          <Swiper
            pagination={{
              type: 'fraction',
            }}
            loop={true}
            navigation={true}
            spaceBetween={10}
            modules={[FreeMode, Navigation, Thumbs, Pagination]}
            thumbs={{
              swiper:
                thumbsSwiper && !thumbsSwiper.destroyed ? thumbsSwiper : null,
            }}
            className="swiper-main"
          >
            {imagenes.map((item, i) => {
              return (
                <SwiperSlide>
                  <img src={item.url} key={i} />
                </SwiperSlide>
              );
            })}
          </Swiper>

          <div className="wrapper-thumbs">
            <Swiper
              onSwiper={setThumbsSwiper}
              loop={true}
              spaceBetween={10}
              slidesPerView={4}
              freeMode={true}
              watchSlidesProgress={true}
              modules={[FreeMode, Navigation, Thumbs]}
              className="swiper-thumbs"
            >
              {imagenes.map((item, i) => {
                return (
                  <SwiperSlide>
                    <img src={item.url} key={i} />
                  </SwiperSlide>
                );
              })}
            </Swiper>
          </div>
        </div>
      </div>
    </section>
  );
}

export default DesktopCarrousel;
