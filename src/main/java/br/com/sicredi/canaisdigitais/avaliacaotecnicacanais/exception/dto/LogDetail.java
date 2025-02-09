package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public record LogDetail(
        LocalDateTime timestamp,
        String httpStatus,
        String path,
        String message,
        String exception,
        String cause,
        String stackTrace
) {

    private static final String CAUSE_NOT_AVAILABLE = "Causa não disponível";
    private static final String PACKAGE_FILTER = "br.com.sicredi.canaisdigitais";
    private static final int MAX_STACK_DEPTH = 5;

    public LogDetail(HttpStatus httpStatus, String path, Exception exception) {
        this(
                LocalDateTime.now(),
                httpStatus.toString(),
                path,
                exception.getMessage(),
                getExceptionName(exception),
                getCauseOrDefault(exception),
                getFilteredStackTrace(exception, httpStatus)
        );
    }

    private static String getExceptionName(Exception exception) {
        return (exception != null) ? exception.getClass().getSimpleName() : null;
    }

    private static String getCauseOrDefault(Exception exception) {
        return (exception != null && exception.getCause() != null)
                ? exception.getCause().toString()
                : CAUSE_NOT_AVAILABLE;
    }

    private static String getFilteredStackTrace(Exception exception, HttpStatus httpStatus) {
        if (exception == null) {
            return "";
        }
        String filteredStackTrace = Arrays.stream(exception.getStackTrace())
                .filter(element -> element.getClassName().startsWith(PACKAGE_FILTER))
                .limit(MAX_STACK_DEPTH)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
        if (filteredStackTrace.isEmpty() || isInternalServerError(httpStatus)) {
            return Arrays.stream(exception.getStackTrace())
                    .limit(MAX_STACK_DEPTH)
                    .map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n"));
        }
        return filteredStackTrace;
    }

    private static boolean isInternalServerError(HttpStatus httpStatus) {
        return httpStatus == HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
