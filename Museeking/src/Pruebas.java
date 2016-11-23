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
        File directorio = new File("C:\\Users\\usuario\\Desktop\\PROYECTO ADT 2\\MUSEEKING\\Museeking\\db");
        //Se utiliza el objeto tipo file para ingresar al archivo del grafo
        GraphDatabaseService graphDb = dbFactory.newEmbeddedDatabase(directorio);
        
        Requests request = new Requests(graphDb);
        DataBase dataBase = new DataBase(graphDb);
        
        Scanner teclado = new Scanner(System.in); //Variable para leer los datos ingresados por el usuario
        
        System.out.println("PRUEBA DE MUSEEKING  \n");
        System.out.println("*****************************************************************************************************  \n");
        System.out.println("Debe elegir un usuario  \n");
        for(int i = 0; i <request.usuarios().length; i++){
            System.out.println("    "+request.usuarios()[i]);
        }
        String name = teclado.nextLine();
        Node user = request.ingreso(name);
        
        //System.out.println("Canciones que le gustan al usuario  \n");
        //System.out.println( request.toString( request.gustos(name) ) );
        
        System.out.println("Generos que escucha el usuario  \n");
        System.out.println( request.toString( request.generosUsuario(name) ) );
        
        System.out.println("Elegir un genero para iniciar una radio  \n");
        String genero = teclado.nextLine();
        System.out.println( request.toString( request.iniciarRadioGenero(genero) ) );
        
        System.out.println("Elegir un artista  \n");
        String artista = teclado.nextLine();
        System.out.println( request.toString( request.cancionesArtista(artista) ) );
        
        System.out.println("Se despliegan los estados de animo \n");
        System.out.println( request.toString( request.estado() ) );
        
        System.out.println("Escoja un estado  \n");
        String estado = teclado.nextLine();
        System.out.println( request.toString( request.iniciarRadioEstado(estado) ) );
        
        System.out.println("Recomendacion en base a amigos  \n");
        System.out.println( request.toString( request.recomendacionAmigos(name) ) );
        
        System.out.println("Recomandacion sugerida por el programa  \n");
        System.out.println( request.toString( request.descubrimientoSemanal(name) ) );
        
        
        
        System.out.println("Escoja una cancion: ");
        String temp = teclado.nextLine();//Aquí se usa nextLine por si la cancion tiene mas de una palabra
        
        //verificador del String que se utilizara para buscar la cancion
        System.out.println("Cancion esocigda: "+temp);
        Node cancion = request.encontrarCancion(temp);
        
        dataBase.crearRelacionCancion(user, cancion);
        
        System.out.println( "A " + name + " le gustan las siguientes canciones: " + request.toString( request.gustos(name)) );
        
        
    }
    
}
