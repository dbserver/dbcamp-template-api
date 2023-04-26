package com.template.data.entity;

import com.template.data.entity.enums.DayTimeEnum;
import com.template.data.entity.enums.NightTimeEnum;

import java.time.LocalDate;

public class WheaterData {

    private Long idWheaterData;

    private City city;

    private LocalDate date;

    DayTimeEnum dayTimeEnum;

    NightTimeEnum nightTimeEnum;

    private Integer maxTemperature;

    private Integer minTemperature;

    private Integer precipitation;

    private Integer humidity;

    private Integer windSpeed;
}
