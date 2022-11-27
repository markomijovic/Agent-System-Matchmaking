package main.java.Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.java.DB.Contract;
import main.java.DB.Util;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.Set;

public class ContractAgent extends Agent {
    protected final String SERVICE_NAME = "contract-agent-service";
    protected final String CREATE_CONTRACT_ID = "createContract";

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
        addBehaviour(new CreateContractBehaviour(this, 100));
    }

    class CreateContractBehaviour extends TickerBehaviour {
        private ContractAgent contractAgent;

        public CreateContractBehaviour(Agent a, long period) {
            super(a, period);
            contractAgent = (ContractAgent) a;
        }

        @Override
        protected void onTick() {
            MessageTemplate messageTemplate = MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                    MessageTemplate.MatchConversationId(CREATE_CONTRACT_ID)
            );
            ACLMessage message = contractAgent.receive(messageTemplate);
            if (message != null) {
                JSONObject request = new JSONObject(message.getContent());
                String response =Contract.addNewContract(request);
                contractAgent.sendMessage(response,
                        CREATE_CONTRACT_ID,
                        ACLMessage.INFORM,
                        contractAgent.searchForService(Util.UIServiceName));
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
