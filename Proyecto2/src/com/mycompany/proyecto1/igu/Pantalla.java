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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.general.DefaultPieDataset;
import structuras.arbol;
import structuras.automata;
import structuras.tablaJson;
import structuras.elToken;
import structuras.fallos;
import structuras.tran;


/**
 *
 * @author Rami
 */
public class Pantalla extends javax.swing.JFrame implements ActionListener{

    /**
     * Creates new form Pantalla
     */

   
    private String nombreArchivo ="";
    private int cantTabJson=0;

   
    public Pantalla() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jMenuItem1.addActionListener(this);
        jMenuItem2.addActionListener(this);
        jMenuItem3.addActionListener(this);
        jMenuItem4.addActionListener(this);
        
        
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
        
        try{
            scanner scan1 = new scanner(new java.io.StringReader(txt));

            parser parser1 = new parser(scan1);
            parser1.parse();
            System.out.println("Analisis realizado correctamente");
            ArrayList<arbol> arbolesER = parser1.getArboles();
            System.out.println("------------------------------------");
            int i=0;
            for(arbol hijo: arbolesER){
                hijo.imprimirInOrder(hijo);
                System.out.println("------------------------------------");
                automata a1 = hijo.generarAutomata(hijo, 0);
                String nombre = "grafo" + String.valueOf(i);
                generarGrafo(a1,nombre);
                automata automataS = a1.metodoSubconjuntos();
                i++;
                nombre = "grafo"+String.valueOf(i);
                generarGrafoSubCon(automataS,nombre);
                i++;
                
            }
            
            ArrayList<tablaJson> tablaS = parser1.getTablaS();
            System.out.println("------------------------------------");
            for(tablaJson var: tablaS){
                System.out.println("id: "+ var.getId()+ " valor: "+ var.getValor());
                System.out.println("------------------------------------");
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
            Desktop.getDesktop().open(new File(path));
            
            
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
    
    public void generarGrafoSubCon(automata auto,String nombre) throws IOException{
        
        String grafica= "digraph L{\n ";
        String enlaces="";
        String nodoAceptacion="";
        for(Integer estado: auto.getEstadosFinales()){
            nodoAceptacion+=String.valueOf(estado)+ "[ shape=doublecircle];\n";
        }
        
        
        ArrayList<tran> t1 = auto.getTransiciones();
        
        for(tran var: t1){
            if(var.getCarTransicion().equals("\".\"")){
                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=punto ];\n";
                continue;
            }
            
            if( var.getCarTransicion().substring(0, 1).equals("\"") ){
                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=" + var.getCarTransicion().substring(1,var.getCarTransicion().length()-1) +" ];\n";
            }else{
                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=" + var.getCarTransicion() +" ];\n";
            }
            
        }
        
        grafica = grafica + nodoAceptacion +enlaces +"}";
        
        //generar HTML
        
        FileWriter fichero=null;
        PrintWriter pw;
        try {
            
            String path = nombre + ".html";
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            
            //Comenzamos a escribir el html
            pw.println("<html>");
            pw.println("<head><title>AFD con Subconjuntos</title></head>");
            pw.println("<body>");
            pw.println("<div align=\"center\">");
            pw.println("<h1>AFD con Subconjuntos</h1>");
            pw.println("<br></br>");
            pw.print("<img src=\"");
            pw.print("https://quickchart.io/graphviz?graph=");
            pw.print(grafica);
            pw.println("\" >");
            pw.println("</div");
            pw.println("</body>");
            pw.println("</html>");
            Desktop.getDesktop().open(new File(path));
            
            
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
    
    
    
    
    public void generarGrafo(automata auto,String nombre) throws IOException{
        
        String grafica= "digraph L{\n ";
        String enlaces="";
        String nodoAceptacion=String.valueOf(auto.getLastEstadoFinal())+ "[ shape=doublecircle];\n";
        
        ArrayList<tran> t1 = auto.getTransiciones();
        
        for(tran var: t1){
            
            if(var.getCarTransicion().equals("\".\"")){
                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=punto ];\n";
                continue;
            }
            
            if( var.getCarTransicion().substring(0, 1).equals("\"") ){
                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=" + var.getCarTransicion().substring(1,var.getCarTransicion().length()-1) +" ];\n";
            }else{
                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=" + var.getCarTransicion() +" ];\n";
            }
            
        }
        
        grafica = grafica + nodoAceptacion +enlaces +"}";
        
        //generar HTML
        
        FileWriter fichero=null;
        PrintWriter pw;
        try {
            
            String path = nombre + ".html";
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            
            //Comenzamos a escribir el html
            pw.println("<html>");
            pw.println("<head><title>AFN por Thompson</title></head>");
            pw.println("<body>");
            pw.println("<div align=\"center\">");
            pw.println("<h1>AFN por Thompson</h1>");
            pw.println("<br></br>");
            pw.print("<img src=\"");
            pw.print("https://quickchart.io/graphviz?graph=");
            pw.print(grafica);
            pw.println("\" >");
            pw.println("</div");
            pw.println("</body>");
            pw.println("</html>");
            Desktop.getDesktop().open(new File(path));
            
            
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
            Desktop.getDesktop().open(new File(path));
            
            
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
        jPanel2 = new javax.swing.JPanel();
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 716, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 509, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(259, 259, 259)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel2)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)))
                .addContainerGap(32, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
