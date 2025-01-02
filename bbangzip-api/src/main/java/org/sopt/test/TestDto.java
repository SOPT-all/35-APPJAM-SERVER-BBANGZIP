package org.sopt.test;

import lombok.Builder;

@Builder
public record TestDto(
    String content
) {
}
