import Calendar from 'react-calendar';
import { eachDayOfInterval, parse } from 'date-fns';
import './CustomCalendar.scss';

import { FaChevronLeft, FaChevronRight } from 'react-icons/fa';
import { useCallback, useEffect, useState } from 'react';

import useWindowSize from '../../Hooks/useWindowSize';

function CustomCalendar({
  disabled,
  allowRange,
  valueDateRange,
  setValueDateRange,
  productData,
}) {
  const [actualDate, setActualDate] = useState(null);
  const [nextDate, setNextDate] = useState(null);
  const [node, setNode] = useState(null);
  const [bookedDates, setBookedDates] = useState([]);

  const calendarRef = useCallback((node) => {
    if (node) {
      setNode(node);
    }
  });

  // TODO : Traer el valor de los bp de sass a react
  const windowSize = useWindowSize();

  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    windowSize.width <= 768 ? setIsMobile(true) : setIsMobile(false);
  }, [windowSize]);

  useEffect(() => {
    setActualDate(
      node?.querySelector('.react-calendar__navigation__label__labelText--from')
        .textContent
    );
    setNextDate(
      node?.querySelector('.react-calendar__navigation__label__labelText--to')
        .textContent
    );
  }, [node]);

  const handleGoToNextMonth = () => {
    node?.querySelector('.react-calendar__navigation__next-button').click();
  };

  const handleGoToPrevMonth = () => {
    node?.querySelector('.react-calendar__navigation__prev-button').click();
  };

  const onChange = (nextValue) => {
    if (disabled) return;

    const datesInRange = eachDayOfInterval({
      start: nextValue[0],
      end: nextValue[1],
    });

    if (
      datesInRange.some((dateSelected) =>
        bookedDates.find(
          (bookedDate) => bookedDate.getTime() === dateSelected.getTime()
        )
      )
    ) {
      alert(
        'El producto seleccionado ya se encuentra reservado en esas fechas'
      );
      return;
    }

    setValueDateRange(nextValue);
  };

  const onActiveStartDateChange = () => {
    setActualDate(
      node?.querySelector('.react-calendar__navigation__label__labelText--from')
        .textContent
    );

    !isMobile &&
      setNextDate(
        node?.querySelector('.react-calendar__navigation__label__labelText--to')
          .textContent
      );
  };

  const getAllBookedDates = (productData) => {
    const bookedDates = [];
    if (productData?.reservas) {
      productData?.reservas.forEach((booking) => {
        bookedDates.push(
          ...eachDayOfInterval({
            start: parse(booking.fechaInicial, 'dd/MM/yyyy', new Date()),
            end: parse(booking.fechaFinal, 'dd/MM/yyyy', new Date()),
          })
        );
      });
    }
    return bookedDates;
  };

  useEffect(
    () => setBookedDates(getAllBookedDates(productData)),
    [productData]
  );

  const disableBookedDates = ({ date }) => {
    if (
      date.getTime() < Date.now() ||
      bookedDates.find((bookedDate) => bookedDate.getTime() === date.getTime())
    ) {
      return 'date-booked';
    }
  };

  return (
    <div className="calendar">
      <div className="months-container">
        <h5>
          {actualDate && actualDate[0].toUpperCase() + actualDate.slice(1)}
        </h5>
        {!isMobile && (
          <h5>{nextDate && nextDate[0].toUpperCase() + nextDate.slice(1)}</h5>
        )}
      </div>
      <div className="calendar-container">
        <button onClick={handleGoToPrevMonth}>
          <FaChevronLeft />
        </button>

        <Calendar
          showNavigation={true}
          value={valueDateRange}
          onChange={onChange}
          locale="es"
          formatShortWeekday={(locale, date) =>
            date.toLocaleDateString(locale, { weekday: 'narrow' })
          }
          showDoubleView={!isMobile ? true : false}
          inputRef={calendarRef}
          onActiveStartDateChange={onActiveStartDateChange}
          selectRange={allowRange ? true : false}
          tileClassName={disableBookedDates}
        />

        <button onClick={handleGoToNextMonth}>
          <FaChevronRight />
        </button>
      </div>
    </div>
  );
}

export default CustomCalendar;
