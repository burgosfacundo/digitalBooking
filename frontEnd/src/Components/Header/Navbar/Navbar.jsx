import { Link, useLocation } from 'react-router-dom';

import './Navbar.scss';

function Navbar() {
  const location = useLocation();

  return (
    <nav>
      <ul>
        {location.pathname === '/register' || (
          <li>
            <Link to={'/register'} className="nav-link nav-link--empty">
              Crear Cuenta
            </Link>
          </li>
        )}
        {location.pathname === '/login' || (
          <li>
            <Link to={'/login'} className="nav-link nav-link--empty">
              Iniciar sesión
            </Link>
          </li>
        )}
      </ul>
    </nav>
  );
}

export default Navbar;
