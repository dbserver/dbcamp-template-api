package com.template.data.entity;

import com.template.data.entity.enums.DayTimeEnum;
import com.template.data.entity.enums.NightTimeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Table(name = "WHEATER_DATA")
@Entity
public class WheaterData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWheaterData;

    @ManyToOne
    @JoinColumn(name = "idCity", nullable = false)
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
