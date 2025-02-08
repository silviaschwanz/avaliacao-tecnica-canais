package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.log;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response.TypeError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLogger {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogger.class);

    public static void log(TypeError typeError, String path, Throwable throwable) {
        LogDetail logDetail = new LogDetail(typeError, path, throwable);
        if(throwable instanceof Error) {
            logCriticalError(logDetail);
        } else {
            logError(logDetail);
        }
    }

    private static void logCriticalError(LogDetail logDetail) {
        logger.error("ERRO CRÍTICO no servidor: {}", logDetail);
    }

    private static void logError(LogDetail logDetail) {
        logger.error("Erro no servidor: {}", logDetail);
    }

}
