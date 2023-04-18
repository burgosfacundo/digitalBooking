import './Login.scss';

import UserLoginForm from '../Components/Form/UserLoginForm';

const Login = () => {
  return (
    <div className="container-page center-content bg-light">
      <div className="main-form__container">
        <h1 className="main-form__title">Iniciar sesión</h1>
        <UserLoginForm />
      </div>
    </div>
  );
};

export default Login;
