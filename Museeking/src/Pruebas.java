/*
 * Universidad del Valle de Guatemala
 * Algoritmos y Estructura de Datos
 * Ing. Douglas Barrios
 * Poyecto 2 ADT - Sistema de Recomendaciones (con implementacion de grafos via Neo4j)
 * Titulo: Museeking
 * Descripción: Sistema de recomendación de músca
 * Fecha: noviembre 2016
 * Integrantes:
 *      Christian Morales - 15015
 *      José Luis Méndez - 15024
 *      Rodrigo Barrios - 15009
 *      Juan García - 15046
 */

/**
 * Pruebas.java
 *      Esta clase es un main alterno, para probar en consola el funcionamiento del programa
 */

import java.io.File;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import java.util.Scanner;

public class Pruebas {
        
    public static void main(String[] args) {
        //Se crea una fabrica para crear bases de datos
        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        //Se obtiene la dirección de la base de datos, esta cambia en cada computadora:
        File directorio = new File("C:\\Users\\usuario\\Desktop\\PROYECTO ADT 2\\MUSEEKING\\db");
        //Se utiliza el objeto tipo file para ingresar al archivo del grafo
        GraphDatabaseService graphDb = dbFactory.newEmbeddedDatabase(directorio);
        
        Requests request = new Requests(graphDb);
        DataBase dataBase = new DataBase(graphDb);
        
        Scanner teclado = new Scanner(System.in); //Variable para leer los datos ingresados por el usuario
        Scanner tec = new Scanner(System.in);
        String temp = "";
        
        System.out.println("PRUEBA DE MUSEEKING  \n");
        System.out.println(" INGRESAR USUARIO  \n");
        System.out.println(" Igrese el nombre:  ");
        String name = teclado.next();
        
        //dataBase.crearUsuario(graphDb, name);
        Node user = request.ingreso(name);
        
        System.out.println( request.toString(request.canciones()) + "\n");
        
        System.out.println("Escoja una cancion: ");
        temp = teclado.next();
        //Descomentar si tiene mas de dos palabras la cancion, si tiene tres agregar otra linea igual
        //temp = temp + " " +teclado.next();
        
        //verificador del String que se utilizara para buscar la cancion
        System.out.println("Cancion esocigda: "+temp);
        Node cancion = request.encontrarCancion(temp);
        
        dataBase.crearRelacion(user, cancion);
        
        System.out.println( "A " + name + " le gustan las siguientes canciones: " + request.toString( request.gustos(name)) );
        
        
    }
    
}
