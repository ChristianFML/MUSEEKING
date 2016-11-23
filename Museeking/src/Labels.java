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
 * Labels.java
 *      Esta clase se encarga del manejo de los distintos labels (la forma en que se relacionaran los nodos)
 */
import org.neo4j.graphdb.RelationshipType;

public enum Labels implements RelationshipType{
        //que artista COMPUSO que cancion, que genero CONTIENE que artistas, 
        //que artistas se RELACIONAN con que estado, que cancion LIKE usuario, que genero LISTEN que usuario
        compuso, contiene, relacionado, like, listen;
    }
