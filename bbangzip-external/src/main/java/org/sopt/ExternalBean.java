package org.sopt;

import org.springframework.stereotype.Component;

@Component
public class ExternalBean {
    public void dependencyTest() {
        System.out.println("ExternalBean 성공적으로 로딩됐습니다.");
    }

}
