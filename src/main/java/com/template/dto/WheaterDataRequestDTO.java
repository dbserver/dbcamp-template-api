package com.template.dto;

import com.template.data.entity.CityEntity;
import com.template.data.entity.enums.DayTimeEnum;
import com.template.data.entity.enums.NightTimeEnum;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WheaterDataRequestDTO {

    private Long idWheaterData;

    private CityEntity city;

    private LocalDate date;

    DayTimeEnum dayTimeEnum;

    NightTimeEnum nightTimeEnum;

    private Integer maxTemperature;

    private Integer minTemperature;

    private Integer precipitation;

    private Integer humidity;

    private Integer windSpeed;
}
