import './MultiSearch.scss';
import Select from 'react-select';
import { useEffect, useState } from 'react';

const searchByOptions = [
  {
    value: 'id',
    label: 'ID',
  },
  {
    value: 'name',
    label: 'Nombre',
  },
];

function MultiSearch({ setSelectedRental }) {
  const [isLodingData, setIsLoadingData] = useState(true);
  const [optionsForSearch, setOptionsForSearch] = useState([]);
  const [data, setData] = useState([]);

  const buildOptionsForSearch = (data) => {
    setOptionsForSearch(
      data
        .sort((a, b) => a.id - b.id)
        .map((rental) => ({
          value: rental.id,
          label: `${rental.id} - ${rental.titulo}`,
        }))
    );
  };

  useEffect(() => {
    const loadRentals = async () => {
      const response = await fetch(
        `${import.meta.env.VITE_BASE_API_URL}/productos/todasRandom`
      );
      setData(await response.json());
      setIsLoadingData(false);
    };

    loadRentals();
  }, []);

  useEffect(() => {
    buildOptionsForSearch(data);
  }, [data]);

  const handleChange = (inputData) => {
    setSelectedRental(
      data.filter((rental) => rental.id === inputData.value)[0]
    );
  };

  return (
    <form className="multi-search__container">
      <Select
        className="search-input"
        placeholder="Ingrese busqueda"
        options={optionsForSearch}
        isDisabled={isLodingData}
        onChange={handleChange}
      />
    </form>
  );
}

export default MultiSearch;
