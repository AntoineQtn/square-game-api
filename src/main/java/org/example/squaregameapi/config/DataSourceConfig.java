package org.example.squaregameapi.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Configuration class for defining and managing DataSource beans.
 *
 * This class defines two different DataSource beans that are activated
 * based on the active Spring profile. It supports H2 and MySQL profiles.
 *
 * - The `h2DataSource` method provides an in-memory H2 database configuration, typically used for testing or development purposes.
 * - The `mysqlDataSource` method provides the configuration for connecting to a MySQL database, typically used in production environments.
 *
 * Spring profiles are used to activate specific bean configurations based on the environment the application is running in.
 */
    @Configuration
    public class DataSourceConfig {

    /**
     * Creates and configures a DataSource bean for an in-memory H2 database.
     * This bean is only active when the "h2" Spring profile is enabled.
     *
     * The method uses the H2 database driver, sets up a development database URL,
     * and provides default credentials for connections.
     *
     * @return an instance of DataSource configured for an in-memory H2 database
     */
    @Bean
        @Profile("h2")
        public DataSource h2DataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("org.h2.Driver")
                    .url("jdbc:h2:mem:devdb")
                    .username("sa")
                    .password("")
                    .build();
        }

    /**
     * Creates and configures a DataSource bean for a MySQL database.
     * This bean is only active when the "mysql" Spring profile is enabled.
     *
     * The method uses the MySQL database driver, specifies the connection URL,
     * and sets the database username and password for authentication.
     *
     * @return an instance of DataSource configured for a MySQL database
     */
    @Bean
        @Profile("mysql")
        public DataSource mysqlDataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/square_games_db")
                    .username("root")
                    .password("Antoine17031996")
                    .build();
        }
    }