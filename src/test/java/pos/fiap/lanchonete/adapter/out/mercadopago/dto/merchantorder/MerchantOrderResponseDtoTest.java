package pos.fiap.lanchonete.adapter.out.mercadopago.dto.merchantorder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MerchantOrderResponseDtoTest {

    @Test
    void testMerchantOrderResponseDto() {
        String externalReference = "external123";

        MerchantOrderResponseDto dto = MerchantOrderResponseDto.builder()
                .externalReference(externalReference)
                .build();

        assertNotNull(dto);
        assertEquals(externalReference, dto.getExternalReference());
    }
}
