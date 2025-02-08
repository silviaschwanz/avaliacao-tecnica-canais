package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.log;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response.TypeError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarnLogger {

    private static final Logger logger = LoggerFactory.getLogger(WarnLogger.class);

    public static void log(TypeError typeError, String path, Throwable throwable) {
        LogDetail logDetail = new LogDetail(typeError, path, throwable);
        logger.warn("Aviso na requisição: {}", logDetail);
    }

}
