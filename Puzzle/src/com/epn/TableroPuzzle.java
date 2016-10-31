package com.epn;
import java.util.Random;
//El tablero representa una organización de 3x3
class TableroPuzzle {
	//las posiciones de todas las piezas en el tablero
    private StringBuffer valorTablero;    
    //padre de el tablero puzzle
    private TableroPuzzle padre;    
    // hueristica usada
    private int tipoHeuristica;
    
    // profundidad del nodo
    private int profundidad;
    
    // A* val = g(n) + h(n)
    private int valorAestrella;
    
    //indice vacio
    private int indiceVacio;

    public TableroPuzzle(String tableroVal, int h) {
        //llamamos al constructor con la profundidad 0 y padre igual null
    	this(tableroVal, 0, null, h);
    }
    
    private TableroPuzzle(String tableroVal, int prof, TableroPuzzle p, int h){
    	//inicializamos el valor del tablero y llenamos conlos valores pasados
        this.valorTablero = new StringBuffer(tableroVal);
        
        //determinamos donde esta un espacio vacio
        for (int i = 0; i < 9; i++) {
        	//verificamos cada casillero para el valor de cero
            if (this.valorTablero.charAt(i) == '0') {
            	//cuando el valor de cero es encontrado marcamos el indice y paramos el ciclo
                this.indiceVacio = i;
                break;
            }
        }
        
        this.tipoHeuristica = h; // valor de la heuristica pasada como argumento
        this.profundidad = prof;         // valor de la profundidad pasada como argumento
        this.padre = p;        // valor del padre pasado  como argumento
        this.calcAestrella();        // Inicializar el valor de a*
    }
    
    /*
     una funcion que recalcule el valor de a* del tablero
    */
    private void calcAestrella(){
    	// heuristica tipo 1 , usa la heuristica de piezas mal colocadas usa la  funcion y calcula a*
        if(this.tipoHeuristica == 1){
            // A* = g(n) + h(n)
            this.valorAestrella = this.profundidad + this.heuristicaMalCol();
        } 
        //  caso contrario usa la heuristica manhatan y calcula a*
        else {
            // A* = g(n) + h(n)
            this.valorAestrella = this.profundidad + this.heuristicaManhattan();
        }
    }

    private int heuristicaMalCol() {
    	// valor entero para guardar el valor de lugar mal colocado
        int numFueraSitio = 0;
     // Bucle a través de todas las piezas y descubrir si está fuera de lugar es decir si no corresponde ahi

        for (int i = 0; i < 9; i++) {
        	/*
        	 compara cada pieza con el valor entero entre [0-8] si tiene un equivalente
        	 con los caracteres ASCII de [48-56]  
        	 */
            if (this.valorTablero.charAt(i) != (char) (48 + i)) {
            	//incrementa la variable numFueraSitio cada vez que encuentra una coincidencia
                numFueraSitio++;
            }
        }
        // regresa el numero de de piezas que estan fuera de lugar
        return numFueraSitio;
    }
    
    /*
     (2) La funcion Heuristica que regresa la suma de las distancias de las posisiones meta
    Output: el entero representa el numero de la distancia manhattan de el nodo meta
     */
    private int heuristicaManhattan() {
    	//entero que gaurda la suma de la distancia manhatan
        int distanciaManhattan = 0;
        //itera atravez de cada cuadrado
        for (int indiceactual = 0; indiceactual < 9; indiceactual++) {
        	// calcula la fila del  indice actual  
            int indiceFila = indiceactual / 3;
         // Calcular la columna del índice actual
            int indiceColumna = indiceactual % 3;
            // obtiene el valor actual de la pieza como un entero
            int valorActual = (int) this.valorTablero.charAt(indiceactual) - 48;
            // calcula la fila. El valor DEBERIA estar en
            int valorFila = valorActual / 3;
            // calcula la columna. El valor DEBERIA estar en
            int valorColumna = valorActual % 3;
            // manhattan distancia = abs(actual fila - fila deseada) + abs(actual col - valor col)
         // Añadir nueva distancia Manhattan de la pieza para  la variable de la suma manhattanDistancia

            distanciaManhattan += Math.abs(indiceFila - valorFila) + Math.abs(indiceColumna - valorColumna);
        }
        return distanciaManhattan;
    }

    /*
comprueba si el rompecabezas que en el estado objetivo    
     */
    public boolean esMeta() {
        return this.valorTablero.toString().equals("012345678");
    }
    
