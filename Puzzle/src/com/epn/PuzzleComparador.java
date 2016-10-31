package com.epn;
import java.util.Comparator;
//Es utilizado para reordenar las configuraciones del tablero que fueron examinadas según
//A* donde g es la profundidad del nodo y h es una heuristica aplicada para medir la calidad del estado

class PuzzleComparador implements Comparator<TableroPuzzle> {
    @Override
    public int compare(TableroPuzzle p1, TableroPuzzle p2){
        //si el primer tablero tiene un valor menor de A* 
        // p1 es menor que p2, el valor de retorno es -1
        if (p1.getValorAestrella() < p2.getValorAestrella())
        {
            return -1;
        }
        // si la primera configuración tiene un valor mejor de A*, 
        // el valor de retorno es 1
        if (p1.getValorAestrella() > p2.getValorAestrella())
        {
            return 1;
        }
        // si los valores de comparación son iguales, el valor de retorno es 0
        return 0;
    }
}
