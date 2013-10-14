package solutions.ws13.assignment1;

import akka.actor.Actor;
import akka.japi.Creator;

import org.amcgala.RGBColor;
import org.amcgala.Scene;
import org.amcgala.agent.Agent;
import org.amcgala.agent.Agent.AgentID;
import org.amcgala.agent.Agent.AgentState;
import org.amcgala.agent.StateLoggerAgent;
import org.amcgala.agent.World;
import org.amcgala.agent.World.Cell;
import org.amcgala.agent.World.Index;
import org.amcgala.math.Vertex3f;
import org.amcgala.shape.Rectangle;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 */
public final class SimpleStateLogger extends StateLoggerAgent {
    Scene scene = new Scene("SimpleStateLogger");
    Rectangle[][] rectangles;


    @Override
    public void onInit() {
        rectangles = new Rectangle[worldWidth][worldHeight];

        for (int x = 0; x < rectangles.length; x++) {
            for (int y = 0; y < rectangles[0].length; y++) {
                rectangles[x][y] = new Rectangle(new Vertex3f(x * scaleX, y * scaleY, -1), (float) scaleX, (float) scaleY);
                rectangles[x][y].setColor(RGBColor.GREEN);
                scene.addShape(rectangles[x][y]);
            }
        }


        framework.addScene(scene);
    }

    @Override
    public void onUpdate(Map<World.Index, World.Cell> cells, Map<Agent.AgentID, Agent.AgentState> agents) {
    	for (Entry<Index, Cell> entry : cells.entrySet()) {
    		Index i = entry.getKey();
    		Cell cell = entry.getValue();
    		float v = 1 - cell.value();
    		RGBColor color = new RGBColor(v,v,v);
    		rectangles[i.x()][i.y()].setColor(color);
    	}
    	
    	for (Entry<AgentID, AgentState> agent : agents.entrySet()) {
    		AgentState a = agent.getValue();
    		Index i = a.position();
    		rectangles[i.x()][i.y()].setColor(RGBColor.RED);
    	}
    }	

    static class StateLoggerCreator implements Creator<Actor> {
        @Override
        public Actor create() throws Exception {
            return new SimpleStateLogger();
        }
    }
}
