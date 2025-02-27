package com.example.demo.repository;

import com.example.demo.modelo.Voltage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoltageRepository extends JpaRepository<Voltage, Long> {

    List<Voltage> findTop5ByVoltageGreaterThanOrderByVoltageDesc(double voltage);

    @Query("SELECT v FROM Voltage v ORDER BY v.timestamp DESC")
    List<Voltage> findLatestVoltage(Pageable pageable);
}

