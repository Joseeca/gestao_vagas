package br.com.joseoliveira.gestao_vagas.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // <-- Adicione esta importação

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    // Boa prática: declarar atributos antes do construtor e usar 'final' em dependências injetadas
    private final MessageSource messageSource;

    // Construtor para injetar a dependência do MessageSource
    public ExceptionHandlerController(MessageSource message){
        this.messageSource = message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Método para tratar exceções de validação de argumentos de método
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        // Cria uma lista para armazenar as mensagens de erro
        List<ErrorMessageDTO> dto = new ArrayList<>();

        // Percorre os erros de validação e cria uma lista de mensagens de erro
        e.getBindingResult().getFieldErrors().forEach(err -> {

            // Correção: Objects.requireNonNull garante à IDE que 'err' não é nulo
            String message = messageSource.getMessage(Objects.requireNonNull(err), LocaleContextHolder.getLocale());

            // Cria um objeto ErrorMessageDTO com a mensagem de erro e o campo correspondente
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            dto.add(error);
        });

        // Retorna uma resposta HTTP com status 400 (Bad Request) e a lista de mensagens de erro
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}