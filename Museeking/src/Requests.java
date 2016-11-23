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
 * Requests.java
 *      Esta clase se encarga de realizar todas las busquedas especificas
 */
import java.util.Iterator;
import java.util.LinkedList;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

public class Requests {
    
    static Result resultado, resultado1;
    private GraphDatabaseService db;
    
    public Requests(GraphDatabaseService DB){
        db = DB;
    }
    
    public Node ingreso(String nombre){
        try (Transaction tx = db.beginTx()){
            Node usuario = db.findNode(Nodes.Usuario, "nombre", nombre);
            tx.success();
            return usuario;
        }
    }
    
    public Node encontrarCancion(String nombre){
        try (Transaction tx = db.beginTx()){
            Node cancion = db.findNode(Nodes.cancion, "nombre", nombre);
            tx.success();
            return cancion;
        }
    }
    
    //Se regresa un array tipo String con los nombres de los usuarios actuales
    public String[] usuarios(){                   
        try (Transaction tx = db.beginTx()){
            resultado = db.execute("MATCH (P1:Usuario) RETURN P1.nombre");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                res1.add(r1.next());
            }
            tx.success();
            String[] names = new String[res1.size()];
            for(int i = 0; i < names.length; i++){
                names[i] = res1.get(i);
            }
            return names;
        }       
    }
    
    //Muestra las canciones que le gustan a un usuario en especifico
    public LinkedList<String> gustos(String nombre){
                       
        try (Transaction tx = db.beginTx()){
            resultado = db.execute("MATCH (P1:Usuario)-[C:like]->(P2:cancion) RETURN P1.nombre");
            resultado1 = db.execute("MATCH (P1:Usuario)-[C:like]->(P2:cancion) RETURN P2.nombre");
            Iterator<String>r1=resultado1.columnAs("P1.nombre");
            Iterator<String>r2=resultado1.columnAs("P2.nombre");
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
    
    //Muestra los generos que escucha el usuario
    public LinkedList<String> generosUsuario(String nombre){
                       
        try (Transaction tx = db.beginTx()){
            resultado = db.execute("MATCH (P1:Usuario)-[C:listen]->(P2:genero) RETURN P1.nombre");
            resultado1 = db.execute("MATCH (P1:Usuario)-[C:listen]->(P2:genero) RETURN P2.nombre");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            Iterator<String>r2=resultado1.columnAs("P2.nombre");
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
    
    //Despliega una playlist con canciones con base en lo que sus amigos escuchan
    public LinkedList<String> recomendacionAmigos(String nombre){
                       
        try (Transaction tx = db.beginTx()){
            resultado = db.execute("MATCH (P1:Usuario)-[C:like]->(P2:cancion) RETURN P1.nombre");
            resultado1 = db.execute("MATCH (P1:Usuario)-[C:like]->(P2:cancion) RETURN P2.nombre");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            Iterator<String>r2=resultado1.columnAs("P2.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                if(r1.equals(nombre)){
                    r2.next();
                }else{
                    res1.add(r2.next());
                }
            }
            tx.success();
            return res1;
        }       
    }
    
    //Despliega una playlist con canciones que el programa le sugiere
    public LinkedList<String> descubrimientoSemanal(String nombre){
        
        try (Transaction tx = db.beginTx()){
            resultado = db.execute("MATCH (P1:Usuario)-[C:listen]->(P2:genero) RETURN P1.nombre");
            resultado1 = db.execute("MATCH (P1:Usuario)-[C:listen]->(P2:genero) RETURN P1.nombre");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            Iterator<String>r2=resultado1.columnAs("P2.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agrega a la lista todos los generos que no escucha el usuario
            while (r1.hasNext()){
                if(r1.equals(nombre)){
                    r2.next();
                }else{
                    res1.add(r2.next());
                }
            }
            //Nueva lista para guardar el total de canciones que estan en los generos que no escucha el usuario
            LinkedList<String> res = new LinkedList();
            //primero for hace un recorrido en la lista de generos, avanza genero por genero
            for(int i = 0; i <res1.size();i++){
                //segundo for lee todas las canciones del genero en cuestion y las va agregando a la lista total
                for(int j = 0; j <iniciarRadioGenero(res1.get(i)).size(); j++){
                    res.add(iniciarRadioGenero(res1.get(i)).get(j));
                }
            }
            tx.success();
            //Se regresa la lista total
            return res;
        }       
    }
    
    //Este metodo regresa una lista con todas las canciones de un genero en especifico
    public LinkedList<String> iniciarRadioGenero(String genero){
                       
        try (Transaction tx = db.beginTx()){
            resultado =db.execute("MATCH (P1:genero)-[r:contiene]->(P2:artista) RETURN P1.nombre");
            resultado1 = db.execute("MATCH (P1:genero)-[r:contiene]->(P2:artista) RETURN P2.nombre");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            Iterator<String>r2=resultado1.columnAs("P2.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                if(r1.next().equals(genero)){
                    res1.add(r2.next());
                }else{
                    r2.next();
                }
            }
            tx.success();
            return res1;
        }       
    }
    
    //Este metodo regresa una lista con todas las canciones relacionadas a un estado de naimo en especifico
    public LinkedList<String> iniciarRadioEstado(String estado){
                       
        try (Transaction tx = db.beginTx()){
            resultado =db.execute("MATCH (P1:artista)-[r:relacionado]->(P2:estado) RETURN P2.nombre");
            resultado1 = db.execute("MATCH (P1:artista)-[r:relacionado]->(P2:estado) RETURN P1.nombre");
            Iterator<String>r1=resultado.columnAs("P2.nombre");
            Iterator<String>r2=resultado1.columnAs("P1.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                if(r1.next().equals(estado)){
                    res1.add(r2.next());
                }else{
                    r2.next();
                }
            }
            tx.success();
            return res1;
        }       
    }
    
    //Se despliegan las canciones de un artista en específico
     public LinkedList<String> cancionesArtista(String artista){
                       
        try (Transaction tx = db.beginTx()){
            resultado =db.execute("MATCH (P1:artista)-[r:compuso]->(P2:cancion) RETURN P1.nombre");
            resultado1 = db.execute("MATCH (P1:artista)-[r:compuso]->(P2:cancion) RETURN P2.nombre");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            Iterator<String>r2=resultado1.columnAs("P2.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                if(r1.next().equals(artista)){
                    res1.add(r2.next());
                }else{
                    r2.next();
                }
            }
            tx.success();
            return res1;
        }       
    }
    
    public LinkedList<String> canciones(){
                       
        try (Transaction tx = db.beginTx()){
            resultado = db.execute("MATCH (P1:cancion) RETURN P1.nombre LIMIT 10");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                res1.add(r1.next());
            }
            tx.success();
            return res1;
        }       
    }
    
    public LinkedList<String> estado(){
                       
        try (Transaction tx = db.beginTx()){
            resultado = db.execute("MATCH (P1:estado) RETURN P1.nombre LIMIT 10");
            Iterator<String>r1=resultado.columnAs("P1.nombre");
            //Se crearon listas para insertar los datos del iterador
            LinkedList<String> res1 = new LinkedList();            
            //Se agregan los datos a la lista
            while (r1.hasNext()){
                res1.add(r1.next());
            }
            tx.success();
            return res1;
        }       
    }
    
    public String toString(LinkedList<String> lista){
        String converted = "";
        for(int i=0;i<lista.size()-1;i++){
            converted = converted + lista.get(i) + ", ";
        }
        return converted;
    }
    
}
