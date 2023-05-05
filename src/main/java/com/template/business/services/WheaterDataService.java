package com.template.business.services;

import com.template.config.exceptions.NoContentException;
import com.template.data.entity.CityEntity;
import com.template.data.entity.WheaterDataEntity;
import com.template.data.repository.WheaterDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WheaterDataService {

    final WheaterDataRepository wheaterDataRepository;

    final CityService cityService;

    public WheaterDataService(WheaterDataRepository wheaterDataRepository, CityService cityService) {
        this.wheaterDataRepository = wheaterDataRepository;
        this.cityService = cityService;
    }

    public WheaterDataEntity save(WheaterDataEntity wheaterDataEntity) throws IOException {
        return wheaterDataRepository.save(wheaterDataEntity);
    }

    public List<WheaterDataEntity> findAll() throws IOException {
        return wheaterDataRepository.findAllByOrderByDateDesc();
    }

    public List<WheaterDataEntity> findAllByName(String cityName, Sort sort) throws IOException {
        return wheaterDataRepository.findAllByCityNameIgnoreCase(cityName, sort);
    }

    public List<WheaterDataEntity> findByDateBetween(String cityName) throws IOException {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);
        Sort sort = Sort.by("date").ascending();

        List<WheaterDataEntity> wheaterDataList = wheaterDataRepository.findByCityNameIgnoreCaseAndDateBetween(cityName, startDate, endDate, sort);

        if (wheaterDataList.isEmpty()) {
            throw new NoContentException("No Content");
        }

        return wheaterDataList;
    }

    public Page<WheaterDataEntity> findAllPageByName(String cityName) throws IOException {
        Integer page = 0;
        Integer size = 10;
        Pageable pageableByCity = PageRequest.of(page, size, Sort.by("date").descending());

        Page<WheaterDataEntity> pageByCityResult = wheaterDataRepository.findAllByCityNameIgnoreCase(cityName, pageableByCity);

        if (pageByCityResult.isEmpty()) {
            throw new NoContentException("No Content");
        }

        return pageByCityResult;
    }

    public Page<WheaterDataEntity> findAllPage() throws IOException {
        Integer page = 0;
        Integer size = 10;
        Pageable pageableAll = PageRequest.of(page, size, Sort.by("date").descending());

        Page<WheaterDataEntity> pageAllResult = wheaterDataRepository.findAll(pageableAll);

        if (pageAllResult.isEmpty()) {
            throw new NoContentException("No Content");
        }

        return pageAllResult;
    }

    public WheaterDataEntity update(Long idWheaterData, WheaterDataEntity wheaterDataEntity) throws IOException{
        Optional<WheaterDataEntity> wheaterDataEntityOptional = wheaterDataRepository.findById(idWheaterData);
        Optional<CityEntity> cityEntityOptional = cityService.findById(wheaterDataEntityOptional.get().getCity().getIdCity());

        cityEntityOptional.get().setName(wheaterDataEntity.getCity().getName());
        CityEntity cityEntity = cityEntityOptional.get();

        WheaterDataEntity wheaterDataEntityUpdate = wheaterDataEntityOptional.get();

        wheaterDataEntityUpdate.setCity(cityEntity);
        wheaterDataEntityUpdate.setDate(wheaterDataEntity.getDate());
        wheaterDataEntityUpdate.setDayTimeEnum(wheaterDataEntity.getDayTimeEnum());
        wheaterDataEntityUpdate.setNightTimeEnum(wheaterDataEntity.getNightTimeEnum());
        wheaterDataEntityUpdate.setMaxTemperature(wheaterDataEntity.getMaxTemperature());
        wheaterDataEntityUpdate.setMinTemperature(wheaterDataEntity.getMinTemperature());
        wheaterDataEntityUpdate.setPrecipitation(wheaterDataEntity.getPrecipitation());
        wheaterDataEntityUpdate.setHumidity(wheaterDataEntity.getHumidity());
        wheaterDataEntityUpdate.setWindSpeed(wheaterDataEntity.getWindSpeed());

        return wheaterDataRepository.save(wheaterDataEntityUpdate);
    }

    public void deleteById(Long idWheaterData) throws IOException {
        wheaterDataRepository.deleteById(idWheaterData);
    }
}
