import { HiMinus } from "react-icons/hi";

function ImageInput({
  deletable,
  id,
  handleRemoveImgInput,
  register,
  errors,
  defaultValues,
}) {
  return (
    <div className="wrapper-form-control">
      {errors && errors[`imagen-${id}`] && (
        <p
          className="input-error-msg"
          style={{
            display: "block",
            position: "static",
            textAlign: "left",
            marginTop: "0",
          }}
        >
          {errors[`imagen-${id}`].message}
        </p>
      )}

      <div className="form-control">
        <div className="inputs">
          <input
            {...register(`imagenes.${id}.titulo`)}
            placeholder="Titulo de imagen"
            defaultValue={defaultValues && defaultValues[0]}
          />
          <input
            {...register(
              `imagenes.${id}.url`,
              !deletable && {
                required: {
                  value: true,
                  message: "Todo producto debe tener al menos 1 imagen",
                },
              }
            )}
            placeholder="URL de imagen"
            defaultValue={defaultValues && defaultValues[1]}
          />
        </div>

        {deletable && (
          <button type="button" onClick={() => handleRemoveImgInput(id)}>
            <HiMinus color="white" />
          </button>
        )}
      </div>
    </div>
  );
}

export default ImageInput;
