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
 *      Rodrigo Barrios - 15
 *      Juan García - 15
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
    
    public DataBase(){ }
    
    //Aquí se cargan todos los valores de la base de datos, donde se aloja todo lo que ya está contenido en ella
    public void levantarDataBase(GraphDatabaseService DB){
               
        //Se intenta realizar una transacción u operación en Neo4j
        try (Transaction tx = DB.beginTx()){
            
//********* CREACIÓN DE NODOS ***********************************************************************************************************
            
            // U S U A R I O S
            Node user1 = DB.createNode(Nodes.Usuario);
            user1.setProperty("Id",1);
            user1.setProperty("Nombre", "Christian");
            
//********* CREACIÓN DE RELACIONES *******************************************************************************************************
            
            //Relationship rel1 = movie1.createRelationshipTo(actor1,Labels.esActor);
            
            tx.success();
        }
    }
}
