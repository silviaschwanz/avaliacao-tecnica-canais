package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.log.ErrorLogger;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.log.WarnLogger;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response.ResponseError;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response.TypeError;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response.ValidationError;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationException(MethodArgumentNotValidException exception, WebRequest request) {
        String path = getPathFromRequest(request);
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        WarnLogger.log(TypeError.BAD_REQUEST, path, exception);
        ResponseError responseError = new ResponseError(TypeError.BAD_REQUEST, path, validationErrors);
        return new ResponseEntity<>(responseError, TypeError.BAD_REQUEST.getHttpStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> handleEntityNotFoundException(
            EntityNotFoundException exception, WebRequest request
    ) {
        String path = getPathFromRequest(request);
        WarnLogger.log(TypeError.NOT_FOUND, path, exception);
        ResponseError responseError = new ResponseError(TypeError.NOT_FOUND, path);
        return new ResponseEntity<>(responseError, TypeError.NOT_FOUND.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseError> handleDataIntegrityViolation(
            DataIntegrityViolationException exception, WebRequest request
    ) {
        String path = getPathFromRequest(request);
        WarnLogger.log(TypeError.BAD_REQUEST, path, exception);
        ResponseError responseError = new ResponseError(TypeError.BAD_REQUEST, path);
        return new ResponseEntity<>(responseError, TypeError.BAD_REQUEST.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGenericException(Exception exception, WebRequest request) {
        String path = getPathFromRequest(request);
        ErrorLogger.log(TypeError.INTERNAL_SERVER_ERROR, path, exception);
        ResponseError responseError = new ResponseError(TypeError.INTERNAL_SERVER_ERROR, path);
        return new ResponseEntity<>(responseError, TypeError.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseError> handleThrowable(Throwable throwable, WebRequest request) {
        String path = getPathFromRequest(request);
        ErrorLogger.log(TypeError.INTERNAL_SERVER_ERROR, path, throwable);
        ResponseError responseError = new ResponseError(TypeError.INTERNAL_SERVER_ERROR, path);
        return new ResponseEntity<>(responseError, TypeError.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    private String getPathFromRequest(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

}
