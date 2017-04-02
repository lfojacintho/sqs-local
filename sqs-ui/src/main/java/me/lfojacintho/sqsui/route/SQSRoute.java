package me.lfojacintho.sqsui.route;

import me.lfojacintho.sqsui.service.SQSService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:lfojacintho@gmail.com">Luis Jacinhto</a>
 */
public class SQSRoute {

    private final SQSService sqsService = new SQSService();

    public ModelAndView listQueues(final Request request, final Response response) {
        final Map<String, Object> model = new HashMap<>();

        model.put("queueList", sqsService.retrieveAllQueues());

        return new ModelAndView(model, "index");
    }
}
