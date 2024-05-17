import java.util.ArrayList;

import processing.core.PApplet;

public class Sketch extends PApplet {
	
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  ArrayList<Objects.Snowball>snowballs = new ArrayList<Objects.Snowball>();

  public void settings() {
	// put your size call here
    size(1000, 800);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  
  public void setup() {
    background(65);
    snowballs.add(new Objects.Snowball (500,400,0,0,10,0.2) );
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    fill (255); stroke(128);
    for (Objects.Snowball ball : snowballs){
      ball.update();
	    displaySnowball(ball);
    }
  }
  
  public void keyPressed() {
      
  }

  public void mouseClicked(){

  }

  public void initalize(){

  }

  public void displaySnowball(Objects.Snowball snowball){
    circle(snowball.getX(), snowball.getY(), snowball.getR() );
  }

  public void displayWall(Objects.Wall wall){

  }
}