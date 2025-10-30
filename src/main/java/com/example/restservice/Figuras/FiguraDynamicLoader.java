package com.example.restservice.Figuras;

import groovy.lang.GroovyClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

public class FiguraDynamicLoader {

    public static Figura cargarDesdeGroovy(String codigo, Map<String, Object> parametros) throws Exception {
        GroovyClassLoader loader = new GroovyClassLoader(FiguraDynamicLoader.class.getClassLoader());
        Class<?> groovyClass = loader.parseClass(codigo);

        Constructor<?> constructor = groovyClass.getConstructors()[0];

        // --- LÓGICA MODIFICADA ---
        // Simplemente toma los valores del mapa.
        // Esto asume que el JSON que envías SÓLO contiene los parámetros
        // en el orden correcto.
        Object[] args = parametros.values().stream()
                .map(val -> Double.parseDouble(val.toString()))
                .toArray();

        if (args.length != constructor.getParameterCount()) {
            throw new IllegalArgumentException("Número incorrecto de parámetros. Se esperaban "
                    + constructor.getParameterCount() + " pero se recibieron " + args.length);
        }
        // --- FIN DE LA LÓGICA MODIFICADA ---

        Object obj = constructor.newInstance(args);
        return (Figura) obj;
    }
}
