package br.com.plashplanmycash.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlashException.class)
    public ResponseEntity<ErroResponse> handlePlash(PlashException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErroResponse(
                        ex.getStatus().value(),
                        ex.getErro(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGeneric(Exception ex) {
        PlashException plash = PlashException.erroInterno(ex.getMessage());
        return ResponseEntity.status(plash.getStatus())
                .body(new ErroResponse(
                        plash.getStatus().value(),
                        plash.getErro(),
                        plash.getMessage()
                ));
    }
}