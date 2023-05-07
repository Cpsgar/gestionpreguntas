package es.mdfe.gestionpreguntas.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="USUARIOS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_role", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("null")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;
	public static enum Role {
		Administrador,
		NoAdministrador
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@NotBlank(message="nombre es un campo obligatorio de la clase usuario")
	private String nombre;
	
	@NotBlank(message="username es un campo obligatorio de la clase usuario")
	@Column(unique=true, name = "username")
	private String username;
	
	@NotBlank(message="password es un campo obligatorio de la clase usuario")
	private String password;
	
	@OneToMany(mappedBy = "usuario")
	List<Pregunta>preguntas;
	
	@Column(name="cuenta_activa")
	private boolean accountNonExpired = true;
	@Column(name="cuenta_desbloqueada")
	private boolean accountNonLocked = true;
	@Column(name="credenciales_activas")
	private boolean credentialsNonExpired = true;
	@Column(name="habilitada")
	private boolean enabled = true;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return null;
	}
	public List<Pregunta> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<SimpleGrantedAuthority>(
				Arrays.asList(new SimpleGrantedAuthority(getRole().toString()))
				);
	}

}
