package kz.attractor.java.handlers;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.Constants;
import kz.attractor.java.helpers.UtilHelper;
import kz.attractor.java.server.BasicServer;
import kz.attractor.java.server.Cookie;
import kz.attractor.java.server.RouteHandler;

import java.io.IOException;
import java.util.Map;


public abstract class BaseHandler implements RouteHandler {

    private final Map<String, String> identificators;

    public BaseHandler(Map<String, String> loginidentificator) {
        this.identificators = loginidentificator;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var cookies = BasicServer.getCookies(exchange);
        Map<String, String> parse = Cookie.parse(cookies);
        if(!isAutentificated(parse)){
            UtilHelper.redirect303(exchange, "/login");
        }
       eventHandler(exchange);
    }

    private boolean isAutentificated(Map<String, String> cookies) {
        return false;
    }


    public abstract String  eventHandler(HttpExchange httpExchange);
}
