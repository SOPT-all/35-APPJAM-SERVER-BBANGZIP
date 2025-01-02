package org.sopt;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BbangzipApplication {

    // 의존성 확인을 위한 코드 - 시작
    private final DomainBean domainBean;
    private final AuthBean authBean;
    private final CommonBean commonBean;
    private final ExternalBean externalBean;

    @Autowired
    public BbangzipApplication(DomainBean domainBean, AuthBean authBean, CommonBean commonBean, ExternalBean externalBean) {
        this.domainBean = domainBean;
        this.authBean = authBean;
        this.commonBean = commonBean;
        this.externalBean = externalBean;
    }

    @PostConstruct
    public void dependencyTest() {
        domainBean.dependencyTest();
        authBean.dependencyTest();
        commonBean.dependencyTest();
        externalBean.dependencyTest();
    }
    // 의존성 확인을 위한 코드 - 끝

    public static void main(String[] args) {
        SpringApplication.run(BbangzipApplication.class, args);
    }
}