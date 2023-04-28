CREATE TABLE wheaterData (
    idWheaterData BIGINT PRIMARY KEY,
    idCity BIGINT NOT NULL,
    date DATE,
    dayTimeEnum VARCHAR(20),
    nightTimeEnum VARCHAR(20),
    maxTemperature INTEGER,
    minTemperature INTEGER,
    precipitation INTEGER,
    humidity INTEGER,
    windSpeed INTEGER,
    FOREIGN KEY (idCity) REFERENCES City(idCity)
);
