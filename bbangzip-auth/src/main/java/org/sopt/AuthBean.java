package org.sopt;

import org.springframework.stereotype.Component;

@Component
public class AuthBean {
    public void dependencyTest() {
        System.out.println("AuthBean 성공적으로 로딩됐습니다.");
    }
}