package com.empleado.api.empleados.api;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empleado.api.empleados.entities.Empleado;
import com.empleado.api.empleados.service.EmpleadoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;


@Api(tags = "Empleados")
@RestController
@RequestMapping("/empleado")
@Slf4j
public class EmpleadosApi {

	@Autowired
	private EmpleadoService empleadoService;
	
    @ApiOperation(value = "Obtener todos los Empleados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Emplados encontrados"),
            @ApiResponse(code = 404, message = "No se encontraron Empleados")
    })
	@GetMapping
	public ResponseEntity<List<Empleado>> obtenerTodos(HttpServletRequest httpServletRequest){
    	
    	if(Objects.nonNull(httpServletRequest))
    		log.info("Header user: "+httpServletRequest.getHeader("user"));
		
    	return  ResponseEntity.ok(empleadoService.all());
	}
    
    @ApiOperation(value = "Eliminar usuario por id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Emplado eliminado"),
            @ApiResponse(code = 404, message = "No se encontro Empleado")
    })
    @ApiParam(value = "Id de usuario a eliminar")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarPorId(@RequestHeader("user")String usuario,@PathVariable Long id){
		log.info("Header user: "+usuario);
    	empleadoService.delete(id);
		return  ResponseEntity.noContent().build();
	}
	
    @ApiOperation(value = "Actualizar informacion de usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Emplado Actualizado"),
            @ApiResponse(code = 404, message = "No se encontro Empleado")
    })
	@PutMapping("/{id}")
	public ResponseEntity<Empleado> actualizarDatos(@RequestBody Empleado empleado, @PathVariable Long id){
		
		return ResponseEntity.ok(empleadoService.update(empleado, id));
	}
    
    @ApiOperation(value = "Insertar uno o varios usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Emplados insertados"),
            @ApiResponse(code = 500, message = "Error interno al insertar"),
            @ApiResponse(code = 400, message = "Datos de empleados erroneos")
            
    })
    @ApiParam(value = "Lista de empleados a insertar")
	@PostMapping
	public ResponseEntity<List<Empleado>> guardarUnoOVarios(@Valid @RequestBody List<Empleado> listaEmpleado) throws Throwable {
    	;
    	return ResponseEntity.status(HttpStatus.CREATED).header("X-Created-Resources", "Empleados")
                .body(empleadoService.saveAll(listaEmpleado));
    	
	}
	
}
