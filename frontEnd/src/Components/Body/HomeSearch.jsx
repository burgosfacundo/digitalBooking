import 'react-datepicker/dist/react-datepicker.css';
import './HomeSearch.scss';
import classNames from 'classnames';
import React, { useState, forwardRef } from 'react';
import es from 'date-fns/locale/es';
import { format } from 'date-fns';
import axios from 'axios';
import AsyncSelect from 'react-select/async';
import { components } from 'react-select';
import DatePicker, { registerLocale, CalendarContainer } from 'react-datepicker';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLocationDot } from '@fortawesome/free-solid-svg-icons';
import { faCalendarDay } from '@fortawesome/free-solid-svg-icons';

registerLocale('es', es);

function HomeSearch({isSearch, titleSearch}) {
  const calRef = React.useRef();
  const [dates, setDates] = useState(null);
  const [ciudad, setCiudad] = useState(null);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [isFormValid, setIsFormValid] = useState(false);
  const [showFormError, setShowFormError] = useState(false);

  const onDatepickerChange = (dates) => {
    const [start, end] = dates;
    setStartDate(start);
    setEndDate(end);
  };

  const CustomLocationControl = ({ children, ...props }) => (
    <components.Control {...props}>
      <FontAwesomeIcon icon={faLocationDot} /> {children}
    </components.Control>
  );

  const CustomLocationOption = (props) => (
    <components.Option {...props}>
      <div className="search-location-option">
        <span className="search-location-option-icon">
          <FontAwesomeIcon icon={faLocationDot} />
        </span>
        <p className="search-location-option-value">
          <span className="search-location-option-city">
            {props.data.label}
          </span>
          <br />
          {props.data.country}
        </p>
      </div>
    </components.Option>
  );

  const CustomNoOptionsMessage = (props) => (
    <components.NoOptionsMessage {...props}>
      <div className="search-location-messages">
        No se encontraron ciudades, por favor ingrese otra búsqueda.
      </div>
    </components.NoOptionsMessage>
  );

  const CustomLoadingMessage = (props) => (
    <components.LoadingMessage {...props}>
      <div className="search-location-messages">
        Cargando...
      </div>
    </components.LoadingMessage>
  );

  const CustomLoadLocationOptions = (query, callback) => {
    axios(`${import.meta.env.VITE_BASE_API_URL}/ciudades/todas`).then((response) => {
      let resultados = response.data
        .filter((data) => {
          if (query == "") {
            return true;
          }

          return data.nombre.toLowerCase().includes(query.toLowerCase());
        })
        .map(({ id, nombre, country }) => ({
          value: nombre,
          label: nombre,
          country: "Argentina",
        }))
        .sort((a, b) => a.label > b.label ? 1 : -1);

      callback(resultados);
    });
  };

  const CustomCalendarInput = forwardRef(({ value, onClick }, ref) => (
    <div className="search-dates">
      <FontAwesomeIcon icon={faCalendarDay} />
      <input
        onClick={onClick}
        ref={ref}
        value={value}
        placeholder="Check In - Check Out"
        onChange={setDates}
      />
    </div>
  ));

  const validateForm = (event) => {

    if (!startDate && !endDate && !ciudad) {
      setIsFormValid(false);
      setShowFormError(true);

      event.preventDefault();

      return false;
    }

    setIsFormValid(true);
    setShowFormError(false);

    return true;
  }

  const CustomCalendarContainer = ({ className, children }) => {
    return (
      <div className="calendar-container">
        <CalendarContainer className={className}>
          <div>{children}</div>
          <div className="calendar-footer">
            <div className="calendar-options">
              <button
                onClick={() => {
                  calRef.current.setOpen(false);
                }}
                className="button-primary button-primary--full"
              >
                Aplicar
              </button>
            </div>
          </div>
        </CalendarContainer>
      </div>
    );
  };

  return (
    <div className="home-search">
      <div className="container-main">
        <h1>Busca ofertas de casas, departamentos y mucho más</h1>
        <form className="search-by-location-date" action='busqueda'>
          <input type="hidden" name="fechaInicio" value={startDate? format(startDate, "dd/MM/yyyy") : ""} />
          <input type="hidden" name="fechaFin" value={endDate? format(endDate, "dd/MM/yyyy") : ""} />
          <AsyncSelect
            name="ciudad"
            onChange={(selected) => setCiudad(selected.value)}
            cacheOptions
            defaultOptions
            loadOptions={CustomLoadLocationOptions}
            components={{
              Control: CustomLocationControl,
              Option: CustomLocationOption,
              DropdownIndicator: () => null,
              IndicatorSeparator: () => null,
              NoOptionsMessage: CustomNoOptionsMessage,
              LoadingMessage: CustomLoadingMessage,
            }}
            placeholder="¿A dónde vamos?"
            classNames={{
              control: ({ isDisabled, isFocused }) =>
                classNames('search-location'),
              valueContainer: (state) => classNames('search-location-value'),
              menu: (state) => classNames('search-location-menu'),
              menuList: (state) => classNames('search-location-menu-list'),
              option: ({ isDisabled, isFocused, isSelected }) =>
                classNames(
                  isSelected && 'search-location-option-selected',
                  !isSelected && isFocused && 'search-location-option-focused'
                ),
            }}
          />
          <div>
            <DatePicker
              name="fecha"
              ref={calRef}
              locale="es"
              monthsShown={2}
              minDate={new Date()}
              showDisabledMonthNavigation
              onChange={onDatepickerChange}
              startDate={startDate}
              endDate={endDate}
              selectsRange
              calendarContainer={CustomCalendarContainer}
              shouldCloseOnSelect={false}
              dateFormat="dd MMM yyyy"
              customInput={<CustomCalendarInput />}
            />
          </div>
          <button className="button-primary button-primary--full" type="submit" onClick={(e) => validateForm(e)}>
            Buscar
          </button>
        </form>
        {showFormError && <p className='form-error'>Por favor ingrese la ciudad y/o las fechas</p>}
      </div>
      {isSearch && <div className='search-result-title'>
        <div className='container-main'>
          <h2 className='text-primary'>Resultados de búsqueda </h2>
          <h1 className='text-dark'>{titleSearch}</h1>
        </div>
      </div>}
    </div>
  );
}

export default HomeSearch;
