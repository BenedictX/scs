package com.invengo.scs.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/08/30
 * Time: 17:50
 */
@Component
@Configuration
public class DataSourceConfiguration {
    @Bean(name="dataSource")
    @Qualifier(value="dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.c3p0")
    public DataSource createDataSource(){
        return DataSourceBuilder.create().type(ComboPooledDataSource.class).build();

    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(createDataSource());
        return sqlSessionFactory;
    }
}
