/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto1.igu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import analizador.parser;
import analizador.scanner;

import java.io.FileWriter;
import java.io.PrintWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.mycompany.proyecto1.igu.grafica;
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.general.DefaultPieDataset;
import structuras.arbol;

import structuras.tablaJson;
import structuras.elToken;
import structuras.fallos;





/**
 *
 * @author Rami
 */
public class Pantalla extends javax.swing.JFrame implements ActionListener{

    /**
     * Creates new form Pantalla
     */

    //private int conNot=0;
    private String nombreArchivo ="";
    private int cantTabJson=0;
    private String consola="";

   
    public Pantalla() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jMenuItem1.addActionListener(this);
        jMenuItem2.addActionListener(this);
        jMenuItem3.addActionListener(this);
        jMenuItem4.addActionListener(this);
        
        
    }
    
    private void imprimirConsolaLn(String txt){
        this.consola+=txt;
        this.consola+="\n";
        jTextArea2.setText(consola);
    }
    
    private void imprimirConsola(String txt){
        this.consola+=txt;
        //this.consola+="\n";
        jTextArea2.setText(consola);
    }

    private void leerArchivo(){
        JFileChooser fc= new JFileChooser();
        fc.showOpenDialog(null);
        
        File archivo = fc.getSelectedFile();
        //String nombre = fc.getName(archivo);
        nombreArchivo = archivo.getPath();
        try{
            FileReader fr = new FileReader(archivo);
            BufferedReader br= new BufferedReader(fr);
            String txt="";
            String linea="";

            while((linea=br.readLine())!=null){
                txt+=linea+"\n";
            }
            jTextArea1.setText(txt);
            //System.out.println(txt);
            JOptionPane.showMessageDialog(null,"Archivo abierto Correctamente");
        }catch(IOException e){

        }
    }
    
    private void guardarArchivo(String txt) throws IOException{
       
        
        File archivo = new File(this.nombreArchivo);
        //String nombre = fc.getName(archivo);
        
        
        FileWriter fichero=null;
        PrintWriter pw;
        try {
            
            
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            
            //Comenzamos a escribir el html
            pw.println(txt);
           
            
            
        } catch (Exception e) {
        } finally {
            if (fichero != null) {
                fichero.close();
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    private void guardarComo(String txt) throws IOException{
        JFileChooser fc= new JFileChooser();
        fc.showSaveDialog(null);
        
        File archivo = fc.getSelectedFile();
        //String nombre = fc.getName(archivo);
        this.nombreArchivo = archivo.getPath();
        
        FileWriter fichero=null;
        PrintWriter pw;
        try {
            
            
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            
            //Comenzamos a escribir el html
            pw.println(txt);
           
            
            
        } catch (Exception e) {
        } finally {
            if (fichero != null) {
                fichero.close();
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    private void analizarJSON(String txt){
        ArrayList<elToken> listaTokens = new ArrayList();
        ArrayList<fallos> listaErrores = new ArrayList();
        ArrayList<tablaJson> TS = new ArrayList();
        
        try{
            scanner scan1 = new scanner(new java.io.StringReader(txt));

            parser parser1 = new parser(scan1);
            //parser1.parse();
            //arbol resultado = parser1.getArbol();
            arbol resultado = (arbol)parser1.parse().value;
            resultado.enumerarArbol(resultado, 0);
            graficarAST(resultado,"AST");
            System.out.println("Analisis realizado correctamente");
            
            
            run(resultado,TS);
            
            for(tablaJson elemento : TS) {
			System.out.println(elemento.getId()+"\t"+elemento.getRol()+"\t"+elemento.getTipo()+"\t"+elemento.getValor());
            }
            
            
            listaTokens.addAll(scan1.getTokens());
            listaErrores.addAll(scan1.getErrores());
            //tabJson.addAll(parser1.getTabla());
            //imprimirTokens(listaTokens);
            //imprimirErrores(listaErrores);
            
            generarReporteErrores(listaErrores,"erroresJson");
            generarReporteTokens(listaTokens,"tokensJson");
            //agregarNombreArchivo();
            //imprimirTablaJson();
            
            

        }catch( Exception e){
            System.out.println(e.getMessage());
        }


    }
    
    public void run(arbol raiz,ArrayList<tablaJson> TS){
        
        for(arbol var: raiz.getHijos()){
            if(var.isAct()){
                run(var,TS);
            }  
        }
        
        
        
        if(raiz.getLex().equals("valor") && raiz.getHijos().size()==1){ // valor->el mero valor
            if(raiz.obtenerHijo(0).getLex().equalsIgnoreCase("true")){
                raiz.setResult("1");
            }else if(raiz.obtenerHijo(0).getLex().equalsIgnoreCase("false")){
                raiz.setResult("0");
            }else{
                raiz.setResult(raiz.obtenerHijo(0).getLex());
            }
            
        }else if(raiz.getLex().equals("valor1") && raiz.getHijos().size()==1){// valor1 -> valor
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("valor1") && raiz.getHijos().size()==2){// valor 1-> menos expPrima
            try{
                raiz.setResult(Double.parseDouble((String)raiz.obtenerHijo(1).getResult())*-1);
            }catch(Exception e){
                imprimirConsolaLn("Error Semantico, tipo no válido para operacion -");
            }
        }else if(raiz.getLex().equals("expPrima") && raiz.getHijos().size()==1){// expPrima -> valor1
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("expArit4") && raiz.getHijos().size()==1){// expArit4 -> expPrima
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("expArit3") && raiz.getHijos().size()==1){// expArit3 -> expArit4
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("expArit") && raiz.getHijos().size()==1){// expArit -> expArit3
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("expRel") && raiz.getHijos().size()==1){// expRel -> expArit
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("expLog2") && raiz.getHijos().size()==1){// expLog2 -> expRel
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("expLog1") && raiz.getHijos().size()==1){// expLog1 -> expLog2
            raiz.setResult(raiz.obtenerHijo(0).getResult());
        }else if(raiz.getLex().equals("expLog") && raiz.getHijos().size()==1){// expLog -> expLog1
            raiz.setResult(raiz.obtenerHijo(0).getResult());
            
            
            
            
        }else if(raiz.getLex().equals("expPrima") && raiz.getHijos().size()==3){// expPrima -> ( expLog )
            raiz.setResult(raiz.obtenerHijo(1).getResult());
            
            
            
            
            
        }else if(raiz.getLex().equals("expArit4Prima")){// expArit4Prima -> / expPrima || expArit4Prima -> % expPrima
            raiz.setResult(raiz.obtenerHijo(1).getResult());
        }else if(raiz.getLex().equals("expArit4") && raiz.getHijos().size()==2){// expArit4 -> expArit4 expArit4Prima
            try{
                
                if(raiz.obtenerHijo(1).obtenerHijo(0).getLex().equals("/")){
                    //dividir
                    Object resultado = Double.parseDouble( String.valueOf( raiz.obtenerHijo(0).getResult())) / Double.parseDouble( String.valueOf( raiz.obtenerHijo(1).obtenerHijo(1).getResult()));
                    
                    raiz.setResult(resultado);
                }else{
                    //Aplicar modulo
                    
                    Object resultado = Double.parseDouble( String.valueOf( raiz.obtenerHijo(0).getResult() ) ) % Double.parseDouble( String.valueOf( raiz.obtenerHijo(1).obtenerHijo(1).getResult() ) );
                    
                    raiz.setResult(resultado);
                }
                
            }catch(Exception e){
                
                imprimirConsolaLn("Error Semantico");
                
            }
        }else if(raiz.getLex().equals("expArit3Prima")){// expArit3Prima -> * expArit4 || expArit4Prima -> ^ expArit4
            raiz.setResult(raiz.obtenerHijo(1).getResult());
            
            
            
        }else if(raiz.getLex().equals("expArit3") && raiz.getHijos().size()==2){// expArit3 -> expArit3 expArit3Prima
            try{
                
                if(raiz.obtenerHijo(1).obtenerHijo(0).getLex().equals("*")){
                    //multiplicar
                    Object resultado = Double.parseDouble( String.valueOf( raiz.obtenerHijo(0).getResult())) * Double.parseDouble( String.valueOf( raiz.obtenerHijo(1).obtenerHijo(1).getResult()));
                    
                    raiz.setResult(resultado);
                }else{
                    //Potencia
                    double base= Double.parseDouble(String.valueOf(raiz.obtenerHijo(0).getResult()));
                    double exp = Double.parseDouble(String.valueOf(raiz.obtenerHijo(1).obtenerHijo(1).getResult()));
                    
                    
                    
                    //Object resultado = Integer.parseInt((String)raiz.obtenerHijo(0).getResult()) % Integer.parseInt((String)raiz.obtenerHijo(1).obtenerHijo(1).getResult());
                    
                    raiz.setResult(calcularPotenciaL(base,exp));
                }
                
            }catch(Exception e){
                
                imprimirConsolaLn("Error Semantico");
            }
            
            
            
            
            
        }else if(raiz.getLex().equals("expArit2")){// exparit2 -> + expArit3 || expArit4Prima -> - expArit3
            raiz.setResult(raiz.obtenerHijo(1).getResult());
            
            
            
        }else if(raiz.getLex().equals("expArit") && raiz.getHijos().size()==2){// expArit -> expArit expArit2
            try{
                
                if(raiz.obtenerHijo(1).obtenerHijo(0).getLex().equals("+")){
                    //sumar
                    Object resultado = Double.parseDouble( String.valueOf( raiz.obtenerHijo(0).getResult())) + Double.parseDouble( String.valueOf( raiz.obtenerHijo(1).obtenerHijo(1).getResult()));
                    
                    raiz.setResult(resultado);
                }else{
                    //restar
                    
                    
                    Object resultado = Double.parseDouble( String.valueOf( raiz.obtenerHijo(0).getResult())) - Double.parseDouble( String.valueOf( raiz.obtenerHijo(1).obtenerHijo(1).getResult()));
                    
                    raiz.setResult(resultado);
                }
                
            }catch(Exception e){
                
                try{
                
                    if(raiz.obtenerHijo(1).obtenerHijo(0).getLex().equals("+")){
                        //Concatenar
                        String c1 = String.valueOf( raiz.obtenerHijo(0).getResult());
                        String c2 = String.valueOf( raiz.obtenerHijo(1).obtenerHijo(1).getResult());
                        Object resultado = "\""+c1.substring(1, c1.length()-1)+c2.substring(1,c2.length()-1)+"\"";

                        raiz.setResult(resultado);
                    }else{
                        //restar

                        imprimirConsolaLn("Error Semantico");
                        //Object resultado = Double.parseDouble( String.valueOf( raiz.obtenerHijo(0).getResult())) - Double.parseDouble( String.valueOf( raiz.obtenerHijo(1).obtenerHijo(1).getResult()));

                        //raiz.setResult(resultado);
                    }

                }catch(Exception e2){



                    imprimirConsolaLn("Error Semantico");
                }
                
                
               
            }
            
            
            
            
            
        }else if(raiz.getLex().equals("expRel1")){// expRel1 -> > expArit || expRel1 -> < expArit || expRel1 -> >= expArit || expRel1 -> <= expArit || expRel1 -> == expArit || expRel1 -> != expArit 
            raiz.setResult(raiz.obtenerHijo(1).getResult());
            
            
            
        }else if(raiz.getLex().equals("expRel") && raiz.getHijos().size()==2){// expRel -> expRel expRel1
            try{
                Double num1 = Double.valueOf(String.valueOf(raiz.obtenerHijo(0).getResult()));
                Double num2 = Double.valueOf(String.valueOf(raiz.obtenerHijo(1).obtenerHijo(1).getResult()));
                
                String op= String.valueOf(raiz.obtenerHijo(1).obtenerHijo(0).getLex());
                
                raiz.setResult(calcularRelacionales(num1,num2,op));
                
                
            }catch(Exception e){
                
                imprimirConsolaLn("Error Semantico");
            }
            
            
            
            
            
        }else if(raiz.getLex().equals("expNot") && raiz.getHijos().size()==1){// expNot-> Not
            
            raiz.setResult("1");
        }else if(raiz.getLex().equals("expNot") && raiz.getHijos().size()==2){// expNot-> expNot Not
            
            raiz.setResult(Integer.parseInt( String.valueOf( raiz.obtenerHijo(0).getResult() ) ) +1);
            
            
            
            
            
        }else if(raiz.getLex().equals("expLog2") && raiz.getHijos().size()==2){// expLog2-> expNot expRel
            
            int conNot= Integer.parseInt( String.valueOf(raiz.obtenerHijo(0).getResult()));
            
            if(!(conNot%2==0)){
                raiz.setResult(negarValor( Integer.parseInt(String.valueOf(raiz.obtenerHijo(1).getResult()))));
            }else{
                raiz.setResult(raiz.obtenerHijo(1).getResult());
            }
            
            
            
        }else if(raiz.getLex().equals("expLog") && raiz.getHijos().size()==3){// expLog -> expLog || expLog1
            
            try{
                
                if( Integer.parseInt( String.valueOf(raiz.obtenerHijo(0).getResult())) ==1 || Integer.parseInt( String.valueOf(raiz.obtenerHijo(2).getResult()))== 1 ){
                
                    raiz.setResult("1");
               
                }else{
                    raiz.setResult("0");
                }
                
            }catch(Exception e){
                
                imprimirConsolaLn("Error Semantico");
                
            }
            
            
        }else if(raiz.getLex().equals("expLog1") && raiz.getHijos().size()==3){// expLog1-> expLog1 && expLog2
            
            try{
            
                
                if( Integer.parseInt( String.valueOf(raiz.obtenerHijo(0).getResult())) ==1 && Integer.parseInt( String.valueOf(raiz.obtenerHijo(2).getResult()))==1){
                
                    raiz.setResult("1");
               
                }else{
                    raiz.setResult("0");
                }
            }catch(Exception e){
                
                imprimirConsolaLn("Error Semantico");
                
                //imprimirConsolaLn(String.valueOf(raiz.getResult()));
                
            }
            
            
        }else if(raiz.getLex().equals("declaracion2") && raiz.getHijos().size()==3){// declaracion2 -> = expLog ;
            
            raiz.setResult(raiz.obtenerHijo(1).getResult());
            
            
            //imprimirConsolaLn(String.valueOf(raiz.getResult()));
        }else if(raiz.getLex().equals("declaracion2") && raiz.getHijos().size()==1){// declaracion2 -> ;
            
            raiz.setResult(null);
           
        }else if(raiz.getLex().equals("declaracion1")){// declaracion1 -> tipo id declaracion2
            
            if(raiz.obtenerHijo(2).getResult()==null){
                //Se envia valor Predeterminado
                
                switch(String.valueOf( raiz.obtenerHijo(0).getResult())){
                    
                    case "cadena":
                        TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","string","global","main",""));
                        break;
                    case "caracter":
                        TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","char","global","main",'0'));
                        break;
                    case "binario":
                        TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","bool","global","main",1));
                        break;
                    case "doble":
                        TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","double","global","main",0.0));
                        break;
                    case "entero":
                        TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","int","global","main",0));
                        break;
                }   
            }else{
                //Se envia valor Asignado
                try{
                    switch(String.valueOf( raiz.obtenerHijo(0).getResult())){
                    
                        case "cadena":
                            if(String.valueOf(raiz.obtenerHijo(2).getResult()).charAt(0)=='\"'){
                                TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","string","global","main",String.valueOf(raiz.obtenerHijo(2).getResult())));
                            }else{
                                imprimirConsolaLn("Error Semantico");
                            }
                            
                            break;
                        case "caracter":
                            if(String.valueOf(raiz.obtenerHijo(2).getResult()).charAt(0)=='\''){
                                TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","char","global","main",String.valueOf(raiz.obtenerHijo(2).getResult())));
                            }else{
                                imprimirConsolaLn("Error Semantico");
                            }
                            break;
                        case "binario":
                            
                            if( String.valueOf( raiz.obtenerHijo(2).getResult()).equals("1") ){
                                TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","bool","global","main",1));
                            }else if(String.valueOf( raiz.obtenerHijo(2).getResult()).equals("0")){
                                TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","bool","global","main",0));
                            }else{
                                imprimirConsolaLn("Error Semantico");
                            }
                            
                            break;
                        case "doble":
                            TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","double","global","main",Double.valueOf( String.valueOf(raiz.obtenerHijo(2).getResult()))));
                            break;
                        case "entero":
                            
                            
                            double r1 = Double.valueOf(String.valueOf(raiz.obtenerHijo(2).getResult()));
                            int r2 = (int)r1;
                            TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","int","global","main",r2));
                            break;
                    } 
                    
                }catch(Exception e){
                    
                    imprimirConsolaLn("Error Semantico");
                    
                }
            }
           
        }else if(raiz.getLex().equals("varTipo") ){// varTipo -> tipo de variable
            
            raiz.setResult(raiz.obtenerHijo(0).getLex().toLowerCase());
           
        }else if(raiz.getLex().equals("asignacion") ){// asignacion -> id = expLog ;
            
            for(tablaJson elemento: TS){
                if(elemento.getId().equals(String.valueOf(raiz.obtenerHijo(0).getLex().toLowerCase()))){
                    try{
                        switch(elemento.getTipo()){

                            case "string":
                                //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","string","global","main",String.valueOf(raiz.obtenerHijo(2).getResult())));
                                if(String.valueOf(raiz.obtenerHijo(2).getResult()).charAt(0)=='\"'){
                                    //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","string","global","main",String.valueOf(raiz.obtenerHijo(2).getResult())));
                                    elemento.setValor(String.valueOf(raiz.obtenerHijo(2).getResult()));
                                }else{
                                    imprimirConsolaLn("Error Semantico");
                                }
                                
                                break;
                            case "char":
                                //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","char","global","main",String.valueOf(raiz.obtenerHijo(2).getResult())));
                                if(String.valueOf(raiz.obtenerHijo(2).getResult()).charAt(0)=='\''){
                                    //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","string","global","main",String.valueOf(raiz.obtenerHijo(2).getResult())));
                                    elemento.setValor(String.valueOf(raiz.obtenerHijo(2).getResult()));
                                }else{
                                    imprimirConsolaLn("Error Semantico");
                                }
                                
                                break;
                            case "bool":

                                if( String.valueOf( raiz.obtenerHijo(2).getResult()).equals("1") ){
                                    //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","bool","global","main",1));
                                    elemento.setValor("1");
                                }else if(String.valueOf( raiz.obtenerHijo(2).getResult()).equals("0")){
                                    //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","bool","global","main",0));
                                    elemento.setValor("0");
                                }else{
                                    imprimirConsolaLn("Error Semantico, valor no acorde al tipo");
                                }

                                break;
                            case "double":
                                //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","double","global","main",Double.valueOf( String.valueOf(raiz.obtenerHijo(2).getResult()))));
                                elemento.setValor(Double.parseDouble(String.valueOf(raiz.obtenerHijo(2).getResult())));
                                break;
                            case "int":


                                double r1 = Double.valueOf(String.valueOf(raiz.obtenerHijo(2).getResult()));
                                int r2 = (int)r1;
                                //TS.add(new tablaJson(raiz.obtenerHijo(1).getLex().toLowerCase(),"var","int","global","main",r2));
                                elemento.setValor(r2);
                                break;
                        } 

                    }catch(Exception e){

                        imprimirConsolaLn("Error Semantico,valor no acorde al tipo");

                    }
                    
                    return;
                    
                }
            }
            imprimirConsolaLn("Error Semantico, Variable no declarada");
           
        }else if(raiz.getLex().equals("valorId") && raiz.getHijos().size()==1){// valor -> ID
            for(tablaJson elemento: TS){
                if(raiz.obtenerHijo(0).getLex().equalsIgnoreCase(elemento.getId())){
                    
                    raiz.setResult(elemento.getValor());
                    return;
                }
            }
            
            
            imprimirConsolaLn("Error Semantico, Variable no declarada");
            
            
            
            
        }else if(raiz.getLex().equals("sImprimir") ){// sImprimir -> imprimir ( expLog ) ;
            
            
            
            imprimirConsolaLn( String.valueOf( raiz.obtenerHijo(2).getResult()));
        }else if(raiz.getLex().equals("sIf") ){// sIf -> if ( expLog ) { instrucciones sIf2
            
            ejecutarIf(raiz,TS);
            
            
        }
        
        
        
        
        
        
        
    }
    
    public void ejecutarIf(arbol raiz, ArrayList<tablaJson> TS){ // sIf -> if ( expLog ) { instrucciones sIf2
        if(String.valueOf(raiz.obtenerHijo(2).getResult()).equals("1")){
                //ejecutar el if
                
                raiz.obtenerHijo(5).setAct(true);
                run(raiz.obtenerHijo(5),TS);
                raiz.obtenerHijo(5).setAct(false);
            }else if(raiz.obtenerHijo(6).getHijos().size()==3){ // sIf -> if ( expLog ) { instrucciones sIf2 && si sIf2-> } else elsePrima 
                
                if(raiz.obtenerHijo(6).obtenerHijo(2).getHijos().size()==3){ //&&si elsePrima -> { instrucciones }
                    //ejecutar el else
                    raiz.obtenerHijo(6).obtenerHijo(2).obtenerHijo(1).setAct(true);
                    run(raiz.obtenerHijo(6).obtenerHijo(2).obtenerHijo(1),TS);
                    raiz.obtenerHijo(6).obtenerHijo(2).obtenerHijo(1).setAct(false);
                }else{                                                                 // && si elsePrima -> sIf
                    //viene un else if
                    ejecutarIf( raiz.obtenerHijo(6).obtenerHijo(2).obtenerHijo(0),TS);
                    
                }
                
                
        }
        
    }
    
    public double calcularPotencia(double base,int exp){
        double resultado=1;
        
        for(int i=0;i<exp;i++){
            resultado =resultado*base;
        }
            
       return resultado;
    }
    
    public double calcularPotenciaL(double base,double exp){
        double resultado= Math.pow(base, exp);
        
        
       return resultado;
    }
    
    public int negarValor(int i){
        
        if(i==1){
            i=0;
        }else if(i==0){
            i=1;
        }else{
            imprimirConsolaLn("Error Semantico");
        }
        return i;
    }
    
    
    
    public int calcularRelacionales(Double num1, Double num2,String op){
        int resultado=0;
        
        switch(op){
                 
                    case "<":
                        
                        if(num1<num2){
                            resultado=1;
                        }else{
                            resultado=0;
                        }
                        
                        break;
                    case ">":
                        
                        if(num1>num2){
                            resultado=1;
                        }else{
                            resultado=0;
                        }
                        break;
                    case ">=":
                        
                        if(num1>=num2){
                            resultado=1;
                        }else{
                            resultado=0;
                        }
                        break;
                    case "<=":
                        
                        if(num1<=num2){
                            resultado=1;
                        }else{
                            resultado=0;
                        }
                        break;
                    case "==":
                        
                        if(Objects.equals(num1, num2)){
                            resultado=1;
                        }else{
                            resultado=0;
                        }
                        break;
                    case "!=":
                        
                        if(!Objects.equals(num1, num2)){
                            resultado=1;
                        }else{
                            resultado=0;
                        }
                        break;
                    
                }
        
        return resultado;
    }
    
    public void generarReporteErrores(ArrayList<fallos> errores,String nombre) throws IOException {
        FileWriter fichero=null;
        PrintWriter pw;
        try {
            
            String path = nombre + ".html";
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            
            //Comenzamos a escribir el html
            pw.println("<html>");
            pw.println("<head><title>REPORTE DE ERRORES</title></head>");
            pw.println("<body>");
            pw.println("<div align=\"center\">");
            pw.println("<h1>Reporte de Errores</h1>");
            pw.println("<br></br>");
            pw.println("<table border=1>");
            pw.println("<tr>");
            pw.println("<td>Lexema</td>");
            pw.println("<td>DESCRIPCION</td>");
            pw.println("<td>FILA</td>");
            pw.println("<td>COLUMNA</td>");
            pw.println("</tr>");

            for (fallos err : errores) {
                pw.println("<tr>");
                pw.println("<td>" + err.getLexema() + "</td>");
                pw.println("<td>" + err.getDesc() + "</td>");
                pw.println("<td>" + err.getFila() + "</td>");
                pw.println("<td>" + err.getCol() + "</td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("</div");
            pw.println("</body>");
            pw.println("</html>");
            //Desktop.getDesktop().open(new File(path));
            
            
        } catch (Exception e) {
        } finally {
            if (fichero != null) {
                fichero.close();
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
    
    
    
    public void graficarAST(arbol resultado,String nombre) throws IOException{
        
        String grafica= "digraph L{\n ";
        String enlaces=obtenerEnlaces(resultado);
        String nodos=obtenerNodos(resultado);
        
        
        grafica = grafica + nodos +enlaces +"}";
        
        //generar HTML
        
        FileWriter fichero=null;
        PrintWriter pw;
        try {
            
            String path = nombre + ".html";
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            
            //Comenzamos a escribir el html
            pw.println("<html>");
            pw.println("<head><title>AST</title></head>");
            pw.println("<body>");
            pw.println("<div align=\"center\">");
            pw.println("<h1>AST</h1>");
            pw.println("<br></br>");
            pw.print("<img src=\"");
            pw.print("https://quickchart.io/graphviz?graph=");
            pw.print(grafica);
            pw.println("\" >");
            pw.println("</div");
            pw.println("</body>");
            pw.println("</html>");
            //Desktop.getDesktop().open(new File(path));
            
            
        } catch (Exception e) {
        } finally {
            if (fichero != null) {
                fichero.close();
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
    }
    
    public String obtenerNodos(arbol raiz){
        String nodos ="";
        
        for(arbol var: raiz.getHijos()){
            nodos+= obtenerNodos(var);
            
            
        }
        if(raiz.getLex().equals(";")){
            
            nodos += "n"+raiz.getId()+"[label=puntoComa];\n"; 
            
        }else if(raiz.getLex().equals("=")){
            nodos += "n"+raiz.getId()+"[label=igual];\n";
            
        }else if(raiz.getLex().equals("!")){
            nodos += "n"+raiz.getId()+"[label=Not];\n";
            
        }else if(raiz.getLex().equals("&&")){
            nodos += "n"+raiz.getId()+"[label=AND];\n";
            
        }else if(raiz.getLex().equals("||")){
            nodos += "n"+raiz.getId()+"[label=or];\n";
            
        }else if(raiz.getLex().equals(",")){
            nodos += "n"+raiz.getId()+"[label=Coma];\n";
            
        }else if(raiz.getLex().equals("(")){
            nodos += "n"+raiz.getId()+"[label=Par_Izq];\n";
            
        }else if(raiz.getLex().equals(")")){
            nodos += "n"+raiz.getId()+"[label=Par_Der];\n";
            
        }else if(raiz.getLex().equals("[")){
            nodos += "n"+raiz.getId()+"[label=Cor_Izq];\n";
            
        }else if(raiz.getLex().equals("]")){
            nodos += "n"+raiz.getId()+"[label=Cor_Der];\n";
            
        }else if(raiz.getLex().equals(">")){
            nodos += "n"+raiz.getId()+"[label=Mayor];\n";
            
        }else if(raiz.getLex().equals("<")){
            nodos += "n"+raiz.getId()+"[label=Menor];\n";
            
        }else if(raiz.getLex().equals("<=")){
            nodos += "n"+raiz.getId()+"[label=menorIgual];\n";
            
        }else if(raiz.getLex().equals(">=")){
            nodos += "n"+raiz.getId()+"[label=mayorIgual];\n";
            
        }else if(raiz.getLex().equals("==")){
            nodos += "n"+raiz.getId()+"[label=equivalente];\n";
            
        }else if(raiz.getLex().equals("!=")){
            nodos += "n"+raiz.getId()+"[label=distinto];\n";
            
        }else if(raiz.getLex().equals("+")){
            nodos += "n"+raiz.getId()+"[label=Mas];\n";
            
        }else if(raiz.getLex().equals("-")){
            nodos += "n"+raiz.getId()+"[label=Menos];\n";
            
        }else if(raiz.getLex().equals("*")){
            nodos += "n"+raiz.getId()+"[label=Por];\n";
            
        }else if(raiz.getLex().equals("/")){
            nodos += "n"+raiz.getId()+"[label=Div];\n";
            
        }else if(raiz.getLex().equals("^")){
            nodos += "n"+raiz.getId()+"[label=Potencia];\n";
            
        }else if(raiz.getLex().equals("%")){
            nodos += "n"+raiz.getId()+"[label=modulo];\n";
            
        }else if(raiz.getLex().equals("{")){
            nodos += "n"+raiz.getId()+"[label=LLave_Abre];\n";
            
        }else if(raiz.getLex().equals("}")){
            nodos += "n"+raiz.getId()+"[label=LLave_Cierra];\n";
            
        }else if(raiz.getLex().charAt(0)=='\"'){
            //nodos += "n"+raiz.getId()+"[label="+raiz.getLex().substring(1,raiz.getLex().length()-1)+"];\n";
            nodos += "n"+raiz.getId()+"[label=cadena];\n";
        }else if(raiz.getLex().charAt(0)=='\''){
            //nodos += "n"+raiz.getId()+"[label="+raiz.getLex().substring(1,raiz.getLex().length()-1)+"];\n";
            nodos += "n"+raiz.getId()+"[label=Caracter];\n";
        }else{
           nodos += "n"+raiz.getId()+"[label="+raiz.getLex()+"];\n"; 
        }
        
        
        return nodos;
    }
    
    public String obtenerEnlaces(arbol raiz){
        String enlaces ="";
        
        for(arbol var: raiz.getHijos()){
            enlaces+= obtenerEnlaces(var);
            enlaces += "n"+raiz.getId()+" -> "+ "n"+var.getId()+"; \n";
            
        }
        
        return enlaces;
    }
 
    public void generarReporteTokens(ArrayList<elToken> listaTokens,String nombre) throws IOException {
        FileWriter fichero=null;
        PrintWriter pw;
        try {
            
            String path = nombre + ".html";
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            
            //Comenzamos a escribir el html
            pw.println("<html>");
            pw.println("<head><title>REPORTE DE TOKENS</title></head>");
            pw.println("<body>");
            pw.println("<div align=\"center\">");
            pw.println("<h1>REPORTE DE TOKENS</h1>");
            pw.println("<br></br>");
            pw.println("<table border=1>");
            pw.println("<tr>");
            pw.println("<td>Lexema</td>");
            pw.println("<td>Token</td>");
            pw.println("<td>Fila</td>");
            pw.println("<td>Columna</td>");
            pw.println("</tr>");

            for (elToken tok : listaTokens) {
                pw.println("<tr>");
                pw.println("<td>" + tok.getLexema() + "</td>");
                pw.println("<td>" + tok.getToken() + "</td>");
                pw.println("<td>" + tok.getLinea() + "</td>");
                pw.println("<td>" + tok.getCol() + "</td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("</div");
            pw.println("</body>");
            pw.println("</html>");
            //Desktop.getDesktop().open(new File(path));
            
            
        } catch (Exception e) {
        } finally {
            if (fichero != null) {
                fichero.close();
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
   
  
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==jMenuItem1) {
            //System.out.println("Abrir Archivo");
            leerArchivo();
        }
        if (e.getSource()==jMenuItem2) {
            System.out.println("Guardar");
            String txt = jTextArea1.getText();
            if(!this.nombreArchivo.equals("")){
                try {
                    guardarArchivo(txt);
                } catch (IOException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    guardarComo(txt);
                } catch (IOException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        if (e.getSource()==jMenuItem3) {
            System.out.println("Guardar Como");
            String txt = jTextArea1.getText();
            try {
                guardarComo(txt);
            } catch (IOException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if (e.getSource()==jMenuItem4) {
            //System.out.println("Ejecutar");
           
            //System.out.println(analizador);
           
            System.out.println("analizar con JSON");
            String json = jTextArea1.getText();
            System.out.println(json);
            analizarJSON(json);

           
                
            

        }

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jLabel2.setText("Entrada");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel3.setText("Salida");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel2)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(269, 269, 269))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 153));

        jMenu1.setBackground(new java.awt.Color(153, 153, 153));
        jMenu1.setText("Archivo");

        jMenuItem1.setText("Abrir Archivo");
        jMenuItem1.setToolTipText("");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Guardar");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Guardar Como");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(153, 153, 153));
        jMenu2.setText("Analizador");

        jMenuItem4.setText("Ejecutar");
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
