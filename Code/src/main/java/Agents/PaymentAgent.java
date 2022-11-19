package main.java.Agents;

import jade.core.Agent;

public class PaymentAgent extends Agent {
    protected void setup() {
        System.out.println("Payment agent init " + getAID().getName());
    }
}
