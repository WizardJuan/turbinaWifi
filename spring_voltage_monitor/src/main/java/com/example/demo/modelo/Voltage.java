package com.example.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Voltage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double voltage;

    @Column(nullable = false)
    private LocalDateTime timestamp; // Se agrega el campo timestamp

    public Voltage() {
        this.timestamp = LocalDateTime.now(); // Inicializa el timestamp al crear la instancia
    }

    public Voltage(double voltage) {
        this.voltage = voltage;
        this.timestamp = LocalDateTime.now(); // Asigna el timestamp al recibir el voltaje
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

