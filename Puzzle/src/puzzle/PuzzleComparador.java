package puzzle;



import java.util.Comparator;

/*



clase comparador es utilizado para nuestro PriorityQueue para ordenar nuestros tablero puzzle
el tablero puzzles está ordenado por su valor de algoritmo 
este valor se calcula coste = g ( n) + h ( n)
donde g ( n ) es la profundidad del nodo ,
y h (n ) es una de las dos funciones  conocidos por el propio nodo
*/
class PuzzleComparador implements Comparator<TableroPuzzle> {
    @Override
    public int compare(TableroPuzzle p1, TableroPuzzle p2){
    	//si el primer valor del tablero tiene un valor menor a*
    	//p1 es menor que p2 retorna -1
        if (p1.getValor() < p2.getValor())
        {
            return -1;
        }
    	//si el primer valor del tablero tiene un valor mayor a*
    	//p1 es mayor que p2 retorna -1
        if (p1.getValor() > p2.getValor())
        {
            return 1;
        }
        // tambien el valor de A* son iguales
        // p1 es igual p2, retorna 0 
        return 0;
    }
}
