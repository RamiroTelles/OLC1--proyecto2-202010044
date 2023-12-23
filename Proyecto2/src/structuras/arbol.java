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
    private ArrayList<arbol> hijos;
    //private int numero=0;
    
    public arbol(String lex){
        this.lex = lex;
        this.hijos = new ArrayList();
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
    
    public void traducirArbol(arbol raiz){
        
        if( !raiz.hijos.isEmpty() ){
            imprimirInOrder(raiz.hijos.get(0));
        }
        
        
        if(raiz.getLex().equals("+")){
            
        }
        
        if(raiz.hijos.size() > 1){
            imprimirInOrder(raiz.hijos.get(1));
        }
        
    }
    
    public automata generarAutomata(arbol raiz,int num){
        
        if(raiz.getLex().equals("|")){
            automata or1 = raiz.generarAutomata(raiz.obtenerHijo(0),num);
            
            automata or2 = raiz.generarAutomata(raiz.obtenerHijo(1),or1.getLastEstadoFinal()+1);
            
            automata orF = new automata();
            
            orF.setEstadoInicial(or2.getLastEstadoFinal()+1);
            orF.addEstadoFinal(or2.getLastEstadoFinal()+2);
            
            orF.addTransicion(String.valueOf(or2.getLastEstadoFinal()+1), "epsilon", String.valueOf(or1.getEstadoInicial()));
            orF.addTransicion(String.valueOf(or2.getLastEstadoFinal()+1), "epsilon", String.valueOf(or2.getEstadoInicial()));
            
            orF.addTransicion(String.valueOf(or1.getLastEstadoFinal()), "epsilon", String.valueOf(or2.getLastEstadoFinal()+2));
            orF.addTransicion(String.valueOf(or2.getLastEstadoFinal()), "epsilon", String.valueOf(or2.getLastEstadoFinal()+2));
            
            ArrayList<tran> t1 = or1.getTransiciones();
            
            ArrayList<tran> t2 = or2.getTransiciones();
            
            for(tran var: t1){
                orF.addTransicion(var);
            }
            
            for(tran var: t2){
                orF.addTransicion(var);
            }
            
            //this.numero= numero+2;
            
            return orF;
            
        }
        
        if(raiz.getLex().equals("*")){
            automata cl1 = raiz.generarAutomata(raiz.obtenerHijo(0),num);
            
            automata clF = new automata();
            
            clF.setEstadoInicial(cl1.getLastEstadoFinal()+1);
            clF.addEstadoFinal(cl1.getLastEstadoFinal()+2);
            
            clF.addTransicion(String.valueOf(cl1.getLastEstadoFinal()+1), "epsilon", String.valueOf(cl1.getEstadoInicial()));
            
            clF.addTransicion(String.valueOf(cl1.getLastEstadoFinal()), "epsilon", String.valueOf(cl1.getLastEstadoFinal()+2));
            
            clF.addTransicion(String.valueOf(cl1.getLastEstadoFinal()), "epsilon", String.valueOf(cl1.getEstadoInicial()));
            
            clF.addTransicion(String.valueOf(cl1.getLastEstadoFinal()+1), "epsilon", String.valueOf(cl1.getLastEstadoFinal()+2));
            
            ArrayList<tran> t1 = cl1.getTransiciones();
            
            for(tran var: t1){
                clF.addTransicion(var);
            }
            
            //this.numero= numero+2;
            
            return clF;
        }
        
        if(raiz.getLex().equals(".")){
          
            automata con1 = raiz.generarAutomata(raiz.obtenerHijo(0),num);
            
            automata con2 = raiz.generarAutomata(raiz.obtenerHijo(1),con1.getLastEstadoFinal()+1);
            
            automata conF = new automata();
            
            conF.setEstadoInicial(con1.getEstadoInicial());
            conF.addEstadoFinal(con2.getLastEstadoFinal());
            
            con1.cambiarEstadoFinal(String.valueOf(con2.getEstadoInicial()));
            
            ArrayList<tran> t1 = con1.getTransiciones();
            
            ArrayList<tran> t2 = con2.getTransiciones();
            
            for(tran var: t1){
                conF.addTransicion(var);
            }
            
            for(tran var1: t2){
                conF.addTransicion(var1);
            }
            
            
            
            return conF;
            
            
        }
        
        automata automata_caracter = new automata();
        automata_caracter.setEstadoInicial(num);
        automata_caracter.addEstadoFinal(num+1);
        automata_caracter.addTransicion(String.valueOf(num), raiz.getLex(), String.valueOf(num+1));
        
        //this.numero = this.numero+2;
        return automata_caracter;
        
    }
    
    
}
