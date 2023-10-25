package com.eviden.reservasalas.excepciones;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.eviden.reservasalas.excepciones.exceptions.BadRequestException;
import com.eviden.reservasalas.excepciones.exceptions.DataNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice {
	
	/*@ExceptionHandler(MethodArgumentNotValidException.class)
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
	
	*/
	/*public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
       ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
       return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	*/
	
	//controla los errores de los campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlderMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                          WebRequest webRequest) {
    	System.out.println("Estamos en handlderMethodArgumentNotValidException");
        Map<String, String> mapErrors = new HashMap<>();
        System.out.println("maperrors: " + mapErrors.toString());
        exception.getBindingResult().getAllErrors().forEach((error) -> {
                    String clave = ((FieldError) error).getField();
                    String valor = error.getDefaultMessage();
                    mapErrors.put(clave, valor);
                }
        );
        ErrorFormatCampos errorFormatCampos = new ErrorFormatCampos(mapErrors.toString(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorFormatCampos, HttpStatus.BAD_REQUEST);
    }
	
	//Controla los errores producidos por la violacion de la integridad de datos en BBDD
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorInfo> dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
		
		ErrorInfo errorInfo = ErrorInfo.builder()
                   .message("Datos duplicados: " + e.getMessage())
                   .statusCode(HttpStatus.CONFLICT.value())
                   .uriRequested(request.getRequestURI())
                   .build();
			 //return new ResponseEntity<>(errorInfo,HttpStatus.CONFLICT);
		return new ResponseEntity<>(errorInfo,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//Datos no encontrados
	@ExceptionHandler(value = DataNotFoundException.class) //Tipo de error que va a tratar
	public ResponseEntity<ErrorDTO> dataNotFoundException(DataNotFoundException ex){
		ErrorDTO errorDto = ErrorDTO.builder()
									  .code(ex.getCode())
									  .mensajeError(ex.getMessage())
				                      .build();
		return new ResponseEntity<ErrorDTO>(errorDto, HttpStatus.NOT_FOUND);
	}
	
	//controla los errores de logica o de los catch en general 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorFormatCampos> handlerBadRequestException(BadRequestException exception,WebRequest webRequest) {
    	ErrorFormatCampos errorFormatCampos = new ErrorFormatCampos(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorFormatCampos, HttpStatus.BAD_REQUEST);
    }
    
	/*
	@ExceptionHandler(value = DatosEntradaException.class) //Tipo de error que va a tratar
	public ResponseEntity<ErrorDTO> entradaErrorException(DatosEntradaException ex){
		ErrorDTO errorDto = ErrorDTO.builder()
									  .code(ex.getCode())	
									  .mensajeError(ex.getMessage())
				                      .build();
		return new ResponseEntity<ErrorDTO>(errorDto, HttpStatus.BAD_REQUEST);
	}
	*/
    
	//controla los errores de varios tipos y globalizrlo con un error 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handlerException(Exception exception, HttpServletRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
