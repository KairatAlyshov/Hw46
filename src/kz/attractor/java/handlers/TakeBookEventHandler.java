package kz.attractor.java.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class TakeBookEventHandler extends BaseHandler{


    public TakeBookEventHandler(Map<String, String> loginidentificator) {
        super(loginidentificator);
    }

    public String eventHandler(HttpExchange httpExchange){
return null;
    }

}
