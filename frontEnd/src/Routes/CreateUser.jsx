import '../Routes/CreateUser.scss';

import CreateUserForm from '../Components/Form/CreateUserForm';

const CreateUser = () => {
  return (
    <div className="container-page center-content bg-light">
      <div className="main-form__container">
        <h1 className="main-form__title">Crear cuenta</h1>
        <CreateUserForm />
      </div>
    </div>
  );
};

export default CreateUser;
