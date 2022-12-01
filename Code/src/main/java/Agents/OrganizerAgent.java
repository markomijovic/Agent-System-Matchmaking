package main.java.Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.java.DB.Util;
import main.java.GUI.LandingPage;
import org.json.JSONObject;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

public class OrganizerAgent extends Agent {
    protected final String SERVICE_NAME = Util.UIServiceName;

    protected void setup() {
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
        new LandingPage(this);
    }

    public JSONObject loginUser(String username, String password) {
        JSONObject request = new JSONObject();
        request.put("username", username);
        request.put("password", password);
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.or(
                        MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchPerformative(ACLMessage.REFUSE)
                ),
                MessageTemplate.MatchConversationId(Util.USER_LOGIN_ID)
        );
        ACLMessage response = sendAgentCall(request, Util.USER_LOGIN_ID, Util.USER_SERVICE_NAME, template);
        if (response.getPerformative() == ACLMessage.INFORM) {
            JSONObject user = new JSONObject(response.getContent());
            return user;
        }
        return null;
    }

    public String registerUser(String username, String fName, String lName, String password,
                                  String type, String email, String link, double rate, boolean isVerified) {
        JSONObject request = new JSONObject();
        request.put("username", username);
        request.put("password", password);
        request.put("firstName", fName);
        request.put("lastName", lName);
        request.put("type", type);
        request.put("hourlyRate", rate);
        request.put("portfolio", link);
        request.put("isVerified", isVerified);
        request.put("paymentEmail", email);
        ACLMessage response = sendAgentCall(request, Util.USER_SIGNUP_ID,
                Util.USER_SERVICE_NAME, inferMessageInformTemplate(Util.USER_SIGNUP_ID));
        return response.getContent();
    }

    private ACLMessage sendAgentCall(JSONObject request, String behaviourId, String serviceName, MessageTemplate template) {
        StringWriter out = new StringWriter();
        request.write(out);
        this.sendMessage(out.toString(), behaviourId, ACLMessage.REQUEST, searchForService(serviceName));
        return blockingReceive(template);
    }

    private MessageTemplate inferMessageInformTemplate(String behaviourId) {
        return MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchConversationId(behaviourId)
        );
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
