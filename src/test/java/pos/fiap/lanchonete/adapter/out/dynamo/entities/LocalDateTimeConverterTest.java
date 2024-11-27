package pos.fiap.lanchonete.adapter.out.dynamo.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeConverterTest {

    private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

    @Test
    void testConvert() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 11, 26, 15, 30, 45);
        String result = converter.convert(dateTime);
        assertEquals("2024-11-26T15:30:45", result);
    }

    @Test
    void testConvertNull() {
        String result = converter.convert(null);
        assertNull(result);
    }

    @Test
    void testUnconvert() {
        String dateTimeString = "2024-11-26T15:30:45";
        LocalDateTime result = converter.unconvert(dateTimeString);

        assertEquals(LocalDateTime.of(2024, 11, 26, 15, 30, 45), result);
    }

    @Test
    void testUnconvertNull() {
        LocalDateTime result = converter.unconvert(null);
        assertNull(result);
    }
}