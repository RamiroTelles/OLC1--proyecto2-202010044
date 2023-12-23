/*

 */
package structuras;

/**
 *
 * @author Rami
 */
public class fallos {

    private String lexema = "";
    private String desc = "";
    private int fila = 0;
    private int col = 0;

    public fallos(String lexema, String desc, int fila, int col) {
        this.lexema = lexema;
        this.desc = desc;
        this.fila = fila;
        this.col = col;
    }

    public int getCol() {
        return col+1;
    }

   

    public int getFila() {
        return fila+1;
    }

    public String getDesc() {
        return desc;
    }

    public String getLexema() {
        return lexema;
    }
    
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setCol(int col) {
        this.col = col;
    }

}
