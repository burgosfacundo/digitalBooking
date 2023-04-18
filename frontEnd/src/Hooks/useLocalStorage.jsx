import { useState } from 'react';

function useLocalStorage(key, defaultValue) {
  const [storedValue, setStoredValue] = useState(() => {
    const value = localStorage.getItem(key);
    return value || defaultValue;
  });

  const updateStorage = (newValue) => {
    setStoredValue(newValue);
    localStorage.setItem(key, newValue);
  };

  const removeFromStorage = () => {
    setStoredValue(null);
    localStorage.removeItem(key);
  };

  return { storedValue, updateStorage, removeFromStorage };
}

export default useLocalStorage;
