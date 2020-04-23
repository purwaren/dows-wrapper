package id.dailyinn.wrapper;

public class DowsJniApiInterface {
    static {
        System.loadLibrary("dcrf32");
    }

    public native int dv_connect(int beep);
}
