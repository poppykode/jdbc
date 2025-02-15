package zw.co.fiscit.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final MySQLDataSourceProperties mysqlProperties;
    private final PostgresDataSourceProperties postgresProperties;

    public DataSourceConfig(MySQLDataSourceProperties mysqlProperties, PostgresDataSourceProperties postgresProperties) {
        this.mysqlProperties = mysqlProperties;
        this.postgresProperties = postgresProperties;
    }

    @Bean(name = "cockroachDataSource")
    public DataSource cockroachDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(mysqlProperties.getDriverClassName())
                .url(mysqlProperties.getUrl())
                .username(mysqlProperties.getUsername())
                .password(mysqlProperties.getPassword())
                .build();
    }

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(postgresProperties.getDriverClassName())
                .url(postgresProperties.getUrl())
                .username(postgresProperties.getUsername())
                .password(postgresProperties.getPassword())
                .build();
    }
}
