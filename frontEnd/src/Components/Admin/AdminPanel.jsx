import "./AdminPanel.scss";
import "../Form/MainForm.scss";

import { HiPlusCircle } from "react-icons/hi";

import { v4 as uuidv4 } from "uuid";

import BannerProductTitle from "../BannerProductTitle";
import useLocalStorage from "../../Hooks/useLocalStorage";
import { useEffect, useRef, useState } from "react";
import ImageInput from "./ImageInput";
import MultiSearch from "./MultiSearch";
import { useForm } from "react-hook-form";
import { Oval } from "react-loader-spinner";
import AdminSuccess from "../../Routes/ProductCreateSuccess";

function AdminPanel({ mode }) {
  const { storedValue } = useLocalStorage("token", null);
  const [amenities, setAmenities] = useState(null);
  const [categories, setCategories] = useState(null);
  const [cities, setCities] = useState(null);
  const [imagesData, setImagesData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const requiredImgId = useRef(uuidv4());
  const [isSubmitSuccess, setIsSubmitSuccess] = useState(false);

  const { storedValue: token } = useLocalStorage("token", null);

  const [selectedRental, setSelectedRental] = useState(null);
  const [defaultFormData, setDefaultFormData] = useState({
    titulo: "",
    descripcion: "",
    latitud: "",
    longitud: "",
    ciudad: "",
    categoria: "",
    direccion: "",
    caracteristicas: [],
    normas: "",
    saludYseguridad: "",
    cancelacion: "",
  });

  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
    reset,
  } = useForm({ mode: "onBlur", defaultValues: defaultFormData });

  const onSubmit = async (formData) => {
    setIsLoading(true);

    let payloadProduct = {
      titulo: formData.titulo,
      descripcion: formData.descripcion,
      direccion: formData.direccion,
      latitud: formData.latitud,
      longitud: formData.longitud,
      saludYseguridad: formData.saludYseguridad,
      cancelacion: formData.cancelacion,
      normas: formData.normas,
      idCiudad: Number(formData.ciudad),
      idCategoria: Number(formData.categoria),
      caracteristicas: formData.caracteristicas.map((idCaracteristica) =>
        Number(idCaracteristica)
      ),
      imagenes: Object.keys(formData.imagenes).map((imageId) => ({
        id: imageId,
        titulo: formData.imagenes[imageId].titulo,
        url: formData.imagenes[imageId].url,
      })),
    };

    if (mode === "create") {
      const response = await fetch(
        `${import.meta.env.VITE_BASE_API_URL}/productos/crear`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-type": "application/json",
          },
          body: JSON.stringify(payloadProduct),
        }
      );

      if (response.ok) {
        setIsLoading(false);
        setIsSubmitSuccess(true);
      }
    } else {
      let payload = {
        ...payloadProduct,
        imagenes: imagesData,
        id: selectedRental.id,
      };

      await fetch(`${import.meta.env.VITE_BASE_API_URL}/productos/actualizar`, {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-type": "application/json",
        },
        body: JSON.stringify(payload),
      });
      setIsLoading(false);
      setIsSubmitSuccess(true);
    }
  };

  useEffect(() => {
    if (mode === "modify") {
      setImagesData(selectedRental?.imagenes);
    }
  }, [selectedRental]);

  const handleAddImgInput = () => {
    setImagesData([...imagesData, { titulo: "", url: "" }]);
  };

  const handleRemoveImgInput = (id) => {
    setImagesData(imagesData.filter((imageData) => imageData.id !== id));
  };

  const getAmenities = async (token) => {
    try {
      const response = await fetch(
        `${import.meta.env.VITE_BASE_API_URL}/caracteristicas/todas`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-type": "application/json",
          },
        }
      );

      if (response.ok) {
        const data = await response.json();
        setAmenities(data);
      } else {
        return null;
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getAmenities(storedValue);
  }, []);

  useEffect(() => {
    const getCategories = async () => {
      const response = await fetch(
        `${import.meta.env.VITE_BASE_API_URL}/categoria/todas`
      );
      const data = await response.json();
      setCategories(data);
    };

    const getCities = async () => {
      const response = await fetch(
        `${import.meta.env.VITE_BASE_API_URL}/ciudades/todas`
      );
      const data = await response.json();
      setCities(data);
    };

    getCategories();
    getCities();
  }, []);

  useEffect(() => {
    reset({
      titulo: selectedRental?.titulo,
      descripcion: selectedRental?.descripcion,
      latitud: selectedRental?.latitud,
      longitud: selectedRental?.longitud,
      ciudad: selectedRental?.ciudad?.id,
      categoria: selectedRental?.categoria?.id,
      direccion: selectedRental?.direccion,
      caracteristicas: selectedRental?.caracteristicas?.map((caracteristica) =>
        caracteristica.id.toString()
      ),
      normas: selectedRental?.normas,
      saludYseguridad: selectedRental?.saludYseguridad,
      cancelacion: selectedRental?.cancelacion,
    });
  }, [selectedRental]);

  useEffect(() => {
    reset(defaultFormData);
    setImagesData([]);
    setSelectedRental(null);
  }, [mode]);

  if (isLoading) {
    return (
      <div className="container-page center-content">
        <Oval
          height={80}
          width={80}
          color="rgb(28, 191, 180)"
          wrapperStyle={{}}
          wrapperClass=""
          visible={true}
          ariaLabel="oval-loading"
          secondaryColor="rgb(28, 191, 180)"
          strokeWidth={5}
          strokeWidthSecondary={5}
        />
      </div>
    );
  }

  if (isSubmitSuccess) {
    return <AdminSuccess mode={mode} />;
  }

  return (
    <div className="container-page">
      <BannerProductTitle titulo={"Administración"} />
      <div className="admin-rental-wrapper">
        <div className="container-main">
          <div className="heading-containter">
            <h1 className="section-title">
              {mode === "create" ? "Crear propiedad" : "Modificar propiedad"}
            </h1>
            {mode === "modify" && (
              <MultiSearch setSelectedRental={setSelectedRental} />
            )}
          </div>
          <div className="section-wrapper">
            <form
              className="main-form__container"
              onSubmit={(e) =>
                handleSubmit(onSubmit)(e).catch(() => {
                  setError("root.responseError", {
                    type: "custom",
                    message: "Error en el servidor. Intente más tarde.",
                  });
                })
              }
            >
              <section className="rental__general">
                <div className="form-control">
                  <label htmlFor="titulo">Nombre de la propiedad</label>
                  <input
                    {...register("titulo", {
                      required: "Campo requerido",
                    })}
                  />

                  {errors.titulo && (
                    <p className="input-error-msg">{errors.titulo.message}</p>
                  )}
                </div>

                <div className="form-control">
                  <label htmlFor="categoria">Categoría</label>
                  <select
                    {...register("categoria", {
                      required: "Campo requerido",
                    })}
                    defaultValue={""}
                  >
                    <option value="" disabled hidden></option>
                    {categories?.map((category) => (
                      <option key={category.id} value={category.id}>
                        {category.titulo}
                      </option>
                    ))}
                  </select>

                  {errors.categoria && (
                    <p className="input-error-msg">
                      {errors.categoria.message}
                    </p>
                  )}
                </div>

                <div className="form-control">
                  <label htmlFor="direccion">Dirección</label>
                  <input
                    {...register("direccion", {
                      required: "Campo requerido",
                    })}
                  />

                  {errors.direccion && (
                    <p className="input-error-msg">
                      {errors.direccion.message}
                    </p>
                  )}
                </div>

                <div className="form-control">
                  <label htmlFor="ciudad">Ciudad</label>
                  <select
                    {...register("ciudad", {
                      required: "Campo requerido",
                    })}
                    defaultValue={""}
                  >
                    <option value="" disabled hidden></option>
                    {cities?.map((city) => (
                      <option key={city.id} value={city.id}>
                        {city.nombre}
                      </option>
                    ))}
                  </select>

                  {errors.ciudad && (
                    <p className="input-error-msg">{errors.ciudad.message}</p>
                  )}
                </div>

                <div className="form-control">
                  <label htmlFor="latitud">Latitud</label>
                  <input
                    {...register("latitud", {
                      required: "Campo requerido",
                      valueAsNumber: true,
                    })}
                  />

                  {errors.latitud && (
                    <p className="input-error-msg">{errors.latitud.message}</p>
                  )}
                </div>

                <div className="form-control">
                  <label htmlFor="longitud">Longitud</label>
                  <input
                    {...register("longitud", {
                      required: "Campo requerido",
                      valueAsNumber: true,
                    })}
                  />

                  {errors.longitud && (
                    <p className="input-error-msg">{errors.longitud.message}</p>
                  )}
                </div>

                <div className="form-control">
                  <label htmlFor="descripcion">Descripción</label>
                  <textarea
                    {...register("descripcion", {
                      required: "Campo requerido",
                      minLength: {
                        value: 20,
                        message: "Al menos 20 caracteres",
                      },
                    })}
                  ></textarea>

                  {errors.descripcion && (
                    <p className="input-error-msg">
                      {errors.descripcion.message}
                    </p>
                  )}
                </div>
              </section>

              <section className="rental__properties">
                <h2 className="section-title">
                  {mode === "create"
                    ? "Agregar atributos"
                    : "Modificar atributos"}
                </h2>
                <div className="amenities-wrapper">
                  {amenities &&
                    amenities
                      .sort((a, b) => a.titulo.localeCompare(b.titulo))
                      .map((amenity) => {
                        return (
                          <div key={amenity.id} className="form-control">
                            <input
                              type="checkbox"
                              {...register("caracteristicas")}
                              value={amenity.id}
                            />
                            <label htmlFor="caracteristicas">
                              {amenity.titulo}
                            </label>
                          </div>
                        );
                      })}
                </div>
              </section>

              <section className="rental__policies">
                <h2 className="section-title">Políticas del producto</h2>
                <div className="policies__container">
                  <div className="policy__container">
                    <h3>Normas de la casa</h3>
                    <p>Descripción</p>
                    <textarea {...register("normas")}></textarea>
                  </div>
                  <div className="policy__container">
                    <h3>Salud y seguridad</h3>
                    <p>Descripción</p>
                    <textarea {...register("saludYseguridad")}></textarea>
                  </div>
                  <div className="policy__container">
                    <h3>Política de cancelación</h3>
                    <p>Descripción</p>
                    <textarea {...register("cancelacion")}></textarea>
                  </div>
                </div>
              </section>

              <section className="rental__images">
                <h2 className="section-title">
                  {mode === "create"
                    ? "Cargar imágenes "
                    : "Modificar imágenes "}
                  <span>
                    <HiPlusCircle
                      color="rgb(28, 191, 180)"
                      cursor="pointer"
                      size={30}
                      onClick={handleAddImgInput}
                    />
                  </span>
                </h2>

                {mode === "create" ? (
                  <>
                    <ImageInput
                      id={requiredImgId.current}
                      errors={errors}
                      register={register}
                    />

                    {imagesData?.map((imageData) => (
                      <ImageInput
                        key={imageData.id}
                        id={imageData.id}
                        handleRemoveImgInput={handleRemoveImgInput}
                        deletable
                        register={register}
                      />
                    ))}
                  </>
                ) : (
                  <>
                    {imagesData
                      ?.sort((a, b) => a.id - b.id)
                      .map((imageData, index) => {
                        if (index === 0) {
                          return (
                            <ImageInput
                              key={imageData.id}
                              id={imageData.id}
                              errors={errors}
                              register={register}
                              defaultValues={[imageData.titulo, imageData.url]}
                            />
                          );
                        }

                        return (
                          <ImageInput
                            key={imageData.id}
                            id={imageData.id}
                            handleRemoveImgInput={handleRemoveImgInput}
                            deletable
                            register={register}
                            defaultValues={[imageData.titulo, imageData.url]}
                          />
                        );
                      })}
                  </>
                )}
              </section>

              <button
                className="button-primary button-primary--full"
                type="submit"
              >
                {mode === "create" ? "Crear" : "Modificar"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AdminPanel;
