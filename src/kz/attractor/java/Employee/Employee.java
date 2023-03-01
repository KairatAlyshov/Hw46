package kz.attractor.java.Employee;
import kz.attractor.java.Books.Book;

import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<Book> reading;
    private List<Book> read;


    public Employee(int id, String name, String surname, String email, String password, List<Book> reading, List<Book> read) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.reading = reading;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getReading() {
        return reading;
    }

    public void setReading(List<Book> reading) {
        this.reading = reading;
    }

    public List<Book> getRead() {
        return read;
    }

    public void setRead(List<Book> read) {
        this.read = read;
    }
}
