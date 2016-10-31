package com.epn;
import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;
//Clase aplicacion del programa
public class OchoPuzzle {
    // constructor
    public OchoPuzzle() {
    }

    public static void main(String[] args) {               
        int seleccion=Integer.parseInt(JOptionPane.showInputDialog("8 puzzle \n1: Generar tablero randómico y procesar algoritmo \n2: salir"));
        TableroPuzzle tableroPrueba = new TableroPuzzle("012345678", 2);
        while(seleccion != 1 && seleccion != 2){
            System.out.println("Favor ingresar una de las opciones");       
        }
        if(seleccion == 1){
            tableroPrueba.mezclarTablero(150);
        }/* else if (seleccion == 2){
        	System.exit(0);;
           boolean validacionPuzzle = tableroPrueba.tieneSolucion();
                if(!validacionPuzzle){
                    System.out.println("8Puzzle invalido.No sepuede resolver.");
                }
           
        }*/
		System.out.println();
        System.out.println("Estado inicial del Puzzle: ");
        tableroPrueba.imprimirPuzzle();
		System.out.println();
        
        solucionPuzzle solPuzzle = new solucionPuzzle(tableroPrueba.getTableroValor());
        System.out.println("/////////////////////////");
        System.out.println("Heuristica: Piezas mal colocadas");
        System.out.println("/////////////////////////");
		System.out.println();
        System.out.println("\nNodos Generados: " + solPuzzle.solSacarPiezas());
        System.out.println("Pasos Utilizados: " + solPuzzle.getPasos());
		System.out.println();
        System.out.println("////////////////////////");
        System.out.println(" Heuristica:Manhattan");
        System.out.println("////////////////////////");
		System.out.println();
        System.out.println("\nNodos Generados: " + solPuzzle.solManhattan());
        System.out.println("Pasos Utilizados: " + solPuzzle.getPasos());
    }
}