package core;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Logs {

    private static ExtentTest testCase;
    private static final Logger LOGGER = LoggerFactory.getLogger("binary_data_logger");


    static public void init(ExtentTest tc) {
        testCase = tc;
    }

    static public void _info(String msg) {
        testCase.info(msg);
    }

    static public void _info(String msg, Media media) {
        testCase.info(msg,media);
    }

    static public void _pass(String msg) {
        testCase.pass(msg);
    }

    static public void _pass(String msg, Media media) {
        testCase.pass(msg,media);
    }

    static public void _warn(String msg) {
        testCase.warning(msg);
    }

    static public void _warn(String msg, Media media) {
        testCase.warning(msg,media);
    }

    static public void _fail(String msg) {
        testCase.fail(msg);
    }

    static public void _fail(String msg, Media media) {
        testCase.fail(msg,media);
    }

    static public void _cause(Throwable error) {
        testCase.info( error.getMessage());
    }

    static public void _img(String base64) {
        testCase.info(base64);
    }


    public static void _RPINFO(String message) {
        LOGGER.warn("RP_MESSAGE#{}#{}",message);
    }


    public static void _RPIMG(String base64, String message) {
        LOGGER.info("RP_MESSAGE#BASE64#{}#{}", base64, message);
    }


}
