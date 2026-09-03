package fr.diginamic.hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GestionnaireException {

    @ExceptionHandler(VilleException.class)
    public ResponseEntity<String> handlerVilleException(VilleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> traiterException(MethodArgumentNotValidException e) {

        FieldError fe = e.getBindingResult().getFieldError();
        return ResponseEntity.badRequest().body(fe.getDefaultMessage());
    }
}
