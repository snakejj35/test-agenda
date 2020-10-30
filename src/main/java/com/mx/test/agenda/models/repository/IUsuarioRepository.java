package com.mx.test.agenda.models.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mx.test.agenda.models.entity.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{
	
	//Iterable<Long> findByUsuariosAgendasIds(Iterable<Long> agendasIds);
	
	@Query("SELECT u FROM Usuario u WHERE id=?1")
	Usuario findUsuarioById(Long id);
	
	@Query("SELECT u FROM Usuario u WHERE nombre=?1 AND apellido=?2")
	List<Usuario> findUsuarioByName(String name, String lastName);
	
	@Query("SELECT u FROM Usuario u WHERE id=?1")
	Optional<Usuario> findOptionalById(Long id);
	
	//Test sping security
	@Query("SELECT u FROM Usuario u WHERE correo=?1")
	Usuario findByUser(String correo);
}
