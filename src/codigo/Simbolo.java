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
    private Object nombre;
    private Object tipo;
    private Object valor;
    
    public Simbolo(Object n,Object t,Object v){
        nombre=n;
        tipo=t;
        valor=v;
    }
    
  public Object getNombre(){
      return nombre;
  }
  
  public Object getTipo(){
      return tipo;
  }
  
  public Object getValor(){
      return valor;
  }
  
  public void setValor(Object v){
      valor=v;
  }
    
    
    
}
