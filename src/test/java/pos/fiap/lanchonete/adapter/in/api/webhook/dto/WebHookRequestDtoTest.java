package pos.fiap.lanchonete.adapter.in.api.webhook.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebHookRequestDtoTest {

    @Test
    void testWebHookRequestDtoBuilder() {
        String resource = "order";
        String topic = "payment";

        WebHookRequestDto webHookRequestDto = WebHookRequestDto.builder()
                .resource(resource)
                .topic(topic)
                .build();

        assertNotNull(webHookRequestDto);
        assertEquals(resource, webHookRequestDto.getResource());
        assertEquals(topic, webHookRequestDto.getTopic());
    }
}