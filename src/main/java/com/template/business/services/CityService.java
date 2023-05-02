package com.template.business.services;

import com.template.data.entity.CityEntity;
import com.template.data.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional
    public CityEntity save(CityEntity cityEntity) throws IOException {
        return cityRepository.save(cityEntity);
    }

    public List<CityEntity> findAll() throws IOException {
        return cityRepository.findAll();
    }

    public Optional<CityEntity> findById(Long idCity) throws IOException{
        return cityRepository.findById(idCity);
    }

}
