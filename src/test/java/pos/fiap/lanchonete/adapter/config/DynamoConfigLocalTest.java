package pos.fiap.lanchonete.adapter.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DynamoConfigLocalTest {

    @Test
    void testAmazonDynamoDBBeanLocalProfile() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = context.getEnvironment();

        environment.setActiveProfiles("local");
        context.register(DynamoConfigLocal.class);
        context.refresh();

        AmazonDynamoDB amazonDynamoDB = context.getBean(AmazonDynamoDB.class);

        assertNotNull(amazonDynamoDB);

        context.close();
    }
}