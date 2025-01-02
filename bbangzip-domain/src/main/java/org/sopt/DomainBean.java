package org.sopt;

import org.springframework.stereotype.Component;

@Component
public class DomainBean {
    public void dependencyTest() {
        System.out.println("DomainBean 성공적으로 로딩됐습니다.");
    }
}