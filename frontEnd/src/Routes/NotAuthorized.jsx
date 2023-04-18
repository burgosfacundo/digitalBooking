import { MdOutlineSecurity } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';

function NotAuthorized() {
  const navigate = useNavigate();

  return (
    <div className="container-page center-content not-found">
      <MdOutlineSecurity size={150} color="#555" />
      <h1 className="not-found__title">403</h1>
      <h2 className="not-found__title">Usted no tiene acceso a esta seccion</h2>
      <button
        style={{ marginTop: '1rem' }}
        className="button-primary button-primary--full"
        onClick={() => navigate('/')}
      >
        Volver
      </button>
    </div>
  );
}

export default NotAuthorized;
