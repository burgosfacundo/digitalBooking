import './sassStyles/base/_general.scss';
import './sassStyles/base/_typography.scss';

import { useState } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

import Home from './Routes/Home';
import Login from './Routes/Login';
import CreateUser from './Routes/CreateUser';
import Header from './Components/Header/Header';
import Footer from './Components/Footer/Footer';
import MenuDrawerMobile from './Components/Header/MenuDrawerMobile/MenuDrawerMobile';
import RentalProducts from './Routes/RentalProducts';
import NotFound from './Routes/NotFound';
import Booking from './Routes/Booking';
import BookingSuccess from './Routes/BookingSuccess';
import CategoryProducts from './Routes/CategoryProducts';
import Search from './Routes/Search';
import useAuth from './Hooks/useAuth';
import MyReservations from './Routes/MyReservations';
import ProtectedRoutes from './Components/ProtectedRoutes';
import AdminPanel from './Components/Admin/AdminPanel';
import NotAuthorized from './Routes/NotAuthorized';

function App() {
  // Estado que determina si el menu lateral en mobile esta visible
  const [menuDrawerVisible, setMenuDrawerVisible] = useState(false);
  const { isLoading } = useAuth();

  if (isLoading) return null;

  return (
    <div className="app">
      {menuDrawerVisible && (
        <MenuDrawerMobile setMenuDrawerVisible={setMenuDrawerVisible} />
      )}

      <Header setMenuDrawerVisible={setMenuDrawerVisible} />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="home" element={<Home />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<CreateUser />} />
        <Route path="busqueda" element={<Search />} />
        <Route path="categoria/:id" element={<CategoryProducts />} />
        <Route path="producto/:id" element={<RentalProducts />} />
        <Route
          path="producto/:id/reserva"
          element={
            <ProtectedRoutes allowedRoles={[2]}>
              <Booking />
            </ProtectedRoutes>
          }
        />
        <Route path="reserva_confirmada" element={<BookingSuccess />} />
        <Route path="/:userId/reservas" element={<MyReservations />} />

        <Route path="admin">
          <Route
            path="crear"
            element={
              <ProtectedRoutes allowedRoles={[1]}>
                <AdminPanel mode="create" />
              </ProtectedRoutes>
            }
          />
          <Route
            path="modificar"
            element={
              <ProtectedRoutes allowedRoles={[1]}>
                <AdminPanel mode="modify" />
              </ProtectedRoutes>
            }
          />
        </Route>

        <Route path="/unauthorized" element={<NotAuthorized />} />
        <Route path="/404" element={<NotFound />} />
        <Route path="*" element={<Navigate replace to="/404" />} />
      </Routes>

      <Footer />
    </div>
  );
}

export default App;
