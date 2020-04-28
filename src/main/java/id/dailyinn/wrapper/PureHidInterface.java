package id.dailyinn.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import purejavahidapi.*;

import java.io.IOException;
import java.util.List;


public class PureHidInterface implements Runnable {
    Logger logger = LoggerFactory.getLogger(getClass());
    public volatile static boolean deviceOpen = false;
    public  HidDevice hidDevice;
    private HidDeviceInfo deviceInfo;
    private final short vendorId;
    private  final short productId;

    //info.getVendorId() == (short) 0x0471 && info.getProductId() == (short) 0xA112
    public PureHidInterface(short vendorId, short productId) {
        this.vendorId = vendorId;
        this.productId = productId;
    }

    private void scanAvailableEncoder() {
        List<HidDeviceInfo> devList = PureJavaHidApi.enumerateDevices();
        for (HidDeviceInfo info : devList) {
            if (info.getVendorId() == vendorId && info.getProductId() == productId) {
                this.deviceInfo = info;
                logger.info("Encoder is found");
                break;
            }
        }
        if (deviceInfo == null) {
            logger.info("Encoder is not detected, please disconnect & reconnect the device");
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                openDevice();
            } catch (InterruptedException e) {
                logger.error("Interrupted exception", e);
            }
            logger.info("device info === {}", deviceInfo);
            logger.info("device open === {}", deviceOpen);
        }
    }

    private void openDevice() throws InterruptedException {
        //scan available devices
        scanAvailableEncoder();
        if (deviceInfo != null && !deviceOpen) {
            deviceOpen = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        hidDevice = PureJavaHidApi.openDevice(deviceInfo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    hidDevice.setDeviceRemovalListener((HidDevice hidDevice) -> {
                        logger.info("Encoder was unplug");
                        deviceOpen = false;
                    });
                    hidDevice.setInputReportListener((HidDevice hidDevice, byte id, byte[] data, int len) -> {
                        System.out.printf("onInputReport: id %d len %d data ", id, len);
                        for (int i = 0; i < len; i++) {
                            System.out.printf("%02X ", data[i]);
                        }
                        System.out.println();
                    });
                    byte reportId = 0x00;
                    byte[] data = {0x02, 0x02, (byte) 0x80, 0x00, (byte) 0x82, 0x64, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xA0, 0x47,
                            (byte) 0xB9, 0x00, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x06, 0x00, 0x60, 0x62, (byte) 0xBF};
                    hidDevice.setFeatureReport(reportId, data, 32);
                }
            }).start();
        }
        else if (deviceInfo != null) {
            logger.info("Device currently opened, sleep thread for 30s");
            Thread.sleep(5000);
        }
        else {
            Thread.sleep(5000);
        }
    }
}
