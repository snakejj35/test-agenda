package com.mx.test.agenda.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mx.test.agenda.models.entity.Agenda;

public interface IAgendaRepository extends CrudRepository<Agenda, Long>{
	
	//@Query("SELECT a FROM Agenda a JOIN FETCH a.usuario u WHERE a.usuarioId=?1 AND u.id=?2")
	@Query("SELECT a FROM Agenda a JOIN Usuario u ON a.usuario=u.id WHERE a.usuario=?1")
	List<Agenda> findAgendasByUsuario(Long agendaId);

}
