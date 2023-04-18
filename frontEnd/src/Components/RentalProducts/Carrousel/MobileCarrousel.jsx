import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination, Autoplay } from 'swiper';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import './MobileCarrousel.scss';

function MobileCarrousel({ producto, imagenes }) {
  return (
    <section className="carrousel-mobile">
      <div className="carrousel-container">
        <Swiper
          autoplay={{
            delay: 2500,
            disableOnInteraction: false,
          }}
          pagination={{
            type: 'fraction',
          }}
          loop={true}
          spaceBetween={10}
          modules={[Pagination, Autoplay]}
          className="swiper-main"
        >
          {imagenes.map((item, i) => {
            return (
              <SwiperSlide key={i}>
                <img src={item.url} />
              </SwiperSlide>
            );
          })}
        </Swiper>
      </div>
    </section>
  );
}

export default MobileCarrousel;
