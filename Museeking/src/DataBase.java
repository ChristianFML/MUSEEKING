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
 * DataBase.java
 *      Esta clase se encarga de llenar la base de datos con toda la información, a partir de ella se podrán hacer las búsquedas
 */

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class DataBase {
    
    public GraphDatabaseService db;
    public enum Nodes implements Label{
        artista, cancion, estado, genero, Usuario;
    }
    public enum Labels implements RelationshipType{
        compuso, contiene, relacionado, like, listen;
    }
    
    public DataBase(GraphDatabaseService DB){
        db = DB;
    }
    
    //Este metodo se encarga de agregar un nuevo usuario
    public void crearUsuario(String nombre, GraphDatabaseService graphDb){
        try (Transaction tx = graphDb.beginTx()){
            Node user = db.createNode(Nodes.Usuario);
            user.setProperty("nombre", nombre);
            tx.success();
       }
    }
    
    //Este metodo se encarga de indicar cuando una cancion le gusta a un usuario
    public void crearRelacion(Node usuario, Node cancion){
        try (Transaction tx = db.beginTx()){
            usuario.createRelationshipTo(cancion,Labels.listen);
            usuario.createRelationshipTo(cancion,Labels.like);
            tx.success();
        }    
    }
}
