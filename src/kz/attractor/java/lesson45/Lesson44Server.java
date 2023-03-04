package kz.attractor.java.lesson45;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kz.attractor.java.Books.BooksDataModel;
import kz.attractor.java.Employee.EmployeeDataModel;
import kz.attractor.java.server.BasicServer;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.ResponseCodes;

import java.io.*;

public class Lesson44Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    public Lesson44Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/sample", this::freemarkerSampleHandler);
        registerGet("/books", this:: booksHandler);
        registerGet("/employee", this::employeeHandler);
        registerGet("/books/bookInfo", this:: bookInfoHandler);
    }

    private void bookInfoHandler(HttpExchange exchange) {
        renderTemplate(exchange, "book.html", getBookDataModel());
    }

    private Object getBookDataModel() {
        return new BookDataModel();
    }

    private void employeeHandler(HttpExchange exchange) {
        renderTemplate(exchange, "employee.ftlh", getEmployeeDataModel());
    }

    private void booksHandler(HttpExchange exchange) {
        renderTemplate(exchange, "books.ftlh", getBooksDataModel());
    }


    BooksDataModel getBooksDataModel() {
        return new BooksDataModel();
    }

    private EmployeeDataModel getEmployeeDataModel() {
        return new EmployeeDataModel();
    }


    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.html", getSampleDataModel());
    }

protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
    try {
        Template temp = freemarker.getTemplate(templateFile);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
            temp.process(dataModel, writer);
            writer.flush();

            var data = stream.toByteArray();

            sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
        }
    } catch (IOException | TemplateException e) {
        e.printStackTrace();
    }
}

    private SampleDataModel getSampleDataModel() {
        return new SampleDataModel();
    }
}
