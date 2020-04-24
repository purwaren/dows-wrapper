package id.dailyinn.wrapper;

public class DowsJniApiInterface {
    static {
        System.loadLibrary("dcrf32");
    }

    private native int dv_connect(int beep);
}
