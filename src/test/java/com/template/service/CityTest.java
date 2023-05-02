package com.template.service;

import com.template.business.services.CityService;
import com.template.data.entity.CityEntity;
import com.template.data.entity.WheaterDataEntity;
import com.template.data.repository.CityRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityTest {

    @Mock
    CityEntity cityEntityMock;

    @Mock
    CityRepository cityRepositoryMock;

    @InjectMocks
    CityService cityServiceMock;

    public CityEntity cityEntityList1Mock() {
        return new CityEntity(202L, "Porto Alegre");
    }

    public CityEntity cityEntityList2Mock() {
        return new CityEntity(203L, "Gramado");
    }

    @Test
    void saveSuccessfully() {
        // Arrange
        when(cityRepositoryMock.save(cityEntityMock)).thenReturn(cityEntityMock);

        // Assert
        Assertions.assertEquals(cityEntityMock, cityRepositoryMock.save(cityEntityMock));
        Mockito.verify(cityRepositoryMock, times(1)).save(cityEntityMock);
    }

    @Test
    void saveFail() {
        // Arrange
        when(cityRepositoryMock.save(cityEntityMock)).thenThrow(new RuntimeException("Erro ao salvar os dados"));

        // Assert
        assertThrows(RuntimeException.class, () -> {
            cityServiceMock.save(cityEntityMock);
        });
    }

    @SneakyThrows
    @Test
    void findAllSuccessfully() {
        // Arrange
        CityEntity cityEntityList1 = cityEntityList1Mock();
        CityEntity cityEntityList2 = cityEntityList2Mock();

        when(cityRepositoryMock.findAll()).thenReturn(List.of(cityEntityList1, cityEntityList2));

        // Act
        List<CityEntity> cityList = cityServiceMock.findAll();

        // Assert
        Assertions.assertEquals(2, cityList.size());
        Assertions.assertEquals(cityEntityList1, cityList.get(0));
        Assertions.assertEquals(cityEntityList2, cityList.get(1));
    }

    @Test
    void findAllFail() {
        // Arrange
        when(cityRepositoryMock.findAll()).thenThrow(new RuntimeException("Erro ao retornar os dados"));

        // Assert
        assertThrows(RuntimeException.class, () -> {
            cityServiceMock.findAll();
        });
    }

    @SneakyThrows
    @Test
    void findByIdSuccessfully() {
        // Arrange
        Long idCity = 202L;

        CityEntity cityEntityList1 = cityEntityList1Mock();

        when(cityRepositoryMock.findById(idCity)).thenReturn(Optional.of(cityEntityList1));

        // Act
        Optional<CityEntity> resultList = cityServiceMock.findById(idCity);

        // Assert
        assertEquals(idCity, resultList.get().getIdCity());
    }

    @Test
    void  findByIdFail() {
        // Arrange
        Long idCity = 202L;

        when(cityRepositoryMock.findById(eq(idCity)))
                .thenThrow(new DataAccessException("Erro ao executar consulta no banco de dados") {});

        // Assert
        assertThrows(DataAccessException.class, () -> {
            cityServiceMock.findById(idCity);
        });
    }
}
