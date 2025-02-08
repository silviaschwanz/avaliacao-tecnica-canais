package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.dto.LogDetail;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.dto.ResponseError;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.dto.ValidationError;
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
        TypeError typeError = TypeError.BAD_REQUEST;
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        logWarning(typeError, path, exception);
        ResponseError responseError = new ResponseError(typeError, path, validationErrors);
        return new ResponseEntity<>(responseError, typeError.getHttpStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> handleEntityNotFoundException(
            EntityNotFoundException exception, WebRequest request
    ) {
        String path = getPathFromRequest(request);
        TypeError typeError = TypeError.NOT_FOUND;
        logWarning(typeError, path, exception);
        ResponseError responseError = new ResponseError(typeError, path);
        return new ResponseEntity<>(responseError, typeError.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseError> handleDataIntegrityViolation(
            DataIntegrityViolationException exception, WebRequest request
    ) {
        String path = getPathFromRequest(request);
        TypeError typeError = TypeError.BAD_REQUEST;
        logWarning(typeError, path, exception);
        ResponseError responseError = new ResponseError(typeError, path);
        return new ResponseEntity<>(responseError, typeError.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGenericException(Exception exception, WebRequest request) {
        String path = getPathFromRequest(request);
        TypeError typeError = TypeError.INTERNAL_SERVER_ERROR;
        logError(typeError, path, exception);
        ResponseError responseError = new ResponseError(typeError, path);
        return new ResponseEntity<>(responseError, typeError.getHttpStatus());
    }

    private String getPathFromRequest(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    private void logError(TypeError typeError, String path, Throwable throwable) {
        LogDetail logDetail = new LogDetail(typeError, path, throwable);
        logger.error("Erro no servidor: {}", logDetail);
    }

    private void logWarning(TypeError typeError, String path, Throwable throwable) {
        LogDetail logDetail = new LogDetail(typeError, path, throwable);
        logger.warn("Aviso na requisição: {}", logDetail);
    }

}
