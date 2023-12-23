/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structuras;

import java.util.ArrayList;
//import structuras.tran;


/**
 *
 * @author Rami
 */
public class automata {
    
    private int estadoInicial;
    private ArrayList<Integer> estadosFinal= new ArrayList();
    private ArrayList<tran> transiciones = new ArrayList();
    
    public automata(){
        
    }
    
    public void setEstadoInicial(int estadoInicial){
        this.estadoInicial = estadoInicial;
    }
    
    public int getEstadoInicial(){
        return this.estadoInicial;
    }
    
    public ArrayList<Integer> getEstadosFinales(){
        return this.estadosFinal;
    }
    
    public void cambiarEstadoFinal(String nuevoEstado){
        
        for(tran var: this.transiciones){
            if(var.getEstadoActual().equals(String.valueOf(this.getLastEstadoFinal()) ) ){
                var.setEstadoActual(nuevoEstado); 
            }
            
            if(var.getEstadoDestino().equals(String.valueOf(this.getLastEstadoFinal()) ) ){
                var.setEstadoDestino(nuevoEstado); 
            }
        }
        
    }
    
    public void addEstadoFinal(int estadoFinal){
        this.estadosFinal.add(estadoFinal);
    }
    
    public int getLastEstadoFinal(){
        if(this.estadosFinal.size()>0){
            return this.estadosFinal.get(estadosFinal.size()-1);
        }
        return -1;
    }
    
    public void addTransicion(String estadoActual,String caracter,String estadoDestino){
        this.transiciones.add(new tran(estadoActual,caracter,estadoDestino));
    }
    
    public ArrayList getTransiciones(){
        return this.transiciones;
    }
    
    public void addTransicion(tran transicion){
        this.transiciones.add(transicion);
    }
    
    public ArrayList getCerradura(ArrayList<Integer> lista){
        ArrayList<Integer> subConjunto = new ArrayList();
        ArrayList<Integer> pila = new ArrayList();
        
        for(Integer var: lista){
            pila.add(var);
        }
        
        
        int estado;
        while(!pila.isEmpty()){
   
            estado = pila.get(pila.size()-1);
            subConjunto.add(estado);
            pila.remove(pila.size()-1);
            
            for(tran var: this.transiciones){
                
                if(estado == Integer.parseInt(var.getEstadoActual()) && var.getCarTransicion().equals("epsilon")){
                    boolean bandera=false;
                    for(Integer numE: subConjunto){
                        if(Integer.parseInt(var.getEstadoDestino())==numE){
                            bandera=true;
                            break;
                        }
                    }
                    if(!bandera){
                        pila.add(Integer.valueOf(var.getEstadoDestino()));
                    }
                    
                    
                
                }
            }
        }
        
        return subConjunto;
    }
    
    public ArrayList getMover(ArrayList<Integer> lista,String caracter){
        ArrayList<Integer> subConjunto = new ArrayList();
        ArrayList<Integer> pila = new ArrayList();
        
        for(Integer var: lista){
            pila.add(var);
        }
        
        
        int estado;
        while(!pila.isEmpty()){
   
            estado = pila.get(pila.size()-1);
            //subConjunto.add(estado);
            pila.remove(pila.size()-1);
            
            for(tran var: this.transiciones){
                
                if(estado == Integer.parseInt(var.getEstadoActual()) && var.getCarTransicion().equals(caracter)){
                    
                    //pila.add(Integer.valueOf(var.getEstadoDestino()));
                    subConjunto.add(Integer.valueOf(var.getEstadoDestino()));
                    
                
                }
            }
        }
        
        return subConjunto;
    }
    
    public automata metodoSubconjuntos(){
        //int numEstado=0;
        automata automataS = new automata();
        ArrayList<subC> estados = new ArrayList();
        ArrayList<subC> pila = new ArrayList();
        subC conjTemp;
        
        ArrayList<String> alfabeto = getAlfabeto();
        
        ArrayList<Integer> temp = new ArrayList();
        
        temp.add(this.estadoInicial);
        
        temp = getCerradura(temp);
        
        pila.add(new subC(estados.size(),temp));
        estados.add(new subC(estados.size(),temp));
        
        
        while(!pila.isEmpty()){
            
            conjTemp = pila.get(pila.size()-1);
            pila.remove(pila.size()-1);
            
            for(String var: alfabeto){
                temp= getCerradura(getMover(conjTemp.getConjunto(),var));
                if(!temp.isEmpty()){
                    
                    int numEstado=-1;
                    for(subC estado: estados){
                        
                        if(compararListas(estado.getConjunto(),temp)){
                            numEstado = estado.getNum();
                            break;
                        }
                    }
                    
                    if(numEstado==-1){
                        automataS.addTransicion(String.valueOf(conjTemp.getNum()), var, String.valueOf(estados.size()));
                        pila.add(new subC(estados.size(),temp));
                        estados.add(new subC(estados.size(),temp));
                        
                        
                    }else{
                        automataS.addTransicion(String.valueOf(conjTemp.getNum()), var, String.valueOf(numEstado));
                    }
                    
                }
            }
            
        }
        automataS.setEstadoInicial(estados.get(0).getNum());
        
        for(subC sub: estados){
            temp = sub.getConjunto();
            
            for (Integer var: temp){
                if(var==getLastEstadoFinal()){
                    automataS.addEstadoFinal(sub.getNum());
                    
                }
            }
        }
        
        return automataS;
    }
    
    public ArrayList getAlfabeto(){
        ArrayList<String> alfabeto = new ArrayList();
        
        for(tran var: this.transiciones){
            if(!var.getCarTransicion().equals("epsilon")){
                boolean bandera=false;
                
                for(String car: alfabeto){
                    
                    if(car.equals(var.getCarTransicion())){
                        bandera=true;
                        break;
                    }
                    
                }
                
                if(!bandera){
                    alfabeto.add(var.getCarTransicion());
                }
                
            }
        }
        
        return alfabeto;
    }
    
    public boolean compararListas(ArrayList<Integer> l1,ArrayList<Integer> l2){
        boolean bandera=false;
        for(Integer var1: l1){
            for(Integer var2:l2){
                
                if(var1==var2){
                    bandera=true;
                    break;
                }
                
            }
            if(bandera){
                bandera=false;
                continue;
            }else{
                return false; 
            }
            
        }
        
        return true;
    }
    
    
    
}
