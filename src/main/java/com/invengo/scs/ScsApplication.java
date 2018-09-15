package com.invengo.scs;


import com.invengo.scs.configuration.DataSourceConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@SpringBootApplication(scanBasePackages = {"com.invengo.scs"})
@ComponentScan(basePackages = "com.invengo.scs")
@EnableAutoConfiguration
@MapperScan(basePackages = "com.invengo.scs.*",annotationClass = Repository.class)
public class ScsApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DataSourceConfiguration.class);
        SpringApplication.run(ScsApplication.class, args);
    }
}
