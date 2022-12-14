package main.java.Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.java.DB.User;
import main.java.DB.Util;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.Set;

public class UserAgent extends Agent {
    protected final String SERVICE_NAME = Util.USER_SERVICE_NAME;
    protected final String SIGNUP_ID = Util.USER_SIGNUP_ID;
    protected final String LOGIN_ID = Util.USER_LOGIN_ID;
    protected final String GET_ALL_USERS_ID = Util.GET_ALL_USERS_ID;
    protected final String GET_USER_RATE_ID = Util.GET_USER_RATE_ID;

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
        addBehaviour(new SignUpBehaviour(this, 100));
        addBehaviour(new LoginBehaviour(this, 100));
        addBehaviour(new GetAllUsersBehaviour(this, 100));
        addBehaviour(new GetUserRateBehaviour(this, 100));
    }

    class SignUpBehaviour extends TickerBehaviour {
        private UserAgent userAgent;
        public SignUpBehaviour(Agent a, long period) {
            super(a, period);
            userAgent = (UserAgent) a;
        }

        @Override
        protected void onTick() {
            MessageTemplate messageTemplate = MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                    MessageTemplate.MatchConversationId(SIGNUP_ID)
            );
            ACLMessage message = userAgent.receive(messageTemplate);
            if (message != null) {
                JSONObject request = new JSONObject(message.getContent());
                String response = "";
                if (request.getString("type").equals("Provider")) {
                    response = User.registerProvider(request);
                } else {
                    response = User.registerClient(request);
                }
                userAgent.sendMessage(response, SIGNUP_ID, ACLMessage.INFORM, userAgent.searchForService(Util.UIServiceName));
            }
        }
    }

    class LoginBehaviour extends TickerBehaviour {
        private UserAgent userAgent;
        public LoginBehaviour(Agent a, long period) {
            super(a, period);
            userAgent = (UserAgent) a;
        }

        @Override
        protected void onTick() {
            MessageTemplate messageTemplate = MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                    MessageTemplate.MatchConversationId(LOGIN_ID)
            );
            ACLMessage message = userAgent.receive(messageTemplate);
            if (message != null) {
                JSONObject request = new JSONObject(message.getContent());
                JSONObject response = User.loginUser(request);
                if (response != null) {
                    userAgent.sendMessage(response.toString(), LOGIN_ID, ACLMessage.INFORM, userAgent.searchForService(Util.UIServiceName));
                } else {
                    userAgent.sendMessage("Login Error", LOGIN_ID, ACLMessage.REFUSE, userAgent.searchForService(Util.UIServiceName));
                }
            }
        }
    }

    class GetAllUsersBehaviour extends TickerBehaviour {
        private UserAgent userAgent;

        public GetAllUsersBehaviour(Agent a, long period) {
            super(a, period);
            this.userAgent = (UserAgent) a;
        }

        @Override
        protected void onTick() {
            MessageTemplate messageTemplate = MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                    MessageTemplate.MatchConversationId(GET_ALL_USERS_ID)
            );
            ACLMessage message = userAgent.receive(messageTemplate);
            if (message != null) {
                JSONObject request = new JSONObject(message.getContent());
                JSONArray response = User.getAllUsers(request.getString("userType"));
                if (response != null) {
                    userAgent.sendMessage(response.toString(), GET_ALL_USERS_ID,
                            ACLMessage.INFORM, userAgent.searchForService(Util.UIServiceName));
                }
            }
        }
    }

    class GetUserRateBehaviour extends TickerBehaviour {
        private UserAgent userAgent;

        public GetUserRateBehaviour(Agent a, long period) {
            super(a, period);
            this.userAgent = (UserAgent) a;
        }

        @Override
        protected void onTick() {
            MessageTemplate messageTemplate = MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                    MessageTemplate.MatchConversationId(GET_USER_RATE_ID)
            );
            ACLMessage message = userAgent.receive(messageTemplate);
            if (message != null) {
                JSONObject request = new JSONObject(message.getContent());
                double rate = User.getUserRate(request.getString("username"));
                if (rate != -1) {
                    userAgent.sendMessage(String.valueOf(rate), GET_USER_RATE_ID,
                            ACLMessage.INFORM, userAgent.searchForService(Util.UIServiceName));
                }
            }
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
