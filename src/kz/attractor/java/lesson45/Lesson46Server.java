package kz.attractor.java.lesson45;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.Employee.Employee;
import kz.attractor.java.Employee.EmployeeDataModel;
import kz.attractor.java.server.Cookie;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson46Server extends Lesson45Server {
    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
    }

    private void cookies(HttpExchange exchange, String email) {
        try {
            List<Employee> em = EmployeeDataModel.readEmployersFile();
            Cookie cookie = Cookie.make("userId: " + em.get(0).getId(), email);
            cookie.setMaxAge(60);
            cookie.setHttpOnly(true);
            Map<String, Object> object = new HashMap<>();
            String cookies = getCookies(exchange);
            Map<String, String> parse = Cookie.parse(cookies);
            object.put(parse.toString(), email);
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean identificator(HttpExchange exchange, String email){
        String cookie = getCookies(exchange);
        Map<String,String> parse = Cookie.parse(cookie);
        String emailUser = parse.get("email");
        return emailUser.equals(email);
    }

//        Cookie sessionCookie = Cookie.make("userId","123");
//        exchange.getResponseHeaders()
//                .add("Set-Cookie", sessionCookie.toString());
//
//        Map<String, Object> data = new HashMap<>();
//        int times =42;
//        data.put("times",times);
//
//        Cookie c1 = Cookie.make("user%Id", "456");
//        setCookie(exchange, c1);
//
//        Cookie c2 = Cookie.make("restricted()<>@,;;\\\"/[]?={}", "restricted()<>@,;;\\\"/[]?={}");
//        setCookie(exchange, c2);
//
//        Cookie c3 = Cookie.make("user-mail", "example@mail.com");
//        setCookie(exchange, c3);
//
//        String cookieString = getCookies(exchange);
//        Map<String, String> cookies = Cookie.parse(cookieString);
//        data.put("cookies",cookies);

//        Map<String, Object> data = new HashMap<>();
//        String name = "times";
//        String cookieStr = getCookies(exchange);
//        Map<String, String> cookies = Cookie.parse(cookieStr);
//
//        String cookieValue = cookies.getOrDefault(name, "0");
//        int times = Integer.parseInt(cookieValue) + 1;
//        Cookie response = new Cookie(name, times);
//        setCookie(exchange, response);
//
//        data.put(name, times);
//        data.put("cookies", cookies);
//        renderTemplate(exchange,"cookie.html",data);
    }
