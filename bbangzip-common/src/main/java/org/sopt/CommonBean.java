package org.sopt;

import org.springframework.stereotype.Component;

@Component
public class CommonBean {
    public void dependencyTest() {
        System.out.println("CommonBean 성공적으로 로딩됐습니다.");
    }
}