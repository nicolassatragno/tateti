package com.gfive.tateti.componentes.arbol;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.gfive.tateti.metricas.Metrica;

/**
 * Nodo del �rbol de archivos que representa una carpeta.
 * @author nicolas
 *
 */
public class CarpetaNodo extends NodoArbol {

    protected CarpetaNodo(Path path) {
        super(path);
    }

    @Override
    public boolean esCarpeta() {
        return true;
    }

    @Override
    protected List<Metrica> calcularMetricas() {
        // TODO calcular m�tricas de los hijos.
        return new ArrayList<Metrica>();
    }

}
