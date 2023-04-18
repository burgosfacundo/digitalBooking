import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

import './MainForm.scss';
import useAuth from '../../Hooks/useAuth';

function UserLoginForm() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [passwordVisible, setPasswordVisible] = useState(false);

  function handleShowPassword() {
    setPasswordVisible(!passwordVisible);
  }

  const onSubmit = async (formData) => {
    const payload = JSON.stringify(formData);
    try {
      const response = await fetch(
        `${import.meta.env.VITE_BASE_API_URL}/usuarios/login`,
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: payload,
        }
      );
      if (response.ok) {
        const data = await response.json();
        const token = data.jwt;

        await login(token);

        navigate(-1);
      } else {
        setError('root.responseError', {
          type: 'custom',
          message: 'Credenciales inválidas',
        });
      }
    } catch (error) {
      console.log(error);
    }
  };

  // useForm hook
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm({ mode: 'onBlur' });

  return (
    <form
      className="main-form"
      onSubmit={(e) =>
        handleSubmit(onSubmit)(e).catch(() => {
          setError('root.responseError', {
            type: 'custom',
            message: 'Error en el servidor. Intente más tarde.',
          });
        })
      }
    >
      <div className="form-control">
        <label htmlFor="email">Email</label>
        <input
          {...register('email', {
            required: 'Campo requerido',
            pattern: {
              value:
                /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
              message: 'Email inválido',
            },
          })}
        />
        {errors.email && (
          <p className="input-error-msg">{errors.email.message}</p>
        )}
      </div>

      <div className="form-control">
        <label htmlFor="password">Contraseña</label>
        <div className="password-wrapper">
          <input
            type={!passwordVisible ? 'password' : 'text'}
            {...register('password', { required: 'Campo requerido' })}
          />
          <FontAwesomeIcon
            className="show-password-icon"
            icon={!passwordVisible ? faEye : faEyeSlash}
            onClick={handleShowPassword}
          />
        </div>
        {errors.password && (
          <p className="input-error-msg">{errors.password.message}</p>
        )}
      </div>

      {errors?.root?.responseError && (
        <div className="wrapper-error">
          <p className="input-error-msg error-form">
            {errors.root.responseError.message}
          </p>
        </div>
      )}

      <button className="button-primary button-primary--full">Ingresar</button>

      <p>
        ¿Aún no tenes cuenta?
        <span>
          <Link className="small-link" to="/register">
            Registrate
          </Link>
        </span>
      </p>
    </form>
  );
}

export default UserLoginForm;
