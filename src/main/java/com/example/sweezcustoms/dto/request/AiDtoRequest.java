package com.example.sweezcustoms.dto.request;

import java.util.UUID;

public record AiDtoRequest(
        UUID id,
        String query
) {
}
