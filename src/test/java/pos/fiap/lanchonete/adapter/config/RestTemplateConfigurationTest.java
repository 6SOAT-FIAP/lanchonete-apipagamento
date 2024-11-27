package pos.fiap.lanchonete.adapter.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RestTemplateConfigurationTest {

    @Test
    void testRestTemplateBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(RestTemplateConfiguration.class);
        context.refresh();

        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        assertNotNull(restTemplate);

        context.close();
    }
}
