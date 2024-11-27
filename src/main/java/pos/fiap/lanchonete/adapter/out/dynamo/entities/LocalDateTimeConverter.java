package pos.fiap.lanchonete.adapter.out.dynamo.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convert(LocalDateTime object) {
        return object != null ? object.format(formatter) : null;
    }

    @Override
    public LocalDateTime unconvert(String object) {
        return object != null ? LocalDateTime.parse(object, formatter) : null;
    }
}
