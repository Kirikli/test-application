package com.example.test_application.common;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class PageResponseBuilder {
    public static <T, F> PageResponseDTO<T> of(Page<F> page, Function<F, T> convert) {
        List<T> result = page.getContent().stream()
                .map(convert)
                .toList();

        return new PageResponseDTO<T>(result, page.getTotalElements(), page.getTotalPages());
    }
}
