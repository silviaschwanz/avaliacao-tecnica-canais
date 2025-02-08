package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.dto;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.TypeError;

import java.time.LocalDateTime;

public record LogDetail(
        LocalDateTime timestamp,
        String errorId,
        String path,
        String message,
        String exception,
        String cause,
        String stackTrace
) {

    private static final String CAUSE_NOT_AVAILABLE = "Causa não disponível";

    public LogDetail(TypeError typeError, String path, Throwable throwable) {
        this(
                LocalDateTime.now(),
                typeError.getErrorId(),
                path,
                getMessageOrDefault(throwable, typeError),
                getExceptionName(throwable),
                getCauseOrDefault(throwable),
                getStackTrace(throwable)
        );
    }

    private static String getMessageOrDefault(Throwable throwable, TypeError errorType) {
        return (throwable != null && throwable.getMessage() != null)
                ? throwable.getMessage()
                : errorType.getDefaultMessage();
    }

    private static String getExceptionName(Throwable throwable) {
        return (throwable != null) ? throwable.getClass().getSimpleName() : null;
    }

    private static String getCauseOrDefault(Throwable throwable) {
        return (throwable != null && throwable.getCause() != null)
                ? throwable.getCause().toString()
                : CAUSE_NOT_AVAILABLE;
    }

    private static String getStackTrace(Throwable throwable) {
        StringBuilder stackTraceBuilder = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            stackTraceBuilder.append(element).append("\n");
        }
        return stackTraceBuilder.toString().trim();
    }

}
