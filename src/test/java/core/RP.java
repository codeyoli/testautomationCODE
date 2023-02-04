package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;

public class RP {


    private static final Logger LOGGER = LoggerFactory.getLogger("binary_data_logger");

    private RP() {
        //statics only
    }


    public static void info(String message) {
        LOGGER.warn("RP_MESSAGE#{}#{}",message);
    }


    public static void info(byte[] bytes, String message) {
        LOGGER.info("RP_MESSAGE#BASE64#{}#{}", Base64.getEncoder().encodeToString(bytes), message);
    }


    public static void img(String base64, String message) {
        LOGGER.info("RP_MESSAGE#BASE64#{}#{}", base64, message);
    }
}

