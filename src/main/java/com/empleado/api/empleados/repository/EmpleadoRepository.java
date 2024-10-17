package com.empleado.api.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empleado.api.empleados.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

	
}
