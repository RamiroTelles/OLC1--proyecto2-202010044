/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structuras;

/**
 *
 * @author Rami
 */
public class tran {
    
    private String estadoActual;
    private String estadoDestino;
    private String carTransicion;
    
    public tran(String estadoActual, String carTransicion, String estadoDestino){
        this.estadoActual = estadoActual;
        this.carTransicion = carTransicion;
        this.estadoDestino = estadoDestino;

        
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public String getCarTransicion() {
        return carTransicion;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public void setCarTransicion(String carTransicion) {
        this.carTransicion = carTransicion;
    }
    
    
    
}
