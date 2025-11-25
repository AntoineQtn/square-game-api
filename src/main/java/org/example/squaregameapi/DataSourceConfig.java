package org.example.squaregameapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

    @Configuration
    public class DataSourceConfig {

        /**
         * DataSource H2 (profil "h2")
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
         * DataSource MySQL (profil "mysql")
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