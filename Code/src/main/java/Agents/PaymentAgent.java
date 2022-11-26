package main.java.Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import main.java.DB.DBLoader;
import java.util.HashSet;
import java.util.Set;

public class PaymentAgent extends Agent {
    protected final DBLoader db = DBLoader.getInstance();
    protected final String SERVICE_NAME = "payment-agent-service";

    protected void setup() {
        System.out.println("User agent " + getAID().getName() + " is ready.");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SERVICE_NAME);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    protected Set<AID> searchForService(String serviceName) {
        Set<AID> agents = new HashSet<>();
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceName);
        dfd.addServices(sd);
        try {
            DFAgentDescription[] services = DFService.search(this, dfd);
            for (DFAgentDescription service : services) {
                agents.add(service.getName());
            }
            return agents;
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    protected void sendMessage(String text, String id, int type, Set<AID> agents) {
        ACLMessage message = new ACLMessage(type);
        message.setContent(text);
        message.setConversationId(id);
        for (AID agent : agents) {
            message.addReceiver(agent);
        }
        send(message);
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
