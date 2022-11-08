import jade.core.Agent;

public class ContractAgent extends Agent {
    protected void setup() {
        System.out.println("Contract agent " + getAID().getName());
    }
}
