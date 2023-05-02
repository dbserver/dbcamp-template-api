package com.template.data.repository;

import com.template.data.entity.WheaterDataEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WheaterDataRepository extends JpaRepository <WheaterDataEntity, Long> {

    List<WheaterDataEntity> findAllByOrderByDateDesc();
    List<WheaterDataEntity> findAllByCityNameIgnoreCase(String cityName, Sort sort);

    List<WheaterDataEntity> findByDateAndCityName(LocalDate date, String cityName);

    List<WheaterDataEntity> findTop7ByCityNameOrderByDateAsc(String cityName, Sort sort);

    void deleteById (Long idWheaterData);

}
