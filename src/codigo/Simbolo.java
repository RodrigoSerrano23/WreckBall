/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

/**
 *
 * @author ClownToy
 */
public class Simbolo {
    private String nombre;
    private String tipo;
    private String valor;
    
    public Simbolo(String n,String t){
        nombre=n;
        tipo=t;
        valor="";
    }
    
  public String getNombre(){
      return nombre;
  }
  
  public String getTipo(){
      return nombre;
  }
  
  public void setValor(String v){
      valor=v;
  }
    
    
    
}
