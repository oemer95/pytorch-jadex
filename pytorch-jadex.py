# PyTorch-Jadex Integration (Pytorch side)

# This script simulates the interaction between a neural network in PyTorch
# and a Jadex BDI agent by reading data from an input queue and returning
# a decision as output to the BDI agent.

import torch
import torch.nn as nn
import torch.nn.functional as F
import json
import time
import random

# Define a simple neural network for decision making
class DecisionNet(nn.Module):
    def __init__(self, input_size=4, hidden_size=16, output_size=2):
        super(DecisionNet, self).__init__()
        self.fc1 = nn.Linear(input_size, hidden_size)
        self.fc2 = nn.Linear(hidden_size, output_size)

    def forward(self, x):
        x = F.relu(self.fc1(x))
        x = self.fc2(x)
        return F.softmax(x, dim=-1)

# Simulate reading input from a BDI agent
def simulate_bdi_input():
    return {
        "features": [random.random() for _ in range(4)],
        "agent_id": random.randint(1, 10),
        "timestamp": time.time()
    }

# Process decision and simulate writing it back to the BDI agent
def main():
    model = DecisionNet()
    model.eval() 

    print("Starting simulated interaction with Jadex BDI...")
    for _ in range(5):
        # Simulate receiving input from BDI agent
        input_data = simulate_bdi_input()
        input_tensor = torch.tensor(input_data['features'], dtype=torch.float32)

        # Run the model
        output = model(input_tensor)
        decision = torch.argmax(output).item()

        # Output the decision
        print(f"Agent {input_data['agent_id']} | Features: {input_data['features']} => Decision: {decision}")
        time.sleep(1)

if __name__ == "__main__":
    main()