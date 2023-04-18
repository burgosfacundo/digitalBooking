import './BannerProductTitle.scss';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons';

import { Link } from 'react-router-dom';

function BannerProductTitle({ titulo, categoria }) {
  return (
    <div className="hero__products__title">
      <div className="container-main">
        <div className="hero__products__title__detail">
          {categoria && <h2>{categoria}</h2>}
          <h1>{titulo}</h1>
        </div>
        <Link to="/">
          <FontAwesomeIcon className="go-back-icon" icon={faChevronLeft} />
        </Link>
      </div>
    </div>
  );
}

export default BannerProductTitle;
