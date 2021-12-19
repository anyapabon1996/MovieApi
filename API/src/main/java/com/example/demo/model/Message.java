package com.example.demo.model;
import org.apache.logging.log4j.Level;
//Importamos los paquetes para el loggers
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class Message {
    final static Logger logger = LogManager.getLogger(Message.class);

    public void logMessage(int parameter) {

        // Este codigo me permite configurar el nivel de mensaje de Log4J
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        org.apache.logging.log4j.core.config.Configuration conf = ctx.getConfiguration();
        conf.getLoggerConfig(LogManager.ROOT_LOGGER_NAME).setLevel(Level.INFO);
        ctx.updateLoggers(conf);

        if (parameter == 1)
            logger.error("Ha ingresado un valor que no corresponde. Debe ser numerico");
        else if (parameter == 2)
            logger.warn("Ten Cuidado. Puedes ingresar numeros entre 0 y 3");
        else
            logger.fatal("Este es un mensaje Fatal");
    }
}
