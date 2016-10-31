package puzzle;


import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.*;
/*
Clase aplicacion del programa
*/
public class OchoPuzzle {

    // constructor
    public OchoPuzzle() {
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        
        System.out.println("Te gustaria");
        System.out.println("1 - Crear un 8puzzle randomico?");
        System.out.println("2 - Ingresar una configuracion manual de 8 puzzle?");
        
        int seleccion = reader.nextInt();
        reader.nextLine();
        
        TableroPuzzle tableroPrueba = new TableroPuzzle("012345678", 2);
        while(seleccion != 1 && seleccion != 2){
            System.out.println("Lo sentimos no se reconoce esa instruccion ingresar 1 o 2");
            seleccion = reader.nextInt();
        }
        if(seleccion == 1){
            tableroPrueba.mezclarTablero(150);
        } else if (seleccion == 2){
            boolean validacionPuzzle = false;
            while (!validacionPuzzle) {
                System.out.println("Ingresar un 8puzzle en el siguiente formato: ");
                System.out.println("x x x");
                System.out.println("x x x");
                System.out.println("x x x");

                String valorUsuario;
        
                valorUsuario = reader.nextLine();
                valorUsuario += reader.nextLine();
                valorUsuario += reader.nextLine();

                valorUsuario = valorUsuario.replaceAll("\\s", "");
                
                tableroPrueba.setTablero(valorUsuario);
                validacionPuzzle = tableroPrueba.esSolucion();
                if(!validacionPuzzle){
                    System.out.println("8Puzzle invalido.No sepuede resolver.");
                }
            }
        }
		System.out.println();
        System.out.println("Estado inicial del Puzzle: ");
        tableroPrueba.imprimirPuzzle();
		System.out.println();
        
        solucionPuzzle solPuzzle = new solucionPuzzle(tableroPrueba.getTableroValor());
      
        System.out.println("  Manhattan");
        System.out.println("===================");
		System.out.println();
        System.out.println("\nNodos Generados: " + solPuzzle.solManhattan());
        System.out.println("Pasos Utilizados: " + solPuzzle.getPasos());
        String salida="";
        salida+=solPuzzle.solManhattan();
        JOptionPane.showMessageDialog(null, salida);
        
		
		
    }
	
	
}
