package kz.attractor.java;
import kz.attractor.java.lesson45.Lesson45Lesson;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Lesson45Lesson("localhost", 9884).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
