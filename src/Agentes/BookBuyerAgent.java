package Agentes;

import jade.core.Agent;
import behaviours.RequestPerformer;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import gui.BookBuyerGui;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookBuyerAgent extends Agent {
  private String bookTitle ="",otra;
  private AID[] sellerAgents;
  private int ticker_timer = 10000;
  private BookBuyerAgent this_agent = this;
  private BookBuyerGui gui;
  
  protected void setup() {
    System.out.println("Buyer agent " + getAID().getName() + " is ready");
    gui = new BookBuyerGui(this);
    gui.showGui();
    
    Object[] args = getArguments();
    while(bookTitle.length() == 0){
        //bookTitle = setTitle();
        if(args != null && args.length > 0) {//args != null && args.length > 0
          bookTitle = (String)args[0];
          System.out.println("Book: " + bookTitle);
          //gui.dispose();
          addBehaviour(new TickerBehaviour(this, ticker_timer) {
            protected void onTick() {
              System.out.println("Trying to buy " + bookTitle);

              DFAgentDescription template = new DFAgentDescription();
              ServiceDescription sd = new ServiceDescription();
              sd.setType("book-selling");
              template.addServices(sd);

              try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                System.out.println("Found the following seller agents:");
                sellerAgents = new AID[result.length];
                for(int i = 0; i < result.length; i++) {
                  sellerAgents[i] = result[i].getName();
                  System.out.println(sellerAgents[i].getName());
                }

              }catch(FIPAException fe) {
                fe.printStackTrace();
                  System.out.println(fe.getMessage());
              }

              myAgent.addBehaviour(new RequestPerformer(this_agent));
            }
          });
        } else {
            //System.out.println("No target book title specified yet");
            //doDelete();
            
        }
    }
  }
  
  protected void takeDown() {
    System.out.println("Buyer agent " + getAID().getName() + " terminating");
    gui.dispose();
  }
  
  public AID[] getSellerAgents() {
    return sellerAgents;
  }
  
  public String getBookTitle() {
    return bookTitle;
  }
  
  public String setTitle(){
    return gui.getTitle();
  }
}
