package com.epn;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Stack;
//Dada una configuración específica del tablero se procede a calcular el número de nodos generados
//para lo cual se utilizará la heuristica manhatan o heuristica en base a posiiones incorrectas de las casillas
class solucionPuzzle {    
    // Raiz del tablero 
    private TableroPuzzle raizTablero;    
    //Configuracion de la raiz del tablero
    private String config;   
    //numero de nodos generados para la ultima solucion
    private int nodosGen;    
    //numero de pasos necesarios para la solucion optima
    private int pasos;
  
    //constructores
    public solucionPuzzle(String configInicial){
    	//inicializamos y guardamos la configuracion inicial in config
        this.config = new String();
        this.config = configInicial;
        // inicializamos los nodos generados y los pasos en cero
        this.nodosGen = 0;
        this.pasos = 0;
    }
    //getter
    public int getPasos(){
      return this.pasos;
    }
    //Llamada a la solución mediante la utilización de la heurística basada en piezas mal colocadas
    public int solSacarPiezas(){
        return this.solucion(1);
    }
    
    // Llamada a la solucion del puzzle usando la heuristica Manhattan (distancias)
    
    public int solManhattan(){
        return this.solucion(2);
    }

    private int solucion(int tipoHeuristica){
        // PriorityQueue es usado como la  frontera en nuestro A* algorithm
    	//Usa compareTo, o un comparador, para ordenar los elementos por su prioridad
    	//Crea e inicializa un nuevo comparador del tablero de puzzle
        Comparator<TableroPuzzle> puzzleComparador = new PuzzleComparador();
        /*
         creamos e inicializamos un nuevo priorityqueue usando un valor de 10 para iniciar 
         */
     
        PriorityQueue<TableroPuzzle> cola = new PriorityQueue<>(10, puzzleComparador);
        /*
         El HashMap es un conjunto de key-value.
		En esto objeto puedes guardar cualquier tipo de objeto, asignándole una key, para, 
		mas tarde, poder recuperar ese objeto mediante la key. 
         y lo usamos para expandir los nodos de nuestro algoritmo A*
         Creamos e inicializamos un HashMap que une un String al puzzle
        // Inicializamos un tamaño inicial a 10
         */
                
        HashMap<String, TableroPuzzle> hashMap = new HashMap<>(10);
        /*
         Creamos un nuevo puzzle usando la configuracion del usuario y la 
         heuristica elgida 
         */     
        this.raizTablero = new TableroPuzzle(this.config, tipoHeuristica);
        
        //añadimos la raiz a nuestra cola
        cola.add(this.raizTablero);
        
        //inicializamos los nodos generados en cero (en caso de que la solucion se llame dos veces)
        this.nodosGen = 0;
        
        
       //Ciclo while minetras todavia haya nodos en la cola
        while(cola.size() > 0){
        	// Eliminar el tableroPuzzle de la cola con el menor valor a *

            TableroPuzzle tPuzzle = cola.remove();
          //Si el puzzle es la meta...
            if(tPuzzle.esMeta()){
            	//entero para mantener el numero de paso para la solucion
            	//empieza en -1 para que el rompecabezas no sea nulo en el nodo raiz
                int pasos = -1;
             // Pila para imprimir los nodos principales en orden inverso
				Stack<TableroPuzzle> stack = new Stack<>();
                while(tPuzzle != null){
                	//incrementa los pasos uno por padre
                    pasos++;
                    // añadimos el tablero puzzle  a  la pila
					stack.push(tPuzzle);
					//obtener el tablero de juego actual a su padre
                    tPuzzle = tPuzzle.getPadre();
                }
                
                while (stack.size() > 1) {
					stack.pop().imprimirPuzzle(); 
					System.out.println("__________");
                }
				stack.pop().imprimirPuzzle(); 
                
                this.pasos = pasos;
                
                //fin de la funcion y regresa numero de nodos generados
                return this.nodosGen;
            }
            
            //producir un puzzle si el tablero   actual esta vacio para moverse hacia arriba
            TableroPuzzle puzzleTemp = tPuzzle.moverArriba();
            // si el tablero no es null y el tablero no a sido explorado
            if (puzzleTemp != null){
            	// incrementa el contador del generador de nodos
              this.nodosGen++;
              if(!hashMap.containsKey(puzzleTemp.getTableroValor())) {
            	  // añade al tablero nuestra cola(frontera)
                cola.add(puzzleTemp);
              }
            }
            //producir un puzzle si el tablero   actual esta vacio para moverse hacia abajo
            puzzleTemp = tPuzzle.moverAbajo();
            // si el tablero no es null y el tablero no a sido explorado
            if (puzzleTemp != null){
            	// incrementa el contador del generador de nodos
              this.nodosGen++;
              if(!hashMap.containsKey(puzzleTemp.getTableroValor())) {
            	  // añade al tablero nuestra cola(frontera)
                cola.add(puzzleTemp);
              }
            }
            //producir un puzzle si el tablero   actual esta vacio para moverse hacia la izquierda
            puzzleTemp = tPuzzle.moverIzq();
            // si el tablero no es null y el tablero no a sido explorado
            if (puzzleTemp != null){
            	// incrementa el contador del generador de nodos
              this.nodosGen++;
              if(!hashMap.containsKey(puzzleTemp.getTableroValor())) {
            	  // añade al tablero nuestra cola(frontera)
                cola.add(puzzleTemp);
              }
            }
            //producir un puzzle si el tablero actual esta vacio para moverse hacia la derecha
            puzzleTemp = tPuzzle.moverDer();
            // si el tablero no es null y el tablero no a sido explorado
            if (puzzleTemp != null){
            	// incrementa el contador del generador de nodos
              this.nodosGen++;
              if(!hashMap.containsKey(puzzleTemp.getTableroValor())) {
            	  // añade al tablero nuestra cola(frontera)
                cola.add(puzzleTemp);
              }
            }
            
            //poner el tablero explorado dentro de nuestro hashmap
            hashMap.put(tPuzzle.getTableroValor(), tPuzzle);
        }
        
        //retornar 0 si la busqueda fallo
        return 0;
    }   
}
