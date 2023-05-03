/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.

James G Willmore - LJ Computing - (C) 2023
*/
package net.ljcomputing.insured.configuration;

import java.util.Objects;
import javax.sql.DataSource;
import net.ljcomputing.insured.entity.Insured;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Data sources configuration. */
@Configuration
@EnableJpaRepositories(
        basePackages = "net.ljcomputing.insured",
        entityManagerFactoryRef = "insuranceEntityManager",
        transactionManagerRef = "insuranceTransactionManager")
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean insuranceEntityManager(
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(insurance()).packages(Insured.class).build();
    }
    /**
     * Insurance data source.
     *
     * @return
     */
    @Bean(name = "insurance")
    @ConfigurationProperties(prefix = "spring.datasource.insurance")
    public DataSource insurance() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager insuranceTransactionManager(
            @Qualifier("insuranceEntityManager")
                    LocalContainerEntityManagerFactoryBean insuranceEntityManager) {
        return new JpaTransactionManager(
                Objects.requireNonNull(insuranceEntityManager.getObject()));
    }
}
