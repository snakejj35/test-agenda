package com.mx.test.agenda.service;

import java.util.List;
import java.util.Optional;

import com.mx.test.agenda.models.entity.Agenda;
import com.mx.test.agenda.models.entity.Usuario;

public interface IServiceUsuario {
	
	Iterable<Usuario> findAllUser(); 
	
	Usuario save(Usuario usuario); //impl
	
	List<Agenda> findAgendasByUsuario(Long agendaId);
	
	//Usuario findUsuarioById(Long id); //impl
	Iterable<Agenda> findByUsuariosAgendasIds(Iterable<Long> agendasIds); //de prueba
	
	Optional<Usuario> findOptionalById(Long id); //imp
	
	List<Usuario> findUsuarioByName(String name, String lastName); //impl
	
	void deleteById(Long id);	
	
	Usuario findByUser(String correo);
	
}
