package com.template.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "BIRD")
@Entity
public class City {

    private Long idCity;

    private String name;
}
