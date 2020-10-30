package com.mx.test.agenda.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String apellido;
	
	private String correo;
	
	private String password;
	
	@JsonIgnoreProperties(value = {"usuario"}, allowSetters = true)
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Agenda> agendas;
	
	public Usuario() {
		this.agendas = new ArrayList<>();
	}
	
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
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}
	
	public void setAgendas(List<Agenda> agendas) {
		this.agendas.clear(); 
		agendas.forEach(a -> this.addAgenda(a) );
	}
	
	public void addAgenda(Agenda agenda) {
		this.agendas.add(agenda); 
		agenda.setUsuario(this);
	}
	
	public void removeAgenda(Agenda agenda) {
		this.agendas.remove(agenda);
		agenda.setUsuario(null);
	}
	
}
