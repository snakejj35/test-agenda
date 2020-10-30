package com.mx.test.agenda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mx.test.agenda.models.entity.Agenda;
import com.mx.test.agenda.models.entity.Usuario;
import com.mx.test.agenda.service.ServiceUsuarioImpl;
@CrossOrigin("http://localhost:4200") //@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/usuarios") 
public class UsuarioController {
	
	@Autowired
	private ServiceUsuarioImpl service;
	
//    @GetMapping("/")
//  //  @ResponseBody	    
//    public String login() {	
//    	if (isAuthenticated()) {
//    		//return "Bienvenido, tienes permisos de " + authentication.getName();	
//    		return "/login";
//		}
//    	//return "No tienes permisos de " + authentication.getDetails();	
//    	return "succefull";
//    }
    
	protected boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.
	      isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}
	
	//@RequestMapping(value = "/lista-usuarios", method = RequestMethod.GET)
	@RequestMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(service.findAllUser());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result){
		List<Usuario> usuarioExistente = service.findUsuarioByName(usuario.getNombre(), usuario.getApellido());
		if(result.hasErrors() || usuarioExistente.size() > 0) {
			return this.validar(result);
		}
		
//		Usuario user = new Usuario();
//		user.setNombre(usuario.getNombre());
//		user.setApellido(usuario.getApellido());
//		user.setCorreo(usuario.getCorreo());
//		user.setPassword(usuario.getPassword());
//		
//		List<Agenda> agendas = new ArrayList<Agenda>();
//		Agenda agenda = new Agenda();
//		Agenda ag = new Agenda();
//		ag.setNombre(agenda.getNombre());
//		ag.setFecha(agenda.getFecha());
//		ag.setHoraInicio(agenda.getHoraInicio());
//		ag.setHoraFin(agenda.getHoraFin());
//		agendas.add(ag);
//		
//		user.setAgendas(agendas);		
//		Usuario usr = service.save(user);   
		Usuario usr = service.save(usuario);            
		return ResponseEntity.status(HttpStatus.CREATED).body(usr); 
	}
	
	/**@RequestMapping(value = "/{id}/agendas", method = RequestMethod.POST)
	public ResponseEntity<?> agregarAgenda(@Valid @RequestBody List<Agenda> agendas, @PathVariable Long id, BindingResult result){
		
		Optional<Usuario> opt = service.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Usuario dbUsuario = opt.get();
		
		agendas.forEach(a -> {
			dbUsuario.addAgenda(a);
		});
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		           
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbUsuario)); 
	} */
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> verUsuario(@PathVariable Long id){
		Optional<Usuario> opt = service.findOptionalById(id);
		//Usuario usr = service.findUsuarioById(id);
		
		if(!opt.isPresent()) {
		//  if(usr == null) {
			return ResponseEntity.notFound().build();
		  }
		Usuario dbUsuario = opt.get();
		return ResponseEntity.ok().body(dbUsuario);
		//return ResponseEntity.ok().body(usr);
	}
	
//	@RequestMapping(value = "/{id}/agendas/{agendaId}", method = RequestMethod.GET)
//	public ResponseEntity<?> verAgenda(@PathVariable Long id, @PathVariable Long agendaId){
//		List<Usuario> usr = service.findUsuarioPorId(id);
//		if(usr.size() < 0) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		Iterable<Agenda> agendas = service.findAgendasByUsuario(agendaId);
//		return ResponseEntity.ok(agendas);
//	}
	
	//@GetMapping("/{id}")   //funciona
	public ResponseEntity<?> view(@PathVariable Long id) {
		Optional<Usuario> opt = service.findOptionalById(id);
		if(!opt.isPresent()) {   
			return ResponseEntity.notFound().build();  
		}
		Usuario usuario = opt.get();
		if(usuario.getAgendas().isEmpty() == false) { 
			List<Long> ids = (List<Long>) usuario.getAgendas().stream().map( ua -> {
				return ua.getId();
			}).collect(Collectors.toList());
			
			List<Agenda> agendas = (List<Agenda>) service.findByUsuariosAgendasIds(ids);
			usuario.setAgendas(agendas);
		}
		
		return ResponseEntity.ok().body(usuario);	
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
		
		Optional<Usuario> opt = service.findOptionalById(id);
		
		if(result.hasErrors()) {
			return this.validar(result);
		}	
		//Optional<Usuario> opt = service.findById(id);
			
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Usuario dbUsuario = opt.get();
		dbUsuario.setNombre(usuario.getNombre());
		dbUsuario.setApellido(usuario.getApellido());
		dbUsuario.setCorreo(usuario.getCorreo());
		dbUsuario.setPassword(usuario.getPassword());
		
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbUsuario));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build(); //Http status 204
	}
	
	@PutMapping("/{id}/agenda/{id}")
	public ResponseEntity<?> editarAgenda(@RequestBody Usuario usuario, @PathVariable Long id) {
		
		Optional<Usuario> opt = service.findOptionalById(id);
		
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Usuario usuarioDb = opt.get();
		usuarioDb.setId(usuario.getId());
			
		//agendas contenidas en el Json que Recorre la lista para mostrar las agendas a eliminar
		List<Agenda> eliminadas = usuarioDb.getAgendas()
		.stream()
		.filter(adb -> !usuario.getAgendas().contains(adb))
		.collect(Collectors.toList());
		
		//se separa el stream para que no este en el mismo flujo y pueda eliminar
		eliminadas.forEach(usuarioDb :: removeAgenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDb));
	}
	
	protected ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());		
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
}
