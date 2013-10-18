package solutions.ws13.assignment2;

import java.util.Random;
import org.amcgala.TurtleMode;

public class Main extends TurtleMode{
	public int house_width;
	public int house_floors;
	public int house_windows;
	public int n_trees;
	
	@Override public void turtleCommands() {
		house_width = 100;
		house_floors = 3;
		house_windows = 6;
		n_trees = 3;
		horizon(); // Schaut danach nach rechts
		trees(n_trees); // Schaut danach nach rechts
		house(house_width, house_floors, house_windows);
	}

	public static void main(String[] args) {	
		new Main();
	}
	
	public void house(int width, int floors, int windows) {
		int win_padding = 5;
		int floor_height = 50;
		int win_floor = windows/floors;
		int win_width = (width - ((win_floor*2))*win_padding)/win_floor;
		int roof_height = (int) (floor_height * floors * 0.75);
		double roof_side = Math.sqrt(Math.pow(width/2, 2) + Math.pow(roof_height, 2));
		int roof_side_int = (int) roof_side;
		int angle_side = new Double(Math.toDegrees(Math.asin(roof_height / roof_side))).intValue();
		int angle_middle = (180 - 90 - angle_side)*2;
		move(400);
		down();
		
		move(width); // House rectangle
		turnLeft(90);
		move(floors*floor_height);
		turnLeft(90);
		move(width);
		turnLeft(90);
		move(floors*floor_height);
		turnLeft(90);
		
		for (int i = 0; i < floors; i++) {
			up();
			turnLeft(90);
			move(win_padding);
			turnRight(90);
			move(win_padding);
			if (i+1 == floors && win_floor * floors < windows) {
				win_floor = win_floor + windows - win_floor * floors;
				win_width = (width - ((win_floor*2))*win_padding)/win_floor;
			}
			for (int k = 0; k < win_floor; k++) {
				down();
				move(win_width);
				turnLeft(90);
				move(floor_height - (2*win_padding));
				turnLeft(90);
				move(win_width);
				turnLeft(90);
				move(floor_height - (2*win_padding));
				turnLeft(90);
				up();
				move(win_width + 2*win_padding);
			}
			turnLeft(180);
			move(win_width*win_floor + (win_floor*2+1)*5);
			turnRight(90);
			move(floor_height - win_padding);
			turnRight(90);			
		}
		turnLeft(angle_side);
		down();
		move(roof_side_int);
		turnRight(180 - angle_middle);
		move(roof_side_int);
	}
	
	public void trees(int n) {
		up();
		turnRight(90);
		move(100);
		turnLeft(90);
		move(50);
		Random r = new Random();
		int howLeftAmI = 0;
		
		for(int i = 0; i < n; i++) {
			int size = (int) Math.ceil(r.nextInt(3)) + 1;
			tree(size);
			howLeftAmI += (113/size)/2 + 11 + (113/size);
		}

		turnLeft(180); // To left border
		move(howLeftAmI + 50);
		turnLeft(90);
		move(200);
		turnLeft(90);
	}
	
	public void tree(int size) {
		int diameter = 113/size;
		turnLeft(90);
		down();
		// Draw circle
		for(int k = 0; k <= 360/size; k++) {
			move(1);
			turnRight(size);
		}
		up();
		turnLeft(size);
		turnRight(90);
		move(diameter/2 - 5);
		turnRight(90);
		move(diameter/2 + 1);
		down();
		move(50/size);
		turnRight(70);
		move(10/size);
		up();
		turnLeft(180);
		move(10/size);
		turnLeft(70);
		move(50/size);
		turnRight(90);
		move(10);
		turnRight(90);
		down();
		move(50/size);
		turnLeft(70);
		move(10/size);
		turnLeft(20);
		up();
		move(diameter);
		turnLeft(90);
		move(diameter);
		turnRight(90);
	}
	
	public void horizon() {
		up();
		turnRight(90);
		move(300);
		turnLeft(160); // Berg hoch
		down();
		move(250);
		
		turnRight(100); // 1. Zipfel hinten
		move(32);
		turnRight(180);
		up();
		move(32);
		down();
		
		turnLeft(140); // 1. Zipfel runter
		move(50);
		
		turnLeft(140); // 2. Zipfel hoch
		move(80);
		
		turnRight(140); // 2. Zipfel vorne
		move(60);
		turnRight(60);
		move(20);
		turnLeft(30);
		move(60);
		up();
		turnLeft(180);
		move(60);
		turnRight(30);
		move(20);
		turnLeft(60);
		move(60);
		
		turnRight(130); // 2. Zipfel runter
		down();
		move(80);
		turnRight(30);
		move(40);
		
		turnLeft(80); // 3. Zipfel hoch
		move(30);
		
		turnLeft(155); // 3. Zipfel hinten
		move(35);
		up();
		turnLeft(180);
		move(35);
		
		turnRight(70); // Berg runter
		down();
		move(130);
		turnLeft(10);
		move(80);
		turnLeft(30);
		move(100);
		
		turnLeft(25); // Ebene
		move(200);
		turnLeft(8);
		move(160);
		
		turnLeft(172); // To left border
		up();
		move(807);
		turnRight(180);
		down();
	}

}