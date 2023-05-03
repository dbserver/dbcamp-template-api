package com.template.data.repository;

import com.template.data.entity.WheaterDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

public interface WheaterDataRepository extends JpaRepository <WheaterDataEntity, Long> {

    List<WheaterDataEntity> findAllByOrderByDateDesc();
    List<WheaterDataEntity> findAllByCityNameIgnoreCase(String cityName, Sort sort);

    Page<WheaterDataEntity> findAllByCityName(String cityName, Pageable pageable);

    List<WheaterDataEntity> findByCityNameAndDateBetween(String cityName, LocalDate startDate, LocalDate endDate);

    void deleteById (Long idWheaterData);
}
