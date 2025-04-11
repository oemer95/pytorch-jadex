// Jadex BDI Agent: PyTorch Integration Example (Java)
// This agent communicates with a Python ML model for decision support

package jadex.examples.pytorchintegration;

import jadex.bridge.service.annotation.*;
import jadex.micro.annotation.*;
import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Agent
@AgentDescription(name = "DecisionAgent", 
    capabilities = {@Capability(name="MainCapability", file="main.cap")})
public class DecisionAgent {

    @Agent
    protected IInternalAccess agent;

    @Belief
    protected double[] features = {0.1, 0.4, 0.6, 0.8};

    @Goal
    public static class MakeDecision {}

    @Plan(trigger = @Trigger(goals = MakeDecision.class))
    public void makeDecisionPlan(MakeDecision goal) {
        try {
            int decision = queryPythonModel(features);
            System.out.println("Received decision from ML model: " + decision);
        } catch (Exception e) {
            System.err.println("Error querying ML model: " + e.getMessage());
        }
    }

    public int queryPythonModel(double[] features) throws IOException {
   
        URL url = new URL("http://localhost:5000/predict");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);

        String jsonInputString = String.format(Locale.US,
            "{\"features\": [%f, %f, %f, %f]}",
            features[0], features[1], features[2], features[3]);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());
        }
    }

    @AgentCreated
    public void init() {
        System.out.println("BDI Agent started. Scheduling decision task.");
        agent.dispatchTopLevelGoal(new MakeDecision());
    }
}
