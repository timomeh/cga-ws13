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
        if (update.currentCell().value() == 0) {
            int x = update.currentPosition().x();
            int y = update.currentPosition().y();
            if (x < 1)
            	x = 198;
            if (y < 1)
            	y = 148;
            if (x > 198)
            	x = 1;
            if (y > 148)
            	y = 1;
        	this.spawnChild(new World.Index(x+1, y));
        	this.spawnChild(new World.Index(x-1, y));
        	this.spawnChild(new World.Index(x, y+1));
        	this.spawnChild(new World.Index(x, y-1));
        	return new AgentMessages.ChangeValue(1);
        } else {
            return die();
        }
    }
}

