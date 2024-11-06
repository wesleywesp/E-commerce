package com.wesploja.lojaweb.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {


    //para tratar as exception de encrytar e descriptografar dados sensíveis
    @ExceptionHandler(TratarEncrypError.class)
    public ResponseEntity<?> handleException(TratarEncrypError ex) {
         ResponseError responseError= new ResponseError(ex.getMessage()
                ,HttpStatus.BAD_REQUEST
                 , LocalDateTime.now() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }
    @ExceptionHandler(TratarCkechoutCart.class)
    public ResponseEntity<?> handleException(TratarCkechoutCart ex) {
        ResponseError responseError= new ResponseError(ex.getMessage()
                ,HttpStatus.BAD_REQUEST
                , LocalDateTime.now() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }



    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {



        var responseError = new ResponseError(ex.getMessage(),HttpStatus.BAD_REQUEST,LocalDateTime.now());
        return ResponseEntity.badRequest().body(responseError);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity tratarErroAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
    }

    record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
