
package proyecto1;


import com.mycompany.proyecto1.igu.Pantalla;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.ArrayList;

import structuras.arbol;
import structuras.automata;
import structuras.tran;
import java.util.ArrayList;
import structuras.fallos;

public class Proyecto1 {

    public static void main(String[] args) throws IOException {
       Pantalla panta = new Pantalla();
       panta.setVisible(true);
       panta.setLocationRelativeTo(null);
       
       
//       arbol a1 = new arbol("i");
//       arbol a2 = new arbol("n");
//       arbol a3 = new arbol("t");
//       arbol a4 = new arbol(".");
//       arbol a5 = new arbol(".");
//       a4.añadirHijo(a1);
//       a4.añadirHijo(a2);
//       a5.añadirHijo(a4);
//       a5.añadirHijo(a3);
//       
//       a5.imprimirInOrder(a5);

//        automata a1 = new automata();
//        
//        a1.setEstadoInicial("4");
//        a1.addEstadoFinal("9");
//        
//        a1.addTransicion("4", "epsilon", "0");
//        a1.addTransicion("4", "epsilon", "2");
//        
//        a1.addTransicion("0", "a", "1");
//        a1.addTransicion("2", "b", "3");
//        
//        a1.addTransicion("1", "epsilon", "8");
//        a1.addTransicion("3", "epsilon", "8");
//        
//        a1.addTransicion("8", "epsilon", "6");
//        a1.addTransicion("8", "epsilon", "9");
//        
//        a1.addTransicion("6", "c", "7");
//        a1.addTransicion("7", "epsilon", "6");
//        
//        a1.addTransicion("7", "epsilon", "9");
//        
//        System.out.println("------------------");
//        
//        a1.cambiarEstadoFinal("72");
//        
//        System.out.println("nuevo estado final");
        
//        
//        arbol or1 = new arbol("|");
//        or1.añadirHijo(new arbol("a"));
//        or1.añadirHijo(new arbol("b"));
//        
//        arbol cl1 = new arbol("*");
//        cl1.añadirHijo(new arbol("c"));
//        
//        arbol er1 = new arbol(".");
//        er1.añadirHijo(or1);
//        er1.añadirHijo(cl1);
//
//
//        System.out.println("-----------");
//        System.out.println("-----------");
//        
//        automata a1=er1.generarAutomata(er1,0);
//        
//        //generarGrafo(a1,"grafo1");
//        
//        System.out.println("-----------");
//        System.out.println("-----------");

//        arbol cl1 = new arbol("*");
//        cl1.añadirHijo(new arbol("b"));
//        arbol er1 = new arbol("|");
//        
//        er1.añadirHijo(new arbol("a"));
//        er1.añadirHijo(cl1);
//        
//        automata a1 = er1.generarAutomata(er1, 0);
        
//        ArrayList<Integer> l1 = new ArrayList();
//        
//        l1.add(a1.getEstadoInicial());
//        
//        
//        ArrayList<Integer> temp = a1.getCerradura(l1);
//        
//        ArrayList<Integer> temp1 = a1.getMover(temp, "a");
//        
//        ArrayList<Integer> temp2 = a1.getMover(temp, "b");
//        
//        temp = a1.getCerradura(temp2);
//        
//        ArrayList<String> alfabeto = a1.getAlfabeto();

//        automata automataS = a1.metodoSubconjuntos();
//        generarGrafoSubCon(automataS,"simon");
//        System.out.println("-----------");
//        
     
    }
    
//    public static void generarGrafoSubCon(automata auto,String nombre) throws IOException{
//        
//        String grafica= "digraph L{\n ";
//        String enlaces="";
//        String nodoAceptacion="";
//        for(Integer estado: auto.getEstadosFinales()){
//            nodoAceptacion+=String.valueOf(estado)+ "[ shape=doublecircle];\n";
//        }
//        
//        
//        ArrayList<tran> t1 = auto.getTransiciones();
//        
//        for(tran var: t1){
//            
//            if( var.getCarTransicion().substring(0, 1).equals("\"") ){
//                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=" + var.getCarTransicion().substring(1,var.getCarTransicion().length()-1) +" ];\n";
//            }else{
//                enlaces+= var.getEstadoActual() + " -> " + var.getEstadoDestino() + " [label=" + var.getCarTransicion() +" ];\n";
//            }
//            
//        }
//        
//        grafica = grafica + nodoAceptacion +enlaces +"}";
//        
//        //generar HTML
//        
//        FileWriter fichero=null;
//        PrintWriter pw;
//        try {
//            
//            String path = nombre + ".html";
//            fichero = new FileWriter(path);
//            pw = new PrintWriter(fichero);
//            
//            //Comenzamos a escribir el html
//            pw.println("<html>");
//            pw.println("<head><title>AFD con Subconjuntos</title></head>");
//            pw.println("<body>");
//            pw.println("<div align=\"center\">");
//            pw.println("<h1>AFD con Subconjuntos</h1>");
//            pw.println("<br></br>");
//            pw.print("<img src=\"");
//            pw.print("https://quickchart.io/graphviz?graph=");
//            pw.print(grafica);
//            pw.println("\" >");
//            pw.println("</div");
//            pw.println("</body>");
//            pw.println("</html>");
//            Desktop.getDesktop().open(new File(path));
//            
//            
//        } catch (Exception e) {
//        } finally {
//            if (fichero != null) {
//                fichero.close();
//            }
//        }
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        
//        
//    }
    
    
    
}