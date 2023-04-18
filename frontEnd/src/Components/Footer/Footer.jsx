import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faFacebook,
  faInstagram,
  faTwitter,
  faLinkedin,
} from '@fortawesome/free-brands-svg-icons';

import './Footer.scss';

function Footer() {
  return (
    <footer>
      <div className="container-main">
        <div className="footer__copyright">
          <p>© 2023 DIGITALBOOKING</p>
        </div>
        <div className="container__social-media">
          <a className="social-media-icon" href="#">
            <FontAwesomeIcon icon={faFacebook} />
          </a>
          <a className="social-media-icon" href="#">
            <FontAwesomeIcon icon={faInstagram} />
          </a>
          <a className="social-media-icon" href="#">
            <FontAwesomeIcon icon={faLinkedin} />
          </a>
          <a className="social-media-icon" href="#">
            <FontAwesomeIcon icon={faTwitter} />
          </a>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
