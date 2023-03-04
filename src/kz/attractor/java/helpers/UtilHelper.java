package kz.attractor.java.helpers;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.Constants;
import kz.attractor.java.server.Cookie;

import java.io.IOException;
import java.util.Map;

public class UtilHelper {
    public static void redirect303(HttpExchange exchange, String path){
        try{
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(303, 0);
            exchange.getRequestBody().close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//    public static Map<String, String> getCookies(HttpExchange exchange) {
//        String cookies = exchange.getRequestHeaders()
//                .getOrDefault("Cookie", "");
//        return Cookie.parse(cookies);
//    }

    public static boolean isAutentificated(Map<String, String> cookies) {
        String value = cookies.get(Constants.NAME_UNIQUE_COOKIES);
        return value != null;
    }
}
