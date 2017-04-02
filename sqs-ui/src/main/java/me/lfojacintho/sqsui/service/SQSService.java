package me.lfojacintho.sqsui.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import me.lfojacintho.sqsui.model.QueueInfoResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lfojacintho@gmail.com">Luis Jacinhto</a>
 */
public class SQSService {

    private static final String SQS_ENDPOINT = "http://localhost:9324";
    private static final String SQS_REGION = "dummy-region";
    private static final String SQS_KEY = "dummy-key";
    private static final String SQS_SECRET = "dummy-secret";
    public static final String ATTRIBUTE_ARN = "QueueArn";
    public static final String ATTRIBUTE_NUMBER_OF_MESSAGES = "ApproximateNumberOfMessages";
    public static final String ATTRIBUTE_MESSAGES_IN_FLIGHT = "ApproximateNumberOfMessagesNotVisible";
    public static final String ATTRIBUTE_CREATED_TIMESTAMP = "CreatedTimestamp";

    private final AmazonSQS sqsClient;

    public SQSService() {
        final EndpointConfiguration endpointConfiguration = new EndpointConfiguration(SQS_ENDPOINT, SQS_REGION);
        final BasicAWSCredentials credentials = new BasicAWSCredentials(SQS_KEY, SQS_SECRET);
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        sqsClient = AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider)
                .build();
    }

    public List<QueueInfoResponse> retrieveAllQueues() {
        final ListQueuesResult listQueuesResult = sqsClient.listQueues();
        final List<String> queueUrlList = listQueuesResult.getQueueUrls();

        if (queueUrlList.isEmpty()) {
            return Collections.emptyList();
        }

        return queueUrlList.stream().map(this::retrieveAttributesFromUrl).collect(Collectors.toList());
    }

    private QueueInfoResponse retrieveAttributesFromUrl(final String queueUrl) {
        final List<String> requestAttributesList = new ArrayList<>(4);
        requestAttributesList.add(ATTRIBUTE_ARN);
        requestAttributesList.add(ATTRIBUTE_NUMBER_OF_MESSAGES);
        requestAttributesList.add(ATTRIBUTE_MESSAGES_IN_FLIGHT);
        requestAttributesList.add(ATTRIBUTE_CREATED_TIMESTAMP);

        final GetQueueAttributesResult attributesResult = sqsClient.getQueueAttributes(queueUrl, requestAttributesList);
        final Map<String, String> queueAttribute = attributesResult.getAttributes();
        final String[] arnArray = queueAttribute.get(ATTRIBUTE_ARN).split(":");
        final String queueName = arnArray[arnArray.length - 1];
        final int messagesAvailable = Integer.parseInt(queueAttribute.get(ATTRIBUTE_NUMBER_OF_MESSAGES));
        final int messagesInFlight = Integer.parseInt(queueAttribute.get(ATTRIBUTE_MESSAGES_IN_FLIGHT));
        final Instant createdInstant = Instant.ofEpochSecond(Long.parseLong(queueAttribute.get(ATTRIBUTE_CREATED_TIMESTAMP)));
        final LocalDateTime created = LocalDateTime.ofInstant(createdInstant, ZoneId.systemDefault());
        return new QueueInfoResponse(queueName, messagesAvailable, messagesInFlight, created);
    }


}
