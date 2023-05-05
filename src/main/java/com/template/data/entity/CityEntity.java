package com.template.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CITY")
@Entity
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCity;

    @NotNull
    private String name;
}
