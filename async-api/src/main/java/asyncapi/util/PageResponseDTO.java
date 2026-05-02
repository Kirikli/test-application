package asyncapi.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PageResponseDTO<T>(
        List<T> content,
        long totalElements,
        long totalPages) {
}
