import { useState } from 'react';

import { useNavigate } from 'react-router';

// TODO :

export function useForm(
  initialForm,
  validateForm,
  errorMsgOnSubmit,
  callbackSubmit
) {
  const [formData, setFormData] = useState(initialForm);
  const [errors, setErrors] = useState({});

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleBlur = (e) => {
    handleChange(e);
    setErrors(validateForm(formData));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors(validateForm(formData));

    if (Object.keys(errors).length === 0) {
      try {
        const successfulSubmit = await callbackSubmit(formData);
        if (successfulSubmit) {
          navigate('/');
        } else {
          setErrors({ ...errors, submit: errorMsgOnSubmit });
        }
      } catch (e) {
        console.log(e);
      }
    }
  };

  return {
    formData,
    errors,
    handleChange,
    handleBlur,
    handleSubmit,
  };
}
