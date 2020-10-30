package com.mx.test.agenda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.test.agenda.models.entity.Agenda;
import com.mx.test.agenda.models.entity.Usuario;
import com.mx.test.agenda.models.repository.IAgendaRepository;
import com.mx.test.agenda.models.repository.IUsuarioRepository;

@Service
public class ServiceUsuarioImpl implements IServiceUsuario, UserDetailsService{
	
	@Autowired
	IUsuarioRepository repositoryUsuario;
	
	@Autowired
	IAgendaRepository repositoryAgenda;

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		return repositoryUsuario.save(usuario);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Usuario> findAllUser() {
		return repositoryUsuario.findAll();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repositoryUsuario.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Agenda> findByUsuariosAgendasIds(Iterable<Long> agendasIds) {
		return repositoryAgenda.findAllById(agendasIds);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Agenda> findAgendasByUsuario(Long agendaId) {
		return repositoryAgenda.findAgendasByUsuario(agendaId);
	}

//	@Override
//	@Transactional(readOnly = true)
//	public Usuario findUsuarioById(Long id) {
//		return repositoryUsuario.findUsuarioById(id);
//	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findUsuarioByName(String name, String lastName) {
		return repositoryUsuario.findUsuarioByName(name, lastName);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findOptionalById(Long id) {
		return repositoryUsuario.findOptionalById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUser(String correo) {
		return repositoryUsuario.findByUser(correo);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usr = repositoryUsuario.findByUser(username);
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("USER"));
		
		UserDetails userDet = new User(usr.getCorreo(), usr.getPassword(), roles);
		
		return userDet;
	}
}