    /*
     Calcula inversiones de el puzzle
     si i<j y puzzle[i] > puzzle[j],  existe una inversion 
     solo un problema con la misma inversion son validos   
    Input: void
    Output:  boolean que represeenta si el puzzle tiene solucion
     */
    public boolean tieneSolucion() {
    	// entero para guardar el  numero de piezas invertidas 
        int numInverted = 0;
        //iteraciones de cada ficha i
        for (int i = 0; i < 8; i++) {
        	//'0' es consideradon como vacio, no es en realidad cero y no va ser usado en inversiones
            if (this.valorTablero.charAt(i) != '0') {
            	//iterar atravez de cada pieza en frente de pieza 'i'
                for (int j = i + 1; j < 9; j++) {
                	// si el valor en 'i'aba posicion es mayor  que el valor en la 'j'aba posicion
                	//'0' es consideradon como vacio, no es en realidad cero y no va ser usado en inversiones

                    if (this.valorTablero.charAt(j) != '0' && (int) this.valorTablero.charAt(i) > (int) this.valorTablero.charAt(j)) {
                    	//incrementa el numero de piezas invertidas
                        numInverted++;
                    }
                }
            }
        }

        // si las piezas invertidas son iguales elproblema tiene  solucion
        if ((numInverted % 2) == 0) {
            return true;
        }
        // si las piezas invertidas no son iguales elproblema no tiene  solucion

        else {
            return false;
        }
    }

    /*
    configuracion representado como una cadena de 8 caracteres
    y realiza n cantidad de movimientos legales para el 8puzzle  
     */
    public void mezclarTablero(int n) {
    	//crea un nuevo objeto random
        Random rand = new Random();
        //entero para guardar nuestro nuevo movimiento
        int nuevoMov;
        // mueve n cantidad de veces
        for (int i = 0; i < n; i++) {
        	//genera un nuevo movimiento randomico
            nuevoMov = rand.nextInt(4);
            /*
                arriba          0
    	    izq    	   der    1   2
    	        abajo           3
             */
            switch (nuevoMov) {
                case 0:
                	//si  vacio no esta  en la  fila superior  , mover  arriba vacio
                    if (this.indiceVacio > 2) {
                    	// para mover arriba debemos intercambiar la pieza vacia con la pieza 3 
                        this.valorTablero.setCharAt(this.indiceVacio, this.valorTablero.charAt(this.indiceVacio - 3));
                        this.indiceVacio -= 3;
                        this.valorTablero.setCharAt(this.indiceVacio, '0');
                    }
                    break;
                case 1:
                	//si  vacio no esta  en la  columna izquierda  , mover  izquierda vacio
                   if (this.indiceVacio % 3 != 0) {
                    	// para mover a la izquierda debemos intercambiar la pieza vacia con la pieza 1 

                        this.valorTablero.setCharAt(this.indiceVacio, this.valorTablero.charAt(this.indiceVacio - 1));
                        this.indiceVacio--;
                        this.valorTablero.setCharAt(this.indiceVacio, '0');
                    }
                    break;
                case 2:
                	//si  vacio no esta  en la  columna derecha  , mover  derecha vacio
                    if (this.indiceVacio % 3 != 2) {
                    	// para mover a la derecha debemos intercambiar la pieza vacia con la pieza 2 
                        this.valorTablero.setCharAt(this.indiceVacio, this.valorTablero.charAt(indiceVacio + 1));
                        this.indiceVacio++;
                        this.valorTablero.setCharAt(this.indiceVacio, '0');
                    }
                    break;
                case 3:
                	//si vacio no esta  en la  fila inferior  , mover  abajo vacio
                    if (this.indiceVacio < 6) {
                    	// para mover abajo debemos intercambiar la pieza vacia con la pieza 3 
                        this.valorTablero.setCharAt(this.indiceVacio, this.valorTablero.charAt(this.indiceVacio + 3));
                        this.indiceVacio += 3;
                        this.valorTablero.setCharAt(this.indiceVacio, '0');
                    }
                    break;
            }
        }
    }
    
    /*
     regresa valor A*
    */
    public int getValorAestrella(){
        return this.valorAestrella;
    }
    
    /*
    regresa nodo padre
    */
    public TableroPuzzle getPadre(){
        return this.padre;
    }
    
    /*
    regresa el valor del tablero como string
    */
    public String getTableroValor(){
        return this.valorTablero.toString();
    }
    
