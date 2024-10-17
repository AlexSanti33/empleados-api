package com.empleado.api.empleados.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.empleado.api.empleados.api.EmpleadosApi;
import com.empleado.api.empleados.entities.Empleado;
import com.empleado.api.empleados.entities.Puesto;
import com.empleado.api.empleados.entities.Sexo;
import com.empleado.api.empleados.repository.EmpleadoRepository;
import com.empleado.api.empleados.service.EmpleadoService;

@ExtendWith(MockitoExtension.class)
public class EmpleadoApiTest {

	@Mock
    private EmpleadoService empleadoService;
	
	@Mock
	private EmpleadoRepository empleadoRepository;
	
	@InjectMocks
	private EmpleadosApi api;
	
	@Test
	void testObtenerEmpleados() {
		Sexo sexo =  new Sexo();
		sexo.setId(2L);
		Puesto puesto = new Puesto();
		puesto.setId(1L);
		LocalDate fechaNacimiento = LocalDate.parse("01-09-1990", DateTimeFormatter.ofPattern("dd-MM-yyy"));
		List<Empleado> empleados = Arrays.asList(
				new Empleado(null, "Alejandro", "Javier","Santiago", "Garcia", (byte) 34, sexo, fechaNacimiento, puesto), 
				new Empleado(null, "Alejandro", "Gael","Santiago", "Callejas", (byte) 31, sexo, fechaNacimiento, puesto));
		when(empleadoService.all()).thenReturn(empleados);
		ResponseEntity<List<Empleado>> respuesta = api.obtenerTodos(null);
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertEquals(2, empleados.size());
	}
	
	@Test
	void testEliminarEmpleadoPorId() {
	    // Configuración del mock
		Sexo sexo =  new Sexo();
		sexo.setId(2L);
		Puesto puesto = new Puesto();
		puesto.setId(1L);
		LocalDate fechaNacimiento = LocalDate.parse("01-09-1990", DateTimeFormatter.ofPattern("dd-MM-yyy"));
	    Long idEmpleado = 1L;
	    Empleado empl =new Empleado(idEmpleado, "Alejandro", "Javier","Santiago", "Garcia", (byte) 34, sexo, fechaNacimiento, puesto);
	    doNothing().when(empleadoService).delete(idEmpleado);
	    when(empleadoRepository.findById(idEmpleado)).thenReturn(Optional.of(empl));
	    ResponseEntity<?> respuesta = api.eliminarPorId("AlexSanti",idEmpleado);
	    assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
	    assertEquals(true,empleadoRepository.findById(idEmpleado).isPresent());
	    verify(empleadoService, times(1)).delete(idEmpleado);
	}

	
}
