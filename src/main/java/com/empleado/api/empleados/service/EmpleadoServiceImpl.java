package com.empleado.api.empleados.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empleado.api.empleados.entities.Empleado;
import com.empleado.api.empleados.repository.EmpleadoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Override
	public List<Empleado> all() {
		log.info("Ejecutando: {} metodo all");	
		return empleadoRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		log.info("Ejecutando: {} metodo delete");
		Empleado empleadoDb = empleadoRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Metodo Elimiar usuario: {}El empleado no se pudo eliminar por que el id no esta registrado"));
		empleadoRepository.delete(empleadoDb);		
	}

	@Override
	public Empleado update(Empleado empleado, Long id) {
		log.info("Ejecutando: {} metodo update");
		Empleado empleadoDb = empleadoRepository.findById(id).orElseThrow(()-> new NoSuchElementException("El empleado no se pudo actualizar por que el id no esta registrado"));
		empleadoDb.setPrimerNombre(empleado.getPrimerNombre());
		empleadoDb.setSegundoNombre(empleado.getSegundoNombre());
		empleadoDb.setEdad(empleado.getEdad());
		empleadoDb.setPuesto(empleado.getPuesto());
		return empleadoRepository.save(empleadoDb);
	}

	@Override
	public List<Empleado> saveAll(List<Empleado> listaEmplados) throws Throwable {
		log.info("Ejecutando: {} metodo saveAll");
		return Optional.ofNullable(empleadoRepository.saveAll(listaEmplados)).orElseThrow(()-> new Exception());
	}

}
