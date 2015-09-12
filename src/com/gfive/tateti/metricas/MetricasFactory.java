package com.gfive.tateti.metricas;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Factory que crea todas las m�tricas que se pueden hacer sobre un archivo.
 * @author nicolas
 *
 */
public final class MetricasFactory {
    
    /**
     * Instancia singleton del repositorio.
     */
    public static MetricasFactory instancia;
    
    /**
     * @return la instancia del repositorio.
     */
    public static MetricasFactory get() {
        if (instancia == null)
            instancia = new MetricasFactory();
        return instancia;
    }
    
    /**
     * Construye un repositorio de m�tricas.
     */
    private MetricasFactory() { }

    /**
     * @return un stream con todas las m�tricas, listas para procesar archivos.
     */
    public Stream<Metrica> getMetricas() {
        return Arrays.stream(new Metrica[] {
            new CantidadLineas()
        });
    }
}
