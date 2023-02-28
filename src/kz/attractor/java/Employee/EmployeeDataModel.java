package kz.attractor.java.Employee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataModel {
    private List<Employee> employees = new ArrayList<>();
    private Employee employee;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = Paths.get("data/employee.json");

    public EmployeeDataModel() {
        employees.addAll(readEmployersFile());
        this.employee = employees.get(0);
    }
    public static List<Employee> readEmployersFile(){
        String json = "";
        try {
            json = Files.readString(PATH);
        }catch (IOException e){
            e.printStackTrace();
        }        return GSON.fromJson(json, new TypeToken<List<Employee>>() {}.getType());
    }

    public static void writeEmployersFile(List<Employee> employeeList){
        String str = GSON.toJson(employeeList);
        try {
            byte[] strToBytes = str.getBytes();
            Files.write(PATH, strToBytes);
        }catch (IOException e){
            e.printStackTrace();        }
    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
