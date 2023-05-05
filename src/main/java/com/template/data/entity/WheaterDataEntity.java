package com.template.data.entity;

import com.template.data.entity.enums.DayTimeEnum;
import com.template.data.entity.enums.NightTimeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WHEATER_DATA")
@Entity
public class WheaterDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idWheaterData;

    @ManyToOne
    @JoinColumn(name = "idCity", nullable = false)
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
