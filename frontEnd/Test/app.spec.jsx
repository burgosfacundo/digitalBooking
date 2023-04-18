
import React from 'react';
import { BrowserRouter } from 'react-router-dom'; 
import { render,screen } from '@testing-library/react';
import CategoriaCard from "../src/Components/Body/CategoriaCard"


const mockData = {
  titulo: 'Comida',
  descripcion: 'Categoría de comida',
  urlImagen: 'https://example.com/comida.jpg'
};

describe('CategoriaCard', () => {
  test('Renderizando tiulo y datos', () => {
    render(
      <BrowserRouter>
        <CategoriaCard data={mockData} id={1} />
      </BrowserRouter>
    );

    const titleElement = screen.getByText(mockData.titulo);
    const descriptionElement = screen.getByText(mockData.descripcion);
    const imageElement = screen.getByAltText(`Imagen de la Categoria ${mockData.titulo}`);

    expect(titleElement).toBeInTheDocument();
    expect(descriptionElement).toBeInTheDocument();
    expect(imageElement).toBeInTheDocument();
    expect(imageElement.src).toBe(mockData.urlImagen);
  });

  test('links', () => {
    render(
      <BrowserRouter>
        <CategoriaCard data={mockData} id={1} />
      </BrowserRouter>
    );

    const linkElements = screen.getAllByRole('link');
    const linkElement = linkElements.find((element) => element.getAttribute('href') === `/categoria/${mockData.titulo}`);
    expect(linkElement).toBeInTheDocument();
  });
});