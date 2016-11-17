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
import java.util.Iterator;
import java.util.LinkedList;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

public class Requests {
    
    static Result resultado, resultado1;
    
    public Node ingreso(GraphDatabaseService db, String nombre){
	Node usuario;
	//se busca usuario por el nombre ingresado
	usuario= db.findNode(Nodes.Usuario, "nombreP", nombre);
	if (usuario==null){
	//si no se encontró el usuario, se crea un usuario nuevo
            usuario= db.createNode();
            usuario.addLabel(Nodes.Usuario);
            usuario.setProperty("nombreP", nombre);
            System.out.println("Usuario nuevo");
	}else{
            //se encontró el usuario
            System.out.println("Usuario guardado");
	}
	return usuario;
    }
    
    public LinkedList<String> gustos(GraphDatabaseService DB, String nombre){
                       
        try (Transaction tx = DB.beginTx()){
            resultado = DB.execute("MATCH (P1:Usuario)-[C:Like]->(P2:Pelicula) RETURN P1.Nombre");
            resultado1 = DB.execute("MATCH (P1:Usuario)-[C:Like]->(P2:Pelicula) RETURN P2.Nombre");
            Iterator<String>r1=resultado.columnAs("P1.Nombre");
            Iterator<String>r2=resultado1.columnAs("P2.Nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                if(r1.next().equals(nombre)){
                    res1.add(r2.next());
                }else{
                    r2.next();
                }
            }
            tx.success();
            return res1;
        }       
    }
    
}
