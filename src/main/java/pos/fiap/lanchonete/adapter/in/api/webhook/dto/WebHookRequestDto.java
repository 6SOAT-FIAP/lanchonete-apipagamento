package pos.fiap.lanchonete.adapter.in.api.webhook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebHookRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5018799325894877872L;

    private String resource;
    private String topic;
}
