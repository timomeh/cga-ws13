package solutions.ws13.assignment6;

import org.amcgala.agent.AgentMessages;
import org.amcgala.agent.AmcgalaAgent;
import org.amcgala.agent.Simulation;
import org.amcgala.agent.World.JCellWithIndex;

public class RaindropAgent extends AmcgalaAgent {
   
	private int phase;
	private int maxCells;
	private int visitedCells;
	private float leveling;
	private float current;
	
	private JCellWithIndex max;


	public RaindropAgent(){
		this.phase = 0;
		this.maxCells = 10;
		this.visitedCells = 0;
		this.leveling = 0;
		this.current = 0;	
	}

    @Override
    protected AgentMessages.AgentMessage onUpdate(Simulation.SimulationUpdate update) {
    	phase++;
    	switch(phase){
    		case 1:
	    		current = update.currentCell().value();
	    		current += leveling;    		
	    		return changeValue(current);
    		
    		case 2:
    			this.max = update.neighbours().entrySet().iterator().next().getValue();
	    		for(JCellWithIndex neighbour : update.neighbours().values()) {
	    			 float diff = 0;
	    			 diff = Math.abs(current - neighbour.cell().value()); 
	    			 
	    			 if(diff > Math.abs(current - this.max.cell().value()))
	                     this.max = neighbour;  
	            }
	    		leveling = 0.4f * Math.abs(current - this.max.cell().value());
	    		current -= leveling;
	    		return changeValue(current);  
 		
    		case 3:
    			if(visitedCells <= maxCells) {
    				visitedCells++;  
    				phase = 0;
    				return moveTo(this.max.index());    
    			}    			
    			else
    				return die();
    		
    		default:
    			return die();
    	}
    }
}