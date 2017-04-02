package me.lfojacintho.sqsui;

import me.lfojacintho.sqsui.route.SQSRoute;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.get;

/**
 * @author <a href="mailto:lfojacintho@gmail.com">Luis Jacinhto</a>
 */
public class Main {

    private static final String PREFIX = "/src/main/resources/templates/";
    private static final String SUFFIX = ".html";

    public static void main(String[] args) {
        final ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine(getRemplaterResolver());
        final SQSRoute sqsRoute = new SQSRoute();

        get("/", sqsRoute::listQueues, templateEngine);
    }

    /*
     * Custom template resolver to run on local environment only.
     * With this template resolver, you don't need to rebuild the project on each
     * template change.
     */
    private static ITemplateResolver getRemplaterResolver() {
        final FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();

        final String userDir = System.getProperty("user.dir");
        fileTemplateResolver.setTemplateMode(TemplateMode.HTML);
        fileTemplateResolver.setPrefix(userDir + PREFIX);
        fileTemplateResolver.setSuffix(SUFFIX);
        fileTemplateResolver.setCacheable(false);

        return fileTemplateResolver;
    }
}
