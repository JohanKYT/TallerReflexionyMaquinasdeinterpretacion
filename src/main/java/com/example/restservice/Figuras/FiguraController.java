package com.example.restservice.Figuras;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/figuras")
public class FiguraController {

    private final FiguraService service;

    public FiguraController(FiguraService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public Figura crear(@RequestParam String nombre, @RequestBody Map<String, Object> parametros) throws Exception {
        return service.crearFigura(nombre, parametros);
    }
    // Crear figura rápida desde URL (GET)
    @GetMapping(value = "/crearRapido", produces = "application/json")
    public FiguraDTO crearRapido(@RequestParam String nombre, @RequestParam List<Double> valores) throws Exception {

        Map<String, Object> parametros = new HashMap<>();
        Constructor<?> constructor = Class.forName("com.example.restservice.Figuras." + nombre)
                .getConstructors()[0];
        Parameter[] params = constructor.getParameters();
        for (int i = 0; i < params.length; i++) {
            parametros.put(params[i].getName(), valores.get(i));
        }

        Figura f = service.crearFigura(nombre, parametros);
        return new FiguraDTO(f);
    }

    @PostMapping("/crearGroovy")
    public FiguraDTO crearGroovy(@RequestParam String codigo, @RequestBody(required = false) Map<String, Object> parametros) throws Exception {
        if (parametros == null) parametros = new HashMap<>();
        Figura figura = service.crearFiguraGroovy(codigo, parametros);
        return new FiguraDTO(figura);
    }


    @GetMapping("/listar")
    public List<FiguraDTO> listar() {
        return service.listarFiguras().stream()
                .map(FiguraDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/params")
    public List<String> obtenerParametros(@RequestParam String nombre) throws Exception {
        // Devuelve los nombres de los parámetros del constructor
        return service.obtenerParametrosFigura(nombre);
    }
}
