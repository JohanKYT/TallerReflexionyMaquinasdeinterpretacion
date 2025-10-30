package com.example.restservice.Figuras;

import groovy.lang.GroovyClassLoader;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FiguraService {
    private final List<Figura> figuras = new ArrayList<>();

    public Figura crearFigura(String nombre, Map<String, Object> parametros) throws Exception {
        Figura figura = FiguraFactory.crearFigura(nombre, parametros);
        figuras.add(figura);
        return figura;
    }

    public Figura crearFiguraGroovy(String codigo, Map<String, Object> parametros) throws Exception {
        Figura figura = FiguraDynamicLoader.cargarDesdeGroovy(codigo, parametros);
        figuras.add(figura);
        return figura;
    }

    public List<Figura> listarFiguras() {
        return figuras;
    }
    public List<String> obtenerParametrosFigura(String nombre) throws Exception {
        String paquete = "com.example.restservice.Figuras";
        Class<?> clase = Class.forName(paquete + "." + nombre);
        Constructor<?> c = clase.getConstructors()[0]; // tomamos primer constructor
        return Arrays.stream(c.getParameters())
                .map(Parameter::getName)
                .collect(Collectors.toList());
    }

}
