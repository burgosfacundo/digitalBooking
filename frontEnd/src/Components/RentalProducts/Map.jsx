import L from 'leaflet';
import { MapContainer, Marker, Popup, TileLayer, useMap } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import markerIcon from 'leaflet/dist/images/marker-icon.png';
import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png';
import markerIconShadow from 'leaflet/dist/images/marker-shadow.png';
import { useEffect, useState } from 'react';

delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
    iconRetinaUrl: markerIcon2x,
    iconUrl: markerIcon,
    shadowUrl: markerIconShadow
});

function CustomMarker({ latitud, longitud, titulo }) {
  const map = useMap();
  const ubicacion = [longitud, latitud];

  map.setView(ubicacion, map.getZoom());

  return (
    <Marker position={ubicacion}>
      <Popup>{titulo}</Popup>
    </Marker>
  );
}

function Map({ producto, imagenes }) {
  const [latitud, setLatitud] = useState(0);
  const [longitud, setLongitud] = useState(0);
  const initalSettings = {
    center: [latitud, longitud],
    zoom: 15,
  };

  useEffect(() => {
    if (producto.id) {
      setLatitud(producto.longitud);
      setLongitud(producto.latitud);
    }
  });

  return (
    <MapContainer
      center={initalSettings.center}
      zoom={initalSettings.zoom}
      scrollWheelZoom={false}
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <CustomMarker latitud={latitud} longitud={longitud} titulo={producto.titulo} />
    </MapContainer>
  );
}

export default Map;
