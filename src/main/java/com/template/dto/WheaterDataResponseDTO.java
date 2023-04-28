package com.template.dto;

import com.template.data.entity.CityEntity;
import com.template.data.entity.enums.DayTimeEnum;
import com.template.data.entity.enums.NightTimeEnum;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WheaterDataResponseDTO {

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
