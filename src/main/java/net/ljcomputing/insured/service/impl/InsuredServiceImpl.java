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
package net.ljcomputing.insured.service.impl;

import java.util.List;
import java.util.Optional;
import net.ljcomputing.insured.entity.Insured;
import net.ljcomputing.insured.repository.InsuredRepository;
import net.ljcomputing.insured.service.InsuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InsuredServiceImpl implements InsuredService {
    @Autowired private InsuredRepository insuredRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Insured> findAll() {
        return (List<Insured>) insuredRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Insured findById(Long id) {
        Insured result = new Insured();
        Optional<Insured> optional = insuredRepository.findById(id);

        if (optional.isPresent()) {
            result = optional.get();
        }

        return result;
    }
}
