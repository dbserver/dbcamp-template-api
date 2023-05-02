package com.template.data.repository;

import com.template.data.entity.WheaterDataEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WheaterDataRepository extends JpaRepository <WheaterDataEntity, Long> {

    List<WheaterDataEntity> findAllByOrderByDateDesc();
    List<WheaterDataEntity> findAllByCityName(String cityName, Sort sort);

    List<WheaterDataEntity> findByDateAndCityName(LocalDate date, String cityName);

    List<WheaterDataEntity> findTop7ByCityNameOrderByDateAsc(String cityName, Sort sort);

    void deleteById (Long idWheaterData);

}
