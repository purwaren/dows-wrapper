package id.dailyinn.wrapper;

import java.util.logging.Logger;

public class Wrapper {
    public static void main(String args[]) {
        Logger logger = Logger.getLogger(Wrapper.class.getName());
        logger.info("Starting Dows Interface...");
        System.setProperty("jna.library.path", "C:\\Program Files (x86)\\DOWS\\LockingSystem5.1");
        DowsApiInterface dows = DowsApiInterface.INSTANCE;
        dows.dv_connect(1);
    }
}
