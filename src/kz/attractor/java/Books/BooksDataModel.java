package kz.attractor.java.Books;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BooksDataModel {
    private List<Book> books = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = Paths.get("data/books.json");

    public BooksDataModel() {
        books.addAll(getBook());
    }

    public List<Book> getBook(){
        String json = "";
        try {
            json = Files.readString(PATH);
        }catch (IOException e){
            e.printStackTrace();
        }    return GSON.fromJson(json, new TypeToken<List<Book>>() {}.getType());
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
