package zw.co.fiscit.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlyWayConfig {
    @Autowired
    private CockroachFlywayProperties cockroachFlywayProperties;

    @Autowired
    private MysqlFlywayProperties mysqlFlywayProperties;

    @Bean(initMethod = "migrate")
    public Flyway cockroachFlyway(@Qualifier("cockroachDataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(cockroachFlywayProperties.getLocations())
                .baselineOnMigrate(cockroachFlywayProperties.isBaselineOnMigrate())
                .placeholderReplacement(cockroachFlywayProperties.isPlaceholderReplacement())
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway mysqlFlyway(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(mysqlFlywayProperties.getLocations())
                .baselineOnMigrate(mysqlFlywayProperties.isBaselineOnMigrate())
                .placeholderReplacement(mysqlFlywayProperties.isPlaceholderReplacement())
                .load();
    }


}
