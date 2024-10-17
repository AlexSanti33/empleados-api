package com.empleado.api.empleados.service;

import java.util.List;

import com.empleado.api.empleados.entities.Empleado;

public interface EmpleadoService {

	List<Empleado>all();
	
	void delete(Long id);
	
	Empleado update(Empleado empleado,Long id);
	
	List<Empleado> saveAll(List<Empleado>listaEmplados) throws Throwable;
	
}
