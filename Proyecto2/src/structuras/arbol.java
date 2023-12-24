/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structuras;

/**
 *
 * @author Rami
 */
import java.util.ArrayList;

public class arbol {
    
    private String lex;
    private int linea;
    private int columna;
    private ArrayList<arbol> hijos;
    private Object result;
    //private int numero=0;
    
    public arbol(String lex){
        this.lex = lex;
        this.hijos = new ArrayList();
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    
    
    public void añadirHijo(arbol hijo){
        this.hijos.add(hijo);
    }
    
    public String getLex(){
        return this.lex;
    }
    
    public arbol obtenerHijo(int index){
        return hijos.get(index);
    }
    
//    public void reiniciarNum(){
//        this.numero =0;
//    }
//    public void imprimirArbol(arbol raiz){
//        
//        for(arbol hijo : raiz.hijos){
//            imprimirArbol(hijo);
//        }
//        
//        System.out.println(raiz.getLex());
//    }
   
    public void imprimirInOrder(arbol raiz){
        
        if( !raiz.hijos.isEmpty() ){
            imprimirInOrder(raiz.hijos.get(0));
        }
        
        
        System.out.println(raiz.getLex());
        
        if(raiz.hijos.size() > 1){
            imprimirInOrder(raiz.hijos.get(1));
        }
    }
    

    

    
    
}
