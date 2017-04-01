package me.lfojacintho.sqsui;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

/**
 * @author <a href="mailto:lfojacintho@gmail.com">Luis Jacinhto</a>
 */
public class Main {

    public static void main(String[] args) {
        get("/", (request, response) -> {
            final Map<String, Object> model = new HashMap<>();
            model.put("message", "Hello SQS!");

            return new ModelAndView(model, "index");
        }, new ThymeleafTemplateEngine());
    }
}
