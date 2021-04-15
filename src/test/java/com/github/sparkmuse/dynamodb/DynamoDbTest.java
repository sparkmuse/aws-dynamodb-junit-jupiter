package com.github.sparkmuse.dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DynamoDbTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    @BeforeAll
    public static void setupClass() throws Exception {
        System.setProperty("sqlite4java.library.path", "target/native-libs");
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
    }

    @AfterAll
    public static void teardownClass() throws Exception {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void canSaveDocument() {

        AmazonDynamoDB dynamoDB = amazonDynamoDBLocal.amazonDynamoDB();

        DynamoDBMapper dbObjectMapper = new DynamoDBMapper(dynamoDB);

        CreateTableRequest createTableRequest = dbObjectMapper.generateCreateTableRequest(Person.class);
        createTableRequest.withProvisionedThroughput(new ProvisionedThroughput(1L,1L));

        CreateTableResult table = dynamoDB.createTable(createTableRequest);

        Assertions.assertNotNull(table);

    }
}
