import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  ArrayList<Objects.Snowball>snowballs = new ArrayList<Objects.Snowball>();
  ArrayList<Objects.Wall>walls = new ArrayList<Objects.Wall>();
  ArrayList<Objects.Wind>winds = new ArrayList<Objects.Wind>();
  
  boolean clicked = false;

  PImage heart;

  float pX = 500, pY = 600, pVelX, pVelY;
  int pSpeed = 3;
  int lives = 3;
  double slow = 100;
  double slowChange = 0.001;

  public void settings() {
    size(1000, 800);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  
  public void setup() {
    heart = loadImage("Heart.png");

    //winds.add(new Objects.Wind(300, 500, 100, 60, 0, 0, -1) );
    snowballs.add(new Objects.Snowball(500,400,0,0,10,0.2) );
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    background(0);

    if (slow <= 0){}

    fill (255); stroke(128);
    for (Objects.Snowball snow : snowballs){
      if (snow.getY() + snow.getR() > height){
        snowballs.remove(snow);
      }
      snow.update();
	    displaySnowball(snow);
    }

    stroke(128);
    for (Objects.Wind wind : winds){
      wind.update();
      displayWind(wind);
    }

    displaySlow();
    displayLife(lives);
  }
  
  public void keyPressed() {
    if ('w' == key){pVelY = -pSpeed;}
    if ('a' == key){pVelX = -pSpeed;}
    if ('s' == key){pVelY = pSpeed;}
    if ('d' == key){pVelX = pSpeed;}
  }

  public void keyReleased(){
    if ('w' == key || 's' == key){pVelY = 0;}
    if ('a' == key || 'd' == key){pVelX = 0;}

    if (keyCode == DOWN){
      for (Objects.Snowball snow : snowballs){snow.speedUp();}
      for (Objects.Wall wall : walls){wall.speedUp();}
      for (Objects.Wind wind : winds){wind.speedUp();}
    }
    if (keyCode == UP){
      for (Objects.Snowball snow : snowballs){snow.slowDown();}
      for (Objects.Wall wall : walls){wall.slowDown();}
      for (Objects.Wind wind : winds){wind.slowDown();}
    }
  }

  public void mousePressed(){
    clicked = true;
    for (Objects.Snowball ball : snowballs){
      if (collision(mouseX, ball.getX(), mouseY, ball.getY(), ball.getR(), 0)){
        ball.hide();
        slow += 1;
      }
    }
  }

  public void mouseReleased(){
    clicked = false;
  }

  public void initalize(){

  }

  // object methods go below here
  

  // display methods go below here
  public void displaySnowball(Objects.Snowball snowball){
    if (!snowball.hidden){circle(snowball.getX(), snowball.getY(), snowball.getR() );}
  }

  public void displayWall(Objects.Wall wall){
    rect(wall.getX() - (wall.getW() / 2), wall.getY() - (wall.getH() / 2), wall.getX() + (wall.getW() / 2), wall.getY() + (wall.getH() / 2) );
  }

  public void displayWind(Objects.Wind wind){
    Objects.Wind.WindLine[] winds = wind.getWindLines();
    for (Objects.Wind.WindLine line : winds){
      float displayX1, displayX2;

      if (wind.getWindSpeed() < 0){
        if (line.getX1() <= wind.getX() - (wind.getW() / 2) ){
          displayX1 = (wind.getX() - (wind.getW() / 2) );
        } else {displayX1 = line.getX1();}
        if (line.getX2() >= wind.getX() + (wind.getW() / 2) ){
          displayX2 = (wind.getX() + (wind.getW() / 2) );
        } else {displayX2 = line.getX2();}
        line(displayX1, line.getY(), displayX2, line.getY() );
      }
      else if (wind.getWindSpeed() > 0){
        if (line.getX1() >= wind.getX() + (wind.getW() / 2) ){
          displayX1 = (wind.getX() + (wind.getW() / 2) );
        } else {displayX1 = line.getX1();}
        if (line.getX2() <= wind.getX() - (wind.getW() / 2) ){
          displayX2 = (wind.getX() - (wind.getW() / 2) );
        } else {displayX2 = line.getX2();}
        line(displayX1, line.getY(), displayX2, line.getY() );
      }
    }
  }

  public void displayPlayer(float playerX, float playerY){
    circle(playerX, playerY, 30);
  }

  // UI elements go below here
  public void displayLife(int lives){
    for (int i = 0; i < lives; i++){
      image(heart, width - 60 - (i * 55), 20);
    }
  }

  public void displaySlow(){
    fill(0); stroke(128);
    rect(825, 80, 165, 20);
    fill(0,0,200); stroke(255);
    rect(827, 82, (float)(161 * (slow/100) ), 16);
  }

  // general math methods go below here
  public boolean collision(float X1, float X2, float Y1, float Y2, float R1, float R2){
    return ( Math.sqrt(Math.pow(Math.abs(Y1 - Y2), 2) + Math.pow(Math.abs(X1 - X2), 2) ) <= R1 + R2);
  }

  public boolean offScreen(float x, float y, float maxSize){
    float size = maxSize * 2;
    return ( (x + size < width || x - size > width)||(y + size < height || y - size > height) );
  }
}