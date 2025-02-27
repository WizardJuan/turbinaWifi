package com.example.demo.controlador;



import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/esp")
public class EspController {
    private boolean ledState = false;

    @PostMapping("/led")
    public String toggleLED() {
        ledState = !ledState;
        return ledState ? "LED Encendido" : "LED Apagado";
    }
}
