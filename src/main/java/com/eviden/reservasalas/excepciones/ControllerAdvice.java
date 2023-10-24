package com.eviden.reservasalas.excepciones;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eviden.reservasalas.excepciones.exceptions.ClaveDuplicadaException;
import com.eviden.reservasalas.excepciones.exceptions.DataNotFoundException;
import com.eviden.reservasalas.excepciones.exceptions.DatosEntradaException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        //Recoge los errores de Spring
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        //Convierte los errores a un String
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(f -> errorMessage.append(f.getField() + " " + f.getDefaultMessage() +  " "));

        //retorna el objeto ErrorInfo en formato JSON
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errorMessage.toString(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	
	/*public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
       ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
       return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	*/
	@ExceptionHandler(value = DataNotFoundException.class) //Tipo de error que va a tratar
	public ResponseEntity<ErrorDTO> dataNotFoundException(DataNotFoundException ex){
		ErrorDTO errorDto = ErrorDTO.builder()
									  .code(ex.getCode())
									  .mensajeError(ex.getMessage())
				                      .build();
		return new ResponseEntity<ErrorDTO>(errorDto, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ClaveDuplicadaException.class) //Tipo de error que va a tratar
	public ResponseEntity<ErrorDTO> claveDuplicadaException(ClaveDuplicadaException ex){
		ErrorDTO errorDto = ErrorDTO.builder()
									  .code(ex.getCode())	
									  .mensajeError(ex.getMessage())
				                      .build();
		return new ResponseEntity<ErrorDTO>(errorDto, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = DatosEntradaException.class) //Tipo de error que va a tratar
	public ResponseEntity<ErrorDTO> entradaErrorException(DatosEntradaException ex){
		ErrorDTO errorDto = ErrorDTO.builder()
									  .code(ex.getCode())	
									  .mensajeError(ex.getMessage())
				                      .build();
		return new ResponseEntity<ErrorDTO>(errorDto, HttpStatus.BAD_REQUEST);
	}
	
	
	
}
