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
    
    private Object valor;
    
    
    public tablaJson( String id,  Object valor){
        
        this.id=id;
        
        this.valor=valor;
        
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
