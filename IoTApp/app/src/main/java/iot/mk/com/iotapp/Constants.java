package iot.mk.com.iotapp;

/**
 * Created by rajesh on 21/01/16.
 */
public interface Constants {

    String WEBSERVER_IP = "192.168.43.94";

    String LAMP_TOPIC = "/kitchen/lamp";
    String TEMPERATURE_GAUGE_TOPIC = "/balkon/temperature";
    String DIMMER_LIGHT_TOPIC = "/roomlight1/dimmer";
    String MAIN_DOOR_TOPIC = "/beta/door";
    String WATER_VALVE_TOPIC = "/valve/water";
    String GARAGE_TOPIC = "/car/garrage";
    enum Action {PUBLISH, SUBSCRIBE};

    String LOGIN_URL = "http://"+WEBSERVER_IP+":8540/IoT_App_User_WebService/Login";
    String REGISTRER_URL = "http://"+WEBSERVER_IP+":8540/IoT_App_User_WebService/Registration";

}
