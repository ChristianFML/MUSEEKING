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
 * Nodes.java
 *      Esta clase se encarga del manejo de los Labels con los que se relacionaran los Nodos
 */
import org.neo4j.graphdb.RelationshipType;

public enum Labels implements RelationshipType{
        Like;
    }
