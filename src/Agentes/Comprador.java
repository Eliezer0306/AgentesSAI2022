/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Agentes;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
/**
 *
 * @author eliez
 */
public class Comprador extends Agent{
    private String titulo;
    @Override
    protected void setup(){
        System.out.println("Hola soy el agente comprador: "+ getAID().getName());
        Object[] args = getArguments();
        if(args != null && args.length > 0){
            titulo = (String)args[0];
            System.out.println("Vamos a intentar comprar el libro " + titulo);
            addBehaviour(new TickerBehaviour(this, 10000){
                @Override
                protected void onTick() {
                    System.out.println("Enviando petición a posibles vendedores");
                }
                
            });
        }
        else{
            System.out.println("No se ha especificado el nombre del libro");
            doDelete();
        }
    }
    protected void takeDown(){
        System.out.println("Finalizando al agente comprador " + getAID().getName());
    }
}
