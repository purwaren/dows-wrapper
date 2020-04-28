package id.dailyinn.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jna.Memory;
import com.sun.jna.Native;


public class Wrapper {
    volatile static boolean deviceOpen = false;
    public static void main(String args[]) {
        Logger logger = LoggerFactory.getLogger(Wrapper.class);
        logger.info("Starting Dows Interface...");
        DowsApiInterface dows = DowsApiInterface.INSTANCE;
        dows.dv_connect(1);
        
        int cardType = dows.dv_check_card();
        logger.info("dv_check_card == {}", cardType);
        
        Memory mem = new Memory(Native.getNativeSize(int.class));
        int ctype = dows.dv_verify_card(mem);
        logger.info("[return] dv_verify_card == {}", ctype);
        logger.info("[out] dv_verify_card == {}", mem.getInt(0));

        Memory mem1 = new Memory(6);
        dows.dv_get_auth_code(mem1);
        logger.info("[out] dv_get_auth_code == {}", mem1.getString(0, "UTF-8"));

        Memory card = new Memory(6);
        dows.dv_get_card_number(card);
        logger.info("[out] dv_get_card_number == {}", card.getString(0, "UTF-8"));

        String authCode = "205741";
        Memory auth = new Memory(authCode.length());
        auth.write(0, authCode.getBytes(), 0, authCode.length());
    }
}
