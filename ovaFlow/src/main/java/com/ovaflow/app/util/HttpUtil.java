package com.ovaflow.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Utilites to connect to a web service and post information to it.
 *
 * From http://rest.elkstein.org/2008/02/using-rest-in-java.html
 */
public class HttpUtil {

    /**
     * Send a HTTP Post string to the web service at urlStr.
     *
     * @param urlStr
     *            The url of the web service.
     * @param paramName
     *            An array of strings containing the names of the parameters.
     * @param paramVal
     *            An array of strings containing the values paired with the
     *            parameter names.
     * @return The response from the web service.
     * @throws HttpPostException
     */
    public static String httpPOST(String urlStr, String[] paramName,
                                  String[] paramVal) throws HttpPostException {
        try {
            URL url = new URL("http://"+urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            // Create the form content
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            for (int i = 0; i < paramName.length; i++) {
                writer.write(paramName[i]);
                writer.write("=");
                writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
                writer.write("&");
            }
            writer.close();
            out.close();

            if (conn.getResponseCode() != 200) {
                throw new IOException(conn.getResponseMessage());
            }

            // Buffer the result into a string
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            throw new HttpPostException(e);
        }
    }

    public static String httpGET(String urlStr, String[] paramName,
                                  String[] paramVal) throws HttpPostException {
        String temp = urlStr+"?";
        for (int i = 0; i < paramName.length; i++) {
            temp = temp+paramName[i]+"="+paramVal[i] + "&";
        }
        temp = "http://"+temp.substring(0,temp.length()-1);
        System.out.println(temp);
        try {
            URL url = new URL(temp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //conn.setAllowUserInteraction(false);
            //conn.setRequestProperty("Content-Type",
                    //"application/x-www-form-urlencoded");


            conn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            return sb.toString();

        } catch (Exception e) {
            throw new HttpPostException(e);
        }
    }
}
