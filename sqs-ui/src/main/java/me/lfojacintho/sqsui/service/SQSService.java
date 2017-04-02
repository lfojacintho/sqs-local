package me.lfojacintho.sqsui.service;

import me.lfojacintho.sqsui.model.QueueInfoResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lfojacintho@gmail.com">Luis Jacinhto</a>
 */
public class SQSService {

//    private final AmazonSQS sqsClient;

    public SQSService() {
//        final EndpointConfiguration endpointConfiguration = new EndpointConfiguration("http://localhost:9090", "us-west-1");
//        sqsClient = AmazonSQSClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).build();
    }

    public List<QueueInfoResponse> retrieveAllQueues() {
        return Collections.singletonList(new QueueInfoResponse("hahah", 2, 9, LocalDateTime.now()));
    }


}
