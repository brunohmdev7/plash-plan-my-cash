package br.com.plashplanmycash.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PlashException extends RuntimeException {

    private final HttpStatus status;
    private final String erro;

    public PlashException(HttpStatus status, String erro, String mensagem) {
        super(mensagem);
        this.status = status;
        this.erro = erro;
    }

    public static PlashException prazoInvalido(String mensagem) {
        return new PlashException(HttpStatus.BAD_REQUEST, "Prazo inválido", mensagem);
    }

    public static PlashException valorInvalido(String mensagem) {
        return new PlashException(HttpStatus.BAD_REQUEST, "Valor inválido", mensagem);
    }

    public static PlashException naoEncontrado(String mensagem) {
        return new PlashException(HttpStatus.NOT_FOUND, "Recurso não encontrado", mensagem);
    }

    public static PlashException acessoNegado(String mensagem) {
        return new PlashException(HttpStatus.FORBIDDEN, "Acesso negado", mensagem);
    }

    public static PlashException conflito(String mensagem) {
        return new PlashException(HttpStatus.CONFLICT, "Conflito de dados", mensagem);
    }

    public static PlashException erroInterno(String mensagem) {
        return new PlashException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", mensagem);
    }
}
