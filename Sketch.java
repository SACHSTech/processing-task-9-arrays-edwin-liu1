import java.util.ArrayList;

import processing.core.PApplet;

public class Sketch extends PApplet {
	
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  ArrayList<Objects.Snowball>snowballs = new ArrayList<Objects.Snowball>();

  public void settings() {
	// put your size call here
    size(400, 400);
  }

  Snowball ball = new Snowball(); Objects.Snowball ball = (100, 100, 0, 0, 10, 0);
  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  
  public void setup() {
    background(210, 255, 173);
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
	  ball.display();
  }
  
  public void keyPressed() {
      
  }

  public void initalize(){

  }
}