package Agents;

import jade.core.AID;
import jade.core.Agent;

public class ProjectAgent extends Agent {
    String nickname = "Project";
    AID id;
    protected void setup() {
        id = new AID(nickname, AID.ISLOCALNAME);
        System.out.println("Project agent init " + getAID().getName());
    }

    protected void takeDown() {
        System.out.println("Project agent shutdown");
    }
}
