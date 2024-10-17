package com.empleado.api.empleados.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String primerNombre;
	private String segundoNombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	
	@NotNull(message = "Edad es requerido")
	private byte edad;
	
	@NotNull(message ="Sexo es un campo obligatorio")
	@ManyToOne
	@JoinColumn(name="sexo_id")
	private Sexo sexo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fechaNacimiento;
	
	@ManyToOne
	@JoinColumn(name="puesto_id")
	private Puesto puesto;
	
	
}
