package com.example.sweezcustoms.dto.response;

import java.util.UUID;

public record AiDtoResponse(
        UUID chatId,
        String answer
) {
}
