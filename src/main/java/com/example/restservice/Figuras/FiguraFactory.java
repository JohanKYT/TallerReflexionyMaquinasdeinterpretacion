package com.example.restservice.Figuras;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Map;

public class FiguraFactory {

    public static Figura crearFigura(String nombre, Map<String, Object> parametros) throws Exception {
        String paquete = "com.example.restservice.Figuras";
        Class<?> clase = Class.forName(paquete + "." + nombre);
        Constructor<?>[] constructores = clase.getConstructors();

        for (Constructor<?> c : constructores) {
            Parameter[] params = c.getParameters();
            if (params.length != parametros.size()) continue;

            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                String paramName = params[i].getName(); // nombre real del parámetro
                args[i] = Double.parseDouble(parametros.get(paramName).toString());
            }

            return (Figura) c.newInstance(args);
        }

        throw new IllegalArgumentException("Figura no encontrada o parámetros incorrectos: " + nombre);
    }
}

