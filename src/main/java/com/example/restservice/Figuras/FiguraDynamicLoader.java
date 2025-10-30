package com.example.restservice.Figuras;

import groovy.lang.GroovyClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

public class FiguraDynamicLoader {

    public static Figura cargarDesdeGroovy(String codigo, Map<String, Object> parametros) throws Exception {
        GroovyClassLoader loader = new GroovyClassLoader();
        Class<?> groovyClass = loader.parseClass(codigo);

        Constructor<?> constructor = groovyClass.getConstructors()[0];
        Parameter[] params = constructor.getParameters();

        LinkedHashMap<String, Object> paramOrdenados = new LinkedHashMap<>();
        for (Parameter p : params) {
            paramOrdenados.put(p.getName(), parametros.get(p.getName()));
        }

        Object obj = constructor.newInstance(paramOrdenados.values().toArray());
        return (Figura) obj;
    }
}
