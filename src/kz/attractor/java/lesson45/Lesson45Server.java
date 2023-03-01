package kz.attractor.java.lesson45;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.Employee.Employee;
import kz.attractor.java.Employee.EmployeeDataModel;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Lesson45Server extends Lesson44Server {

    boolean profile = true;

    Employee newEmployee = new Employee(0,null, null, null, null, null, null);


    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/register", this::registerGet);
        registerPost("/register", this::registerPost);
        registerGet("/error", this::registerError);
        registerGet("/done", this::registerDone);
        registerGet("/login/error", this::loginErrorGet);
        registerGet("/profile", this::profileGet);
    }

    private void profileGet(HttpExchange exchange) {
        if(!profile){
        renderTemplate(exchange, "profile.html", newEmployee);}
        else {
            newEmployee = new Employee(1,"Example", "Example", "example@gmail", "example", null, null );
            renderTemplate(exchange, "profile.html", newEmployee);
        }
    }


    private void loginErrorGet(HttpExchange exchange) {
        Path path = makeFilePath("loginError.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void registerDone(HttpExchange exchange) {
        Path path = makeFilePath("done.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void registerError(HttpExchange exchange) {
        Path path = makeFilePath("error.html");
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
                redirect303(exchange, "/error");
            }
        }
        if (checkEmail) {
            Employee employee = new Employee(id, name, surname, email, password, List.of(), List.of());
            employees.add(employee);
            EmployeeDataModel.writeEmployersFile(employees);
            redirect303(exchange, "/done");
        }
    }

    private void registerGet(HttpExchange exchange) {
        Path path = makeFilePath("register.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }


    private void loginPost(HttpExchange exchange)  {
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
                break;
            }
        }
        if (user != null) {
            redirect303(exchange, "/profile");
        } else {
            redirect303(exchange, "/login/error");
        }
    }


    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }
}
