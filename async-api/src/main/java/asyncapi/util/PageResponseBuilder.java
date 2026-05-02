package asyncapi.util;

import java.util.List;
import java.util.function.Function;

public class PageResponseBuilder {
    public static <T, F> PageResponseDTO<T> of(
            List<F> content,
            long totalElements,
            int totalPages,
            Function<F, T> convert
    ) {
        List<T> result = content.stream()
                .map(convert)
                .toList();

        return new PageResponseDTO<>(result, totalElements, totalPages);
    }
}
