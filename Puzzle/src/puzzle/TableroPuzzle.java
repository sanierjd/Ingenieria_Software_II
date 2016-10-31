package puzzle;



import java.util.Random;


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

    /* public constructor
     
    Input:  String tableroVal - Representa el valor del  ta blero, por ejemplo el tablero...
                              0 1 2
                              3 4 5
                              6 7 8
                             puede ser representado como "012345678"
           
                */ 
    public TableroPuzzle(String tableroVal, int h) {
        //llamamos al constructor con la profundidad 0 y padre igual null
    	this(tableroVal, 0, null, h);
    }
    
    
    /* private constructor
    Input:  String tableroVal - valor del tablero
            int prof - la profundidad desde el nodo raiz
            PuzzleBoard p - el nodo padre del nodo que se está creando
          
    Output: un nuevo tablero con la configuracion del valor del tablero configugurado  con profundidad prof y padre igual a p
     
    */
    private TableroPuzzle(String tableroVal, int prof, TableroPuzzle p, int h){
    	//inicializamos el valor del tablero y llenamos conlos valores pasados
        this.valorTablero = new StringBuffer(tableroVal);
        
        //determinamos donde esta un espacio vacio
        for (int i = 0; i < 9; i++) {
        	//verificamos cada casillero para el valor de cero
            if (this.valorTablero.charAt(i) == '0') {
            	//cuando el valor de cero es encontrado marcamos el index y paramos el ciclo
                this.indiceVacio = i;
                break;
            }
        }
        
        this.tipoHeuristica = h; // valor de la heuristica pasada como argumento
        this.profundidad = prof;         // valor de la profundidad pasada como argumento
        this.padre = p;        // valor del padre pasado  como argumento
        this.funcionCoste();        // Inicializar el valor de a*
    }
    
    /*
     una funcion que recalcule el valor de a* del tablero
   
    */
    private void funcionCoste(){
    	
            // coste = g(n) + h(n)
            this.valorAestrella = this.profundidad + this.Manhattan();
        
    }

   
    

    private int Manhattan() {
    	//entero que gaurda la suma de la distancia manhatan
        int distanciaManhattan = 0;
        //itera atravez de cada cuadrado
        for (int currentIndex = 0; currentIndex < 9; currentIndex++) {
        	// calcula la fila del  indice actual  
            int indexRow = currentIndex / 3;
         // Calcular la columna del índice actual
            int indexCol = currentIndex % 3;
            // obtiene el valor actual de la pieza como un entero
            int currentValue = (int) this.valorTablero.charAt(currentIndex) - 48;
            // calcula la fila. El valor DEBERIA estar en
            int valueRow = currentValue / 3;
            // calcula la columna. El valor DEBERIA estar en
            int valueCol = currentValue % 3;
            // manhattan distancia = abs(actual fila - fila deseada) + abs(actual col - valor col)
         // Añadir nueva distancia Manhattan de la pieza para  la variable de la suma manhattanDistancia

            distanciaManhattan += Math.abs(indexRow - valueRow) + Math.abs(indexCol - valueCol);
        }
        return distanciaManhattan;
    }

    
   

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
    public boolean esSolucion() {
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
    
    toma una matriz de 3x3 representado como un arreglo tipochar de 8 caracteres
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
                	//si  vacio no esta  en la  fila inferior  , mover  abajo vacio
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
    
    // setters
    /*
    Configurar el tablero con los valores deseados
    */
    public void setTablero(String nuevoValor){
    	//asignar valores al tablero al nuevo stringbuffer usando nuevos valores
        this.valorTablero = new StringBuffer(nuevoValor);
        //recalcula el valor A*
        this.funcionCoste();
    }
    
    // getters
    /*
   
    
    */
    public int getValor(){
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
