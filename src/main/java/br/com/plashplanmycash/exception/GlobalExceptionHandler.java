package br.com.plashplanmycash.exception;

import br.com.plashplanmycash.exception.ErroResponse;
import br.com.plashplanmycash.exception.PlashException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlashException.class)
    public ResponseEntity<ErroResponse> handlePlash(PlashException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(new ErroResponse(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getErro(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> campos.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Um ou mais campos inválidos",
                request.getRequestURI(),
                campos
        ));
    }
}