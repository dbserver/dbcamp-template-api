package com.template.business.services;

import com.template.data.entity.CityEntity;
import com.template.data.entity.WheaterDataEntity;
import com.template.data.repository.CityRepository;
import com.template.data.repository.WheaterDataRepository;
import com.template.dto.WheaterDataRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WheaterDataService {

    final WheaterDataRepository wheaterDataRepository;

    final CityRepository cityRepository;
    final CityService cityService;

    public WheaterDataService(WheaterDataRepository wheaterDataRepository, CityRepository cityRepository, CityService cityService) {
        this.wheaterDataRepository = wheaterDataRepository;
        this.cityRepository = cityRepository;
        this.cityService = cityService;
    }

    public WheaterDataEntity save(WheaterDataEntity wheaterDataEntity) throws IOException {
        return wheaterDataRepository.save(wheaterDataEntity);
    }

    public List<WheaterDataEntity> findAll() throws IOException {
        return wheaterDataRepository.findAllByOrderByDateDesc();
    }

    public List<WheaterDataEntity> findAllByName(String cityName, Sort sort) throws IOException {
        return wheaterDataRepository.findAllByCityName(cityName, sort);
    }

    public List<WheaterDataEntity> findDateCurrent(LocalDate date, String cityName) throws IOException {
        return wheaterDataRepository.findByDateAndCityName(date, cityName);
    }

    public List<WheaterDataEntity> findAllNextDays(String cityName, Sort sort) throws IOException {
        return wheaterDataRepository.findTop7ByCityNameOrderByDateAsc(cityName, sort);
    }

    public Page<WheaterDataEntity> findAllPage(Pageable pageable) throws IOException {
        return wheaterDataRepository.findAll(pageable);
    }

    public WheaterDataEntity update(Long idWheaterData, WheaterDataRequestDTO wheaterDataRequestDTO) throws IOException{
        Optional<WheaterDataEntity> wheaterDataEntityOptional = wheaterDataRepository.findById(idWheaterData);
        Optional<CityEntity> cityEntityOptional = cityService.findById(wheaterDataEntityOptional.get().getCity().getIdCity());

        //System.out.println(cityEntityOptional);
       // var cityEntity = cityEntityOptional.get();
       // System.out.println(cityEntity);
        var wheaterDataEntity = wheaterDataEntityOptional.get();
        System.out.println(wheaterDataEntity); //gramado

        var cityEntity = wheaterDataEntity.getCity();
        System.out.println(cityEntity); //gramado

        cityEntity.setName(wheaterDataRequestDTO.getCity().getName());
        System.out.println(cityEntity); //floripa

        wheaterDataEntity.setCity(cityEntity);
        System.out.println(wheaterDataEntity); //floripa

        cityRepository.save(cityEntity);

        wheaterDataEntity.setDate(wheaterDataRequestDTO.getDate());
        wheaterDataEntity.setDayTimeEnum(wheaterDataRequestDTO.getDayTimeEnum());
        wheaterDataEntity.setNightTimeEnum(wheaterDataRequestDTO.getNightTimeEnum());
        wheaterDataEntity.setMaxTemperature(wheaterDataRequestDTO.getMaxTemperature());
        wheaterDataEntity.setMinTemperature(wheaterDataRequestDTO.getMinTemperature());
        wheaterDataEntity.setPrecipitation(wheaterDataRequestDTO.getPrecipitation());
        wheaterDataEntity.setHumidity(wheaterDataRequestDTO.getHumidity());
        wheaterDataEntity.setWindSpeed(wheaterDataRequestDTO.getWindSpeed());

        return wheaterDataRepository.save(wheaterDataEntity);
    }
    public void deleteById(Long idWheaterData) throws IOException {
        wheaterDataRepository.deleteById(idWheaterData);
    }




}
