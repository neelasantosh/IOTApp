package iot.mk.com.iotapp;

/**
 * Created by  Rajesh on 1/23/2016.
 */
public class ConnectionConfiguration {

    private String ipAddress;
    private int port;
    private boolean cleanSession;
    private String clientId;

    public ConnectionConfiguration(String ipAddress, int port, boolean cleanSession, String clientId) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.cleanSession = cleanSession;
        this.clientId = clientId;
    }

    @Override public String toString() {
        return ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
