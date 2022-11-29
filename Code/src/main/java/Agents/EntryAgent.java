package main.java.Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class EntryAgent extends Agent {

    @Override
    protected void setup() {
        createAgent("Organizer_Agent", "main.java.Agents.OrganizerAgent");
        createAgent("User_Agent", "main.java.Agents.UserAgent");
    }

    private void createAgent(String name, String className) {
        AID agentID = new AID(name, AID.ISLOCALNAME);
        AgentContainer controller = getContainerController();
        try {
            AgentController agent = controller.createNewAgent(name, className, null);
            agent.start();
            System.out.println("+++ Created: " + agentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EntryAgent entry = new EntryAgent();
        entry.setup();
    }
}
