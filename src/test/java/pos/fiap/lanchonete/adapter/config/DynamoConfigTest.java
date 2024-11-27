package pos.fiap.lanchonete.adapter.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class DynamoConfigTest {

    @Test
    void testAmazonDynamoDBBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(DynamoConfig.class);
        context.refresh();

        AmazonDynamoDB amazonDynamoDB = context.getBean(AmazonDynamoDB.class);

        assertNotNull(amazonDynamoDB);

        context.close();
    }
}