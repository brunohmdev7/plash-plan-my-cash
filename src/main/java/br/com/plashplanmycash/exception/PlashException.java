package br.com.plashplanmycash.exception;

import org.springframework.http.HttpStatus;

public class PlashException extends RuntimeException {

    private final HttpStatus status;
    private final String erro;

    private PlashException(HttpStatus status, String erro, String mensagem) {
        super(mensagem);
        this.status = status;
        this.erro = erro;
    }

    public static PlashException naoEncontrado(String mensagem) {
        return new PlashException(HttpStatus.NOT_FOUND, "Recurso não encontrado", mensagem);
    }

    public static PlashException conflito(String mensagem) {
        return new PlashException(HttpStatus.CONFLICT, "Conflito de dados", mensagem);
    }

    public static PlashException prazoInvalido(String mensagem) {
        return new PlashException(HttpStatus.UNPROCESSABLE_ENTITY, "Prazo inválido", mensagem);
    }

    public static PlashException valorInvalido(String mensagem) {
        return new PlashException(HttpStatus.UNPROCESSABLE_ENTITY, "Valor inválido", mensagem);
    }

    public HttpStatus getStatus() { return status; }

    public String getErro() { return erro; }
}
