package Agents;

import jade.core.Agent;

public class UserAgent extends Agent {
    protected void setup() {
        System.out.println("User agent init " + getAID().getName());
    }
}
