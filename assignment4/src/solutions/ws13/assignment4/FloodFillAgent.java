package solutions.ws13.assignment4;

import org.amcgala.agent.AgentMessages;
import org.amcgala.agent.AmcgalaAgent;
import org.amcgala.agent.Simulation;
import org.amcgala.agent.World;

/**
* Fuellt ein Polygon mit einem Flood Fill Algorithmus.
*/
public class FloodFillAgent extends AmcgalaAgent {


    @Override
    protected AgentMessages.AgentMessage onUpdate(Simulation.SimulationUpdate update) {
        if (update.currentState().cell().value() == 0) {
            // TODO Flood-Fill Operation implementieren.
        } else {
            return die();
        }
    }
}

