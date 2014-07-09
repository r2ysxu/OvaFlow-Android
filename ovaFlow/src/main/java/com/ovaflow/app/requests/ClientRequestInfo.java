package com.ovaflow.app.requests;

/**
 * Created by ArthurXu on 09/07/2014.
 */
public class ClientRequestInfo {

    public static final String hostIp = "192.168.0.15";
    public static final String hostPort = "8080";

    public static final String generateRequest(String resourcePath, String[] paramKeys, String[] paramValues) {
        String resource = "http://" + hostIp + ":" + hostPort + resourcePath;

        if (paramKeys.length > 0) {
            resource += "?";
        }

        for (int i = 0; i < paramKeys.length; i++) {
            String key = paramKeys[i];
            String value = paramValues[i];
            resource += key + "=" + value;
            if (i < paramKeys.length - 1) {
                resource += "&";
            }
        }
        return resource;
    }
}
