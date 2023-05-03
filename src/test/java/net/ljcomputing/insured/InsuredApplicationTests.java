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
package net.ljcomputing.insured;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Random;
import net.ljcomputing.insured.entity.Insured;
import net.ljcomputing.insured.repository.InsuredRepository;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class InsuredApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(InsuredApplicationTests.class);

    @Autowired private InsuredRepository insuredRepository;

    private static Long count;

    public Insured expectedInsured() {
        Random random = new Random();
        Integer rnd = random.nextInt(500);
        final Insured insured = new Insured();
        String givenName = String.format("%s%s", "John", rnd.toString());
        String surname = String.format("%s%s", "Doe", rnd.toString());

        insured.setGivenName(givenName);
        insured.setSurname(surname);

        log.debug("expected: [{}]", insured);
        return insured;
    }

    @Test
    @Order(1)
    void contextLoads() {}

    @Test
    @Order(10)
    void deleteAll() {
        insuredRepository.deleteAll();
        final List<Insured> list = (List<Insured>) insuredRepository.findAll();
        assertEquals(0, list.size());
    }

    @Test
    @Order(20)
    void insert() {
        final Insured expected = expectedInsured();
        final Insured result = insuredRepository.save(expected);

        assertNotNull(result);
        assertNotNull(result.getId());

        expected.setId(result.getId());

        assertEquals(expected, result);
    }

    @Test
    @Order(30)
    void update() {
        final List<Insured> list = (List<Insured>) insuredRepository.findAll();
        final Integer lastIndex = list.size() - 1;
        final Insured actual = list.get(lastIndex);
        assertNotNull(actual.getId());

        final Long id = actual.getId();
        final Insured expected = insuredRepository.findById(id).get();

        expected.setMiddleName("Michael");

        final Insured result = insuredRepository.save(expected);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(expected, result);
    }

    @Test
    @Order(50)
    void selectCount() {
        count = insuredRepository.count();
        log.debug("cnt: [{}]", count);
        assertNotEquals(0, count);
    }

    @Test
    @Order(100)
    void selectAll() {
        List<Insured> results = (List<Insured>) insuredRepository.findAll();
        assertEquals(count, results.size());
    }

    @Test
    @Order(110)
    void findById() {
        final List<Insured> list = (List<Insured>) insuredRepository.findAll();
        final Integer lastIndex = list.size() - 1;
        final Long id = list.get(lastIndex).getId();
        final Insured expected = insuredRepository.findById(id).get();
        assertNotNull(expected.getId());
    }
}
