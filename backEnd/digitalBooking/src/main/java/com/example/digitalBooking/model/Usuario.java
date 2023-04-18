package com.example.digitalBooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String nombre;

    @Column(nullable = false,length = 50)
    private String apellido;

    @Column(nullable = false,length = 50,unique = true)
    private String email;

    @Column(nullable = false,length = 100)
    private String password;

    @Column(nullable = false,length = 50)
    private String ciudad;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<Puntuacion> puntuaciones = new HashSet<>();

    public void addPuntuacion(Puntuacion puntuacion) {
        puntuaciones.add(puntuacion);
        puntuacion.setUsuario(this);
    }
    public void removePuntuacion(Puntuacion puntuacion) {puntuaciones.remove(puntuacion);}

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<Reserva> reservas = new HashSet<>();

    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setUsuario(this);
    }
    public void removeReserva(Reserva reserva) {reservas.remove(reserva);}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var grantedAuthority = new SimpleGrantedAuthority(rol.getNombre());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword(){ return this.password; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
