package solutions.ws13.assignment5;

import org.amcgala.agent.AgentMessages;
import org.amcgala.agent.AmcgalaAgent;
import org.amcgala.agent.Directions;
import org.amcgala.agent.Simulation;
import org.amcgala.agent.World;

public class FloydSteinbergAgent extends AmcgalaAgent {
	private int phase = 0;
	private float qerror = 0;
	private float oldPixel = 0;
	private float newPixel = 0;
	private boolean noChild = true;
	
	@Override
	protected AgentMessages.AgentMessage onUpdate(Simulation.SimulationUpdate update) {
		phase++;
		
		switch(phase) {
			case 1:
				// Ueberpruefen, ob die Zelle, auf der man steht, schon besucht wurde. 
				if(checkVisited(update.currentCell())) {
					return die();
				}
				phase++;
				
			case 2:
				// Berechnen der Summe der Quantisierungsfehler und Zuweisen des neuen Wert.	
				oldPixel = update.currentCell().value();
				qerror = oldPixel;
				for (World.InformationObject informationObject : update.currentCell().informationObjects()) {
					if (informationObject instanceof World.QuantisationError) {
						World.QuantisationError e = (World.QuantisationError) informationObject;
						qerror += e.value();
					}
				}
				
				newPixel = Math.round(qerror); // anstatt if < 0.5 = 0 ...
				
				qerror -= newPixel;
				return changeValue(newPixel);
				
			case 3: // Verteilen der Anteile des Quantisierungsfehlers auf die Nachbarzellen, wie vom Floyd-Steinberg-Algorithmus vorgegeben
				// Rechts = 7/16
				World.QuantisationError errorRight = new World.QuantisationError(qerror * 7/16);
				return putInformationObjectTo(this.getNeighourIndex(Directions.RIGHT(), update), errorRight);
				
			case 4:
				// Runter Rechts = 1/16
				World.QuantisationError errorDownRight = new World.QuantisationError(qerror * 1/16);
				return putInformationObjectTo(this.getNeighourIndex(Directions.DOWN_RIGHT(), update), errorDownRight);
			case 5:
				// Runter = 5/16
				World.QuantisationError errorDown = new World.QuantisationError(qerror * 5/16);
				return putInformationObjectTo(this.getNeighourIndex(Directions.DOWN(), update), errorDown);
				
			case 6:
				// Runter Links = 3/16
				World.QuantisationError errorDownLeft = new World.QuantisationError(qerror * 3/16);
				return putInformationObjectTo(this.getNeighourIndex(Directions.DOWN_LEFT(), update), errorDownLeft);
				// Ein neuer Agent fuer die naechste Zeile kann hier noch nicht gespawnt werden
				
			case 7:
				// Markieren der Zelle, dass sie besucht wurde 
				return putVisitedObject(update);
				
			case 8:
				// Zuruecksetzen des Agenten in den Ausgangszustand
				// Ein neuer Agent fuer die naechste Zeile kann hier gespawnt werden
				if(update.currentPosition().x() > 0 && noChild) {
					spawnChild(getNeighourIndex(Directions.DOWN_LEFT(), update));
					noChild = false;
				}
				
				phase = 0;
				return moveTo(Directions.RIGHT(), update);
				
			default:
				// Notfallphase
				return die();
		}
	}
}