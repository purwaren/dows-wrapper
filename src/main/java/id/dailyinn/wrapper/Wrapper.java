package id.dailyinn.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import purejavahidapi.*;

import java.util.List;

public class Wrapper {
    volatile static boolean deviceOpen = false;
    public static void main(String args[]) {
        Logger logger = LoggerFactory.getLogger(Wrapper.class);
        logger.info("Starting Dows Interface...");
        PureHidInterface hidInterface = new PureHidInterface((short) 0x0471, (short) 0xA112);
        new Thread(hidInterface).start();
    }
}