    //GENERACIÓN DE NUEVOS ESTADOS (NODOS) DESPUES DE CADA MOVIMIENTO
    /*
    produce un tablero puzzle si el espacio en blanco fuera a moverse hacia arriba
    Entrada : void
    Salida : devuelve tablero puzzle si el movimiento es legal
            devuelve un valor nulo si el movimiento no es legal
    */
    public TableroPuzzle moverArriba(){
    	//Stringbuffer para almacenar copiar y manipular los valores del tablero actuales
        StringBuffer ValorNuevo = new StringBuffer(this.valorTablero.toString());
        // si vacio no esta el la fla superior;produce un movimiento hacia arriba
        if (this.indiceVacio > 2) {
        	// para mover arriba debemos intercambiar la pieza vacia con la pieza 0 
            ValorNuevo.setCharAt(this.indiceVacio, this.valorTablero.charAt(this.indiceVacio - 3));
            ValorNuevo.setCharAt(this.indiceVacio - 3, '0');
            //regresa un nuevo tablero puzzle como un hijo producido apartir de un movimiento ascendente
            return new TableroPuzzle(ValorNuevo.toString(), this.profundidad + 1, this, this.tipoHeuristica);
        } else {
            return null;
        }
    }
    
    /*
    produce un tablero puzzle si el espacio en blanco fuera a moverse hacia abajo
    
    Entrada : void
    Salida : devuelve tablero puzzle si el movimiento es legal
            devuelve un valor nulo si el movimiento no es legal
    */
    public TableroPuzzle moverAbajo(){
    	//Stringbuffer para almacenar copiar y manipular los valores del tablero actuales
        StringBuffer valorNuevo = new StringBuffer(this.valorTablero.toString());
        // si vacio no esta el la fla inferior;produce un movimiento hacia abajo
        if (this.indiceVacio < 6) {
        	// para mover arriba debemos intercambiar la pieza vacia con la pieza 3 
            valorNuevo.setCharAt(this.indiceVacio, this.valorTablero.charAt(this.indiceVacio + 3));
            valorNuevo.setCharAt(this.indiceVacio + 3, '0');
            //regresa un nuevo tablero puzzle como un hijo producido apartir de un movimiento descendente
            return new TableroPuzzle(valorNuevo.toString(), this.profundidad + 1, this, this.tipoHeuristica);
        } else {
            return null;
        }
    }
    
    /*
    produce un tablero puzzle si el espacio en blanco fuera a moverse hacia la izquierda
    
    Entrada : void
    Salida : devuelve tablero puzzle si el movimiento es legal
            devuelve un valor nulo si el movimiento no es legal
    */
    public TableroPuzzle moverIzq(){
    	//Stringbuffer para almacenar copiar y manipular los valores del tablero actuales
        StringBuffer valorNuevo = new StringBuffer(this.valorTablero.toString());
        // si vacio no esta el la columna izquierda ;produce un movimiento hacia izquierda

        if (this.indiceVacio % 3 != 0) {
        	// para mover a la izquierda debemos intercambiar la pieza vacia con la pieza 1 

            valorNuevo.setCharAt(this.indiceVacio, this.valorTablero.charAt(this.indiceVacio - 1));
            valorNuevo.setCharAt(this.indiceVacio - 1, '0');
            //regresa un nuevo tablero puzzle como un hijo producido apartir de un movimiento lateralizquierdo

            return new TableroPuzzle(valorNuevo.toString(), this.profundidad + 1, this, this.tipoHeuristica);
        } else {
            return null;
        }
    }
    
    /*
    produce un tablero puzzle si el espacio en blanco fuera a moverse hacia la derecha
    
    Entrada : void
    Salida : devuelve tablero puzzle si el movimiento es legal
            devuelve un valor nulo si el movimiento no es legal
    */
    public TableroPuzzle moverDer(){
    	//Stringbuffer para almacenar copiar y manipular los valores del tablero actuales
        StringBuffer valorNuevo = new StringBuffer(this.valorTablero.toString());
        // si vacio no esta el la columna derecha ;produce un movimiento hacia la derecha

        if (this.indiceVacio % 3 != 2) {
        	// para mover a la derecha debemos intercambiar la pieza vacia con la pieza 2 

            valorNuevo.setCharAt(this.indiceVacio, this.valorTablero.charAt(this.indiceVacio + 1));
            valorNuevo.setCharAt(this.indiceVacio + 1, '0');
            //regresa un nuevo tablero puzzle como un hijo producido apartir de un movimiento lateralizquierdo
            return new TableroPuzzle(valorNuevo.toString(), this.profundidad + 1, this, this.tipoHeuristica);
        } else {
            return null;
        }
    }
    /*
     Imprime los valores del tablero usados para probar  el proyecto 8puzzle    
     */
    public void imprimirPuzzle() {
        System.out.println(this.valorTablero.charAt(0) + " " + this.valorTablero.charAt(1) + " " + this.valorTablero.charAt(2));
        System.out.println(this.valorTablero.charAt(3) + " " + this.valorTablero.charAt(4) + " " + this.valorTablero.charAt(5));
        System.out.println(this.valorTablero.charAt(6) + " " + this.valorTablero.charAt(7) + " " + this.valorTablero.charAt(8));
    }
}
