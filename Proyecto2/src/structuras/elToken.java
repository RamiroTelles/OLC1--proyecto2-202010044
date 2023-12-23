/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structuras;

/**
 *
 * @author Rami
 */
public class elToken {
    
    private String lexema="";
    private String token="";
    private int linea=0;
    private int col=0;
    
    public elToken(String lexema, String token, int linea, int col){
        this.lexema = lexema;
        this.token = token;
        this.linea = linea;
        this.col=col;
        
    }

    public String getLexema() {
        return lexema;
    }

    public String getToken() {
        return token;
    }

    public int getLinea() {
        return linea+1;
    }

    public int getCol() {
        return col+1;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    
    
}
