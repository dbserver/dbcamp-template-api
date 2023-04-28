package com.template.business.services;

import com.template.data.entity.WheaterDataEntity;
import com.template.data.repository.WheaterDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class WheaterDataService {

    final WheaterDataRepository wheaterDataRepository;

    public WheaterDataService(WheaterDataRepository wheaterDataRepository) {
        this.wheaterDataRepository = wheaterDataRepository;
    }

    @Transactional
    public WheaterDataEntity save(WheaterDataEntity wheaterDataEntity) throws IOException {
        return wheaterDataRepository.save(wheaterDataEntity);
    }

    public List<WheaterDataEntity> findAll() throws IOException {
        return wheaterDataRepository.findAllByOrderByDateDesc();
    }

    public Page<WheaterDataEntity> findAllPage(Pageable pageable) throws IOException {
        return wheaterDataRepository.findAll(pageable);
    }


}
