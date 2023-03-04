package kz.attractor.java.lesson45;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.Employee.Employee;
import kz.attractor.java.Employee.EmployeeDataModel;
import kz.attractor.java.handlers.TakeBookEventHandler;
import kz.attractor.java.helpers.UtilHelper;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.Cookie;
import kz.attractor.java.server.ResponseCodes;
import kz.attractor.java.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Lesson46Server extends Lesson44Server {

    boolean profile = true;

    Employee newEmployee = new Employee(0,null, null, null, null, null, null);


    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login", exchange -> loginGet(exchange));
        registerPost("/login", this::loginPost);
        registerGet("/register", this::registerGet);
        registerPost("/register", this::registerPost);
        registerGet("/registerError", this::registerError);
        registerGet("/registerDone", this::registerDone);
        registerGet("/loginError", this::loginErrorGet);
        registerGet("/profile", this::profileGet);
        registerGet( "/takeBook",this::takeBookGet);
        registerPost("/takeBook",this::takeBookPost);
        registerGet( "/backBook",this::backBookGet);
        registerPost("/backbook",this::backBookPost);
    }

    private void backBookPost(HttpExchange exchange) {

    }

    private void backBookGet(HttpExchange exchange) {
        renderTemplate(exchange, "backBook.ftlh",getBooksDataModel());
    }

    private void takeBookGet(HttpExchange exchange) {
        renderTemplate(exchange, "takeBook.ftlh",getBooksDataModel());
    }



    private  void cookies(HttpExchange exchange,String email){
        try {
            Cookie cookies = Cookie.make("email",email);
            cookies.setMaxAge(600);
            cookies.setHttpOnly(true);
            Map<String,Object> object = new HashMap<>();
            String cookie = getCookies(exchange);
            Map<String,String> parse = Cookie.parse(cookie);
            object.put(parse.toString(),email);
            exchange.getResponseHeaders().add("Set-Cookie", cookies.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void takeBookPost(HttpExchange exchange) {




        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            if(identificator(exchange, " email")) {
                int bookID = Integer.parseInt(parsed.get("id"));
                List<Employee> employees = EmployeeDataModel.readEmployersFile();
                Employee employee = employees.get(bookID);
                if (employee.getReading().size() >= 2) {
                    String state = "you can't take more than 2 books";
                    String data = String.format("<p>"+"<b>%s</b></p>", state);
                    sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data.getBytes());
                    renderTemplate(exchange, "takeBook.ftlh",getBooksDataModel());
                } else {
                    String state = "you can ";
                    String data = String.format("<p>"+"<b>%s</b></p>", state);
                   EmployeeDataModel.writeEmployersFile(employees);
                    sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data.getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean identificator(HttpExchange exchange, String email){
        String cookie = getCookies(exchange);
        Map<String,String> parse = Cookie.parse(cookie);
        String emailUser = parse.get(" email");
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

    private void profileGet(HttpExchange exchange) {
        if(!profile){
        renderTemplate(exchange, "profile.html", newEmployee);}
        else {
            newEmployee = new Employee(0,"Example", "Example", "example@gmail", "example", null, null );
            renderTemplate(exchange, "profile.html", newEmployee);
        }
    }

    public static void redirect303(HttpExchange exchange, String path){
        try{
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(303, 0);
            exchange.getRequestBody().close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private void loginErrorGet(HttpExchange exchange) {
        Path path = makeFilePath("loginError.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void registerDone(HttpExchange exchange) {
        Path path = makeFilePath("registerDone.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void registerError(HttpExchange exchange) {
        Path path = makeFilePath("registerError.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void registerPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        List<Employee> employees = EmployeeDataModel.readEmployersFile();

        int id = employees.size() + 1;
        String name = parsed.get("name");
        String surname = parsed.get("surname");
        String email = parsed.get("email");
        String password = parsed.get("password");
        boolean checkEmail = true;
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email)) {
                checkEmail = false;
                redirect303(exchange, "/registerError");
            }
        }
        if (checkEmail) {
            Employee employee = new Employee(id, name, surname, email, password, List.of(), List.of());
            employees.add(employee);
            EmployeeDataModel.writeEmployersFile(employees);
            redirect303(exchange, "/registerDone");
        }
    }

    private void registerGet(HttpExchange exchange) {
        Path path = makeFilePath("register.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }


    private void loginPost(HttpExchange exchange) {

        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        List<Employee> employees = EmployeeDataModel.readEmployersFile();
        String email = parsed.get("email");
        String password = parsed.get("password");
        Employee user = null;
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email) && employee.getPassword().equals(password)) {
                user = employee;
                newEmployee = user;
                profile = false;
                cookies(exchange, email);
                break;
            }
        }
        if (user != null) {
            redirect303(exchange, "/profile");
        } else {
            redirect303(exchange, "/loginError");
        }
    }


    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

}
