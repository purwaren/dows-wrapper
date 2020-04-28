package id.dailyinn.wrapper;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface DowsApiInterface extends Library {
    //DowsApiInterface INSTANCE = (DowsApiInterface) Native.load("dcrf32", DowsApiInterface.class);
    DowsApiInterface INSTANCE = (DowsApiInterface) Native.load("CLock", DowsApiInterface.class);
    int dv_connect(int beep);
    int dv_disconnect();
    int dv_check_card();
    int dv_verify_card(Pointer ctype);
    int dv_get_auth_code(Pointer auth);
    int dv_get_card_number(Pointer ptr);
    int dv_read_card(Pointer auth, Pointer cardno, Pointer building, Pointer room, Pointer door, Pointer arrival, Pointer departure);
    int dv_write_card(Pointer auth, Pointer building, Pointer room, Pointer door, Pointer arrival, Pointer departure);
    int dv_delete_card(Pointer room);
}
