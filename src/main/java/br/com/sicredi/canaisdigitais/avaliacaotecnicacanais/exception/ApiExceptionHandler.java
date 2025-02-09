package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception.dto.LogDetail;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception.dto.ResponseException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseException> handleEntityNotFoundException(
            EntityNotFoundException exception, WebRequest request
    ) {
        String path = getPathFromRequest(request);
        HttpStatusCustom httpStatusCustom = HttpStatusCustom.NOT_FOUND;
        logWarning(httpStatusCustom.getHttpStatus(), path, exception);
        ResponseException responseException = new ResponseException(httpStatusCustom, path, exception.getMessage());
        return ResponseEntity.status(httpStatusCustom.getHttpStatus()).body(responseException);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseException> handleGenericException(NoResourceFoundException exception, WebRequest request) {
        String path = getPathFromRequest(request);
        HttpStatusCustom httpStatusCustom = HttpStatusCustom.NOT_FOUND;
        logError(httpStatusCustom.getHttpStatus(), path, exception);
        ResponseException responseException = new ResponseException(httpStatusCustom, path);
        return ResponseEntity.status(httpStatusCustom.getHttpStatus()).body(responseException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> handleGenericException(Exception exception, WebRequest request) {
        String path = getPathFromRequest(request);
        HttpStatusCustom httpStatusCustom = HttpStatusCustom.INTERNAL_SERVER_ERROR;
        logError(httpStatusCustom.getHttpStatus(), path, exception);
        ResponseException responseException = new ResponseException(httpStatusCustom, path);
        return ResponseEntity.status(httpStatusCustom.getHttpStatus()).body(responseException);
    }

    private String getPathFromRequest(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    private void logError(HttpStatus httpStatus, String path, Exception exception) {
        LogDetail logDetail = new LogDetail(httpStatus, path, exception);
        logger.error("Erro no servidor: {}", logDetail);
    }

    private void logWarning(HttpStatus httpStatus, String path, Exception exception) {
        LogDetail logDetail = new LogDetail(httpStatus, path, exception);
        logger.warn("Aviso na requisição: {}", logDetail);
    }

}
