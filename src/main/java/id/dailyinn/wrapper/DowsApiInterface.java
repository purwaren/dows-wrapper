package id.dailyinn.wrapper;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface DowsApiInterface extends Library {
    DowsApiInterface INSTANCE = (DowsApiInterface) Native.load("dcrf32", DowsApiInterface.class);
    //DowsApiInterface INSTANCE = (DowsApiInterface) Native.load("CLock", DowsApiInterface.class);
    int dv_connect(int beep);
    int dv_disconnect();
    int dv_check_card();
    int dv_verify_card();
    String dv_get_auth_code();
    String dv_get_card_number();
}
