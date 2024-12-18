package pos.fiap.lanchonete.adapter.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.*;

class DynamoConfigTest {

    @Test
    void testAmazonDynamoDBBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = context.getEnvironment();

        environment.setActiveProfiles("default");
        context.register(DynamoConfig.class);
        context.refresh();

        AmazonDynamoDB amazonDynamoDB = context.getBean(AmazonDynamoDB.class);

        assertNotNull(amazonDynamoDB);

        context.close();
    }
}