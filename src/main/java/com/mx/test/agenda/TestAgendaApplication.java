package com.mx.test.agenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mx.test.agenda.auth.SecurityConfig;

@Configuration 
@Import({ SecurityConfig.class}) //contendrá todo lo referente a las contraseñas.
@SpringBootApplication
public class TestAgendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestAgendaApplication.class, args);
	}

}
