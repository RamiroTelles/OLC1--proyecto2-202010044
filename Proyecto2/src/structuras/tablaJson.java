/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structuras;

/**
 *
 * @author Rami
 */
public class tablaJson {
    
    
    private String id;
    private String rol;
    private String tipo;
    private String entorno;
    private String pertenece;
    private Object valor;
    private arbol instrucciones;
    
    
    public tablaJson( String id,String rol,String tipo,String entorno, String pertenece,  Object valor){
        
        this.id=id;
        this.rol=rol;
        this.tipo=tipo;
        this.entorno=entorno;
        this.pertenece=pertenece;
        
        this.valor=valor;
        
    }
    
    public tablaJson( String id,String rol,String tipo,String entorno, String pertenece,  Object valor,arbol instrucciones){
        
        this.id=id;
        this.rol=rol;
        this.tipo=tipo;
        this.entorno=entorno;
        this.pertenece=pertenece;
        
        this.valor=valor;
        this.instrucciones = instrucciones;
        
    }
    
     public arbol getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(arbol instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getRol() {
        return rol;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEntorno() {
        return entorno;
    }

    public String getPertenece() {
        return pertenece;
    }

    
    
    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEntorno(String entorno) {
        this.entorno = entorno;
    }

    public void setPertenece(String pertenece) {
        this.pertenece = pertenece;
    }

   

    public String getId() {
        return id;
    }

  

    public Object getValor() {
        return valor;
    }

    

    public void setId(String id) {
        this.id = id;
    }

    

    public void setValor(Object valor) {
        this.valor = valor;
    }

    
    
    
    
}
