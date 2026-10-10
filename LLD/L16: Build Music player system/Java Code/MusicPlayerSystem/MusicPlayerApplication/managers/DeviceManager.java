package MusicPlayerApplication.managers;

import MusicPlayerApplication.device.IAudioOutputDevice;
import MusicPlayerApplication.enums.DeviceType;
import MusicPlayerApplication.factories.DeviceFactory;

public class DeviceManager {
    private static DeviceManager instance = null;
    private IAudioOutputDevice currentOutputDevice;

    private DeviceManager() {
        currentOutputDevice = null;
    }

    public static synchronized DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }

    public void connect(DeviceType deviceType) {
        if (currentOutputDevice != null) {
            // In C++: delete currentOutputDevice;
            // In Java, garbage collector handles it, so no explicit delete.
        }

        currentOutputDevice = DeviceFactory.createDevice(deviceType);

        switch (deviceType) {
            case BLUETOOTH:
                System.out.println("Bluetooth device connected ");
                break;
            case WIRED:
                System.out.println("Wired device connected ");
                break;
            case HEADPHONES:
                System.out.println("Headphones connected ");
                break;
        }
    }

    public IAudioOutputDevice getOutputDevice() {
        if (currentOutputDevice == null) {
            throw new RuntimeException("No output device is connected.");
        }
        return currentOutputDevice;
    }

    public boolean hasOutputDevice() {
        return currentOutputDevice != null;
    }
}
