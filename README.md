# PyTorch-Jadex Integration: Intelligent BDI Agent with Python ML Model

This project demonstrates how to integrate a **Jadex BDI agent** with a **Python-based PyTorch model** to enable intelligent decision-making in agent-based systems.

## Project Overview
The agent extracts feature values, sends them to a Python ML model via REST API, and uses the returned decision to act within its environment. This enables hybrid AI combining symbolic (BDI) and sub-symbolic (ML) methods.

---

## Structure
```
├── jadex-bdi-agent/                # Java-based BDI agent (Jadex)
│   └── DecisionAgent.java          # Main agent with PyTorch integration
├── python-ml-server/              # Python REST API with PyTorch model
│   └── app.py                     # Flask server that serves model predictions
└── README.md                      # Project documentation
```

---

## 🚀 Setup Instructions

### ✅ Prerequisites
- Java 11+
- Jadex Platform (v3.0+)
- Python 3.8+
- Flask (`pip install flask`)
- PyTorch (`pip install torch`)

###⚙️ Run Instructions

#### 1. Start the Python Flask Server
Navigate to `python-ml-server/` and run:
```bash
python app.py
```
This will start a server on `http://localhost:5000/predict` that accepts JSON input like:
```json
{
  "features": [0.1, 0.4, 0.6, 0.8]
}
```
And responds with a single integer decision, e.g. `1`.

#### 2. Run the Jadex Agent
Use Jadex or your Java IDE to run the agent defined in `DecisionAgent.java`. The agent will:
- Initialize with default feature values
- Trigger a goal
- Call the Python model
- Print the result

---

## Example Communication
**Request sent from agent:**
```json
{
  "features": [0.1, 0.4, 0.6, 0.8]
}
```
**Response from Python server:**
```json
1
```

---

## 🛠️ Future Extensions
- Add feature extraction from environment
- Multiple agents with distributed decision making
- Secure communication between agent and server (e.g., token auth)
- Use FastAPI instead of Flask for async support

---

## 📚 References
- Jadex BDI Framework: https://www.activecomponents.org
- PyTorch: https://pytorch.org
- Flask: https://flask.palletsprojects.com

