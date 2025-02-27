package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.modelo.Voltage;
import com.example.demo.modelo.VoltageRequest;
import com.example.demo.repository.VoltageRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/voltage")
public class VoltageController {

    @Autowired
    private VoltageRepository repository;

    @PostMapping("/send")
    public ResponseEntity<String> receiveVoltage(@RequestBody VoltageRequest request) {
        if (authenticate(request.getUsername(), request.getPassword())) {
            Voltage voltage = new Voltage(request.getVoltage());
            voltage.setTimestamp(LocalDateTime.now());
            repository.save(voltage);
            return ResponseEntity.ok("Voltage saved successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        List<Voltage> latestVoltage = repository.findLatestVoltage(PageRequest.of(0, 1));
        
        if (latestVoltage.isEmpty()) {
            return ResponseEntity.ok("No voltage data available");
        }

        Voltage lastVoltage = latestVoltage.get(0);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(lastVoltage.getTimestamp(), now);

        if (duration.getSeconds() > 10) { // Si no hay actualización en 10 segundos, asumimos desconexión
            return ResponseEntity.ok("Sensor desconectado");
        }

        return ResponseEntity.ok("Última lectura: " + lastVoltage.getVoltage() + "V");
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Map<String, Object>>> getTop5Voltages() {
        List<Voltage> topVoltages = repository.findTop5ByVoltageGreaterThanOrderByVoltageDesc(0.0);
        
        List<Map<String, Object>> response = topVoltages.stream().map(voltage -> {
            Map<String, Object> data = new HashMap<>();
            data.put("voltage", voltage.getVoltage());
            data.put("timestamp", voltage.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return data;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    private boolean authenticate(String username, String password) {
        return "esp8266".equals(username) && "clave123".equals(password);
    }


}


