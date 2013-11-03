package solutions.ws13.assignment3.lsystem;

import org.amcgala.Turtle;
import org.amcgala.TurtleState;
import org.amcgala.math.Vector3d;
import org.amcgala.shape.util.CompositeShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

import static java.lang.Math.*;

/**
* Ein Lindenmayer-System, das folgende Zeichnen interpretieren kann:
* - + : Rechtsdrehung
* - - : Linksdrehung
* - [ : Push
* - ] : Pop
*
* @author Robert Giacinto
* @since 2.0
*/
public class LindenmayerSystem {
    private Logger log = LoggerFactory.getLogger(LindenmayerSystem.class);

    private Rules rules;
    private Level level;
    private Stack<Turtle> turtles;
    private Turtle turtle;
    private CompositeShape shape;
    private Length length;
    private Angle angle;
    private Axiom axiom;


    /**
     * Erstellt ein neues L-System.
     *
     * @param axiom  das Startsymbol des L-Systems
     * @param rules  die Ersetzungsregeln, die auf das Axiom angewendet werden
     * @param level  die Rekursionstiefe
     * @param length die Schrittweite
     * @param angle  der Winkel, um den gedreht wird
     * @param shape  das Shape, das fuer die Darstellung des L-Systems verwendet werden soll
     */
    public LindenmayerSystem(Axiom axiom, Rules rules, Level level, Length length, Angle angle, CompositeShape shape) {
        this.axiom = axiom;
        this.rules = rules;
        this.level = level;
        this.length = length;
        this.angle = angle;
        this.shape = shape;
        turtles = new Stack<>();
        turtle = new Turtle(shape);
    }

    /**
     * Erstellt ein neues L-System.
     *
     * @param axiom         das Startsymbol des L-Systems
     * @param rules         die Ersetzungsregeln, die auf das Axiom angewendet werden
     * @param level         die Rekusionstiefe
     * @param length        die Schrittweite
     * @param angle         der Winkel, um den gedreht wird
     * @param shape         das Shape, das fuer die Darstellung des L-Systems verwendet werden soll
     * @param startPosition die Startposition der Turtle
     */
    public LindenmayerSystem(Axiom axiom, Rules rules, Level level, Length length, Angle angle, CompositeShape shape, Vector3d startPosition, float startHeading) {
        this(axiom, rules, level, length, angle, shape);
        Vector3d heading = new Vector3d(cos(toRadians(startHeading)), -sin(toRadians(startHeading)), 0).normalize();
        turtle = new Turtle(new TurtleState(startHeading, heading, startPosition), shape);
    }


    /**
     * Wendet die Regeln des L-Systems auf das Startsymbol an.
     */
    public void run() {
        String current = applyRules();

        for (char c : current.toCharArray()) {
            switch (c) {
                case '+':
                    log.debug("Right Turn: {}", angle.value);
                    turtle.turnRight(angle.value);
                    break;
                case '[':
                    log.debug("Push Turtle to stack");
                    Turtle t = new Turtle(turtle.getTurtleState(), shape);
                    turtles.push(t);
                    break;
                case '-':
                	log.debug("Left Turn: {}", angle.value);
                	turtle.turnLeft(angle.value);
                	break;
                case 'm':
                	log.debug("Move without drawing: {}", length.value);
                	turtle.up();
                	turtle.move(length.value);
                	break;
                case 'M':
                	log.debug("Move with drawing: {}", length.value);
                	turtle.down();
                	turtle.move(length.value);
                	break;
                case ']':
                	if(!turtles.isEmpty()) {
                		log.debug("Pop Turtle from stack");
                		turtle = new Turtle(turtles.pop().getTurtleState(), shape);
                	}

                /*
                 * - => Turtle dreht sich links
                 *
                 * m => Turtle bewegt sich ohne eine Linie zu zeichnen
                 *
                 * M => Turtle bewegt sich und zeichnet eine Linie
                 *
                 * [ => Aktuelle Turtle wird kopiert und auf den Stack gelegt. Verwenden Sie hierzu den Stack turtles.
                 *
                 * ] => Alte Turtle wird vom Stack geholt.
                 */
            }
        }

    }

    /**
     * Anwenden der Ersetzungsregeln auf das Axiom.
     */
    private String applyRules() {
        String current = axiom.getAxiom();

        for (int i = 1; i < level.level; i++) {
            String temp = "";
            for (char c : current.toCharArray()) {
                temp += rules.applyReplacementRules(Character.toString(c));
            }
            current = temp;
        }

        String temp = "";
        for (char c : current.toCharArray()) {
            temp += rules.applyDrawingRules(Character.toString(c));
        }

        current = temp;
        return current;
    }
}
