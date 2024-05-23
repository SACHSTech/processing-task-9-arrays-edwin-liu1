/*
 * bugs:
 * the time slow thing. when new snowballs spawn they are not affected by the mechanic however are affected by new changes.
 * displaying wind don't work.
 */

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
  
  boolean clicked = false; // have you released the mouse button yet?

  PImage heart;

  long lastDamaged = -10000; // starts at -10000 to avoid starting i-frames
  long lastspawn = 0;
  float nextspawn = 1000; // starts at 1000 to give some time before things start spawning

  // player variables
  float pX = 500, pY = 600, pVelX = 0, pVelY = 0;
  int pSpeed = 3;

  int lives = 3; // amount of lives you have

  // time slow down mechanic variables (no idea what to call it)
  double slow = 100;
  double slowChange = 0.1;
  int slowMulti = 1;

  public void settings() {
    size(1000, 800);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  
  public void setup() {
    heart = loadImage("Heart.png");

    // test objects
    //winds.add(new Objects.Wind(300, 500, 100, 60, 0, 0, -1) );
    //snowballs.add(new Objects.Snowball(200,100,0,(float)0.4,10,0.2) );
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    if (lives > 0){
    background(0);

    if (slow <= 0){}

    updatePlayer();
    displayPlayer();

    fill (255); stroke(128);
    for (int i = 0; i < snowballs.size(); i++){
      if (offScreen(snowballs.get(i).getX(), snowballs.get(i).getY(), snowballs.get(i).getD() ) ){snowballs.remove(i);}
      snowballs.get(i).update();
	    displaySnowball(snowballs.get(i) );
    }

    /* not used yet so this doesn't need to be here and can be ignored
    stroke(128);
    for (Objects.Wind wind : winds){
      wind.update();
      displayWind(wind);
    }
    */

    // spawns new snowballs and updates and displays the UI
    updateSpawn();
    updateSlow();
    displaySlow();
    displayLife(lives);
    } else { // when you die
      // clears the arrays
      snowballs.clear();
      winds.clear();
      walls.clear();
      background(255);
    }
  }
  
  // i tried to normalize the vectors but it didn't work out well. i'll try again later.
  public void keyPressed() {
    if ('a' == key){pVelX = -pSpeed;}
    if ('d' == key){pVelX = pSpeed;}
    if ('w' == key){pVelY = -pSpeed;}
    if ('s' == key){pVelY = pSpeed;}
  }

  public void keyReleased(){
    if ('w' == key || 's' == key){pVelY = 0;}
    if ('a' == key || 'd' == key){pVelX = 0;}

    // speeds up
    if (keyCode == DOWN){
      if (-1 == slowMulti){slowMulti = 1;}
      else if (slowMulti < 1){slowMulti /= 2;}
      else {slowMulti *= 2;}
      for (Objects.Snowball snow : snowballs){snow.speedUp();}
      for (Objects.Wall wall : walls){wall.speedUp();}
      for (Objects.Wind wind : winds){wind.speedUp();}
    }
    // slows down
    if (keyCode == UP){
      if (1 == slowMulti){slowMulti = -1;}
      else if (slowMulti > 1){slowMulti /= 2;}
      else {slowMulti *= 2;}
      for (Objects.Snowball snow : snowballs){snow.slowDown();}
      for (Objects.Wall wall : walls){wall.slowDown();}
      for (Objects.Wind wind : winds){wind.slowDown();}
    }
  }

  /**
   * have you clicked on a snowball?
   */
  public void mousePressed(){
    clicked = true;
    for (Objects.Snowball snow : snowballs){
      if (collision(mouseX, snow.getX(), mouseY, snow.getY(), (snow.getD() / 2), 0) && !snow.hidden){
        snow.hide();
        if (slow < 100){
          slow += 1;
        }
      }
    }
  }

  /**
   * literally just detects if the mouse is released
   */
  public void mouseReleased(){
    clicked = false; // literally just this.
  }

  // object methods go below here

  /**
   * spawns a snowball. i don't want to deal with math equasions right now so it doesn't change with time
   * 
   * it just makes a random snowball and puts it in the arraylist
   */
  public void spawnSnowball(){
    float startX = random(0, width);
    int startSize = (int)random(5, 10);
    float startY = -startSize / 2;
    float startVelX = 0;
    //if (millis() > 60 * 1000){}
    float startVelY = random( (float)0.3, 3);
    double growth = (double)random(0, (float)0.2);
    snowballs.add(new Objects.Snowball(startX, startY, startVelX, startVelY,startSize , growth) );
  }

  /**
   * spawns new snowballs
   * can be used to spawn more but...
   */
  public void updateSpawn(){
    if (millis() >= (lastspawn + nextspawn) ){
      spawnSnowball();
      nextspawn = random(10, 500);
      lastspawn = millis();
    }
  }

  // display methods go below here

  /**
   * just displays the select snowball object
   * 
   * @param snowball the select snowball
   */
  public void displaySnowball(Objects.Snowball snowball){
    if (!snowball.hidden){circle(snowball.getX(), snowball.getY(), snowball.getD() );}
  }

  /**
   * not used yet
   * displays the select wall object
   * 
   * @param wall the select wall
   */
  public void displayWall(Objects.Wall wall){
    rect(wall.getX() - (wall.getW() / 2), wall.getY() - (wall.getH() / 2), wall.getX() + (wall.getW() / 2), wall.getY() + (wall.getH() / 2) );
  }

  /**
   * not used yet and also broken
   * displays the select wind object
   * 
   * @param wind the select wind object
   */
  public void displayWind(Objects.Wind wind){
    Objects.Wind.WindLine[] winds = wind.getWindLines();
    for (Objects.Wind.WindLine line : winds){
      float displayX1, displayX2;

      // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
      if (wind.getWindSpeed() < 0){
        if (line.getX1() <= wind.getX() - wind.getW() ){
          displayX1 = (wind.getX() - wind.getW() );
        } else {displayX1 = line.getX1();}
        if (line.getX2() >= wind.getX() + wind.getW() ){
          displayX2 = (wind.getX() + wind.getW() );
        } else {displayX2 = line.getX2();}
        line(displayX1, line.getY(), displayX2, line.getY() );
      }
      else if (wind.getWindSpeed() > 0){
        if (line.getX1() >= wind.getX() + wind.getW() ){
          displayX1 = (wind.getX() + wind.getW() );
        } else {displayX1 = line.getX1();}
        if (line.getX2() <= wind.getX() - wind.getW() ){
          displayX2 = (wind.getX() - wind.getW() );
        } else {displayX2 = line.getX2();}
        line(displayX1, line.getY(), displayX2, line.getY() );
      }
    }
  }

  // player methods go below here

  /**
   * updates the player's location
   */
  public void updatePlayer(){
    // detection for player colision (snowballs only rn)
    for (Objects.Snowball snow : snowballs){
      if (collision(pX, snow.getX(), pY, snow.getY(), 15, snow.getD() / 2 ) && (millis() - lastDamaged > 3000) && !snow.hidden ){
        lives -= 1;
        lastDamaged = millis();
        slow = 100;
      }
    }
    pX += pVelX;
    pY += pVelY;
  }

  /**
   * displays the player with included flashing damage indicator
   */
  public void displayPlayer(){
    if (millis() - lastDamaged < 3000){
      if ( (int)(millis() / 200) % 2 == 0){
        fill(5, 35, 128);
      } else {fill(255, 24, 12);}
    } else {fill(12, 53, 255);}
    circle(pX, pY, 30);
  }

  // UI elements go below here

  /**
   * displays the lives on the top right corner
   * 
   * @param lives the amount of lives left
   */
  public void displayLife(int lives){
    for (int i = 0; i < lives; i++){
      image(heart, width - 60 - (i * 55), 20);
    }
  }

  /**
   * displays the bar in the top right corner under the health
   */
  public void displaySlow(){
    fill(0); stroke(128);
    rect(825, 80, 165, 20);
    fill(0,0,200); stroke(255);
    rect(827, 82, (float)(161 * (slow/100) ), 16);
  }

  /**
   * updates the amount of juice you have (i have absolutly no idea what to call the mechanic)
   * note: the mechanic doesn't affect newly spaned snowballs. might need to fix.
   */
  public void updateSlow(){
    slow += slowChange * slowMulti;
    if (slow < 0){
      for (; slowMulti <= -1; slowMulti /= 2){
        for (Objects.Snowball snow : snowballs){snow.speedUp();}
        for (Objects.Wall wall : walls){wall.speedUp();}
        for (Objects.Wind wind : winds){wind.speedUp();}
      }
      slowMulti = 1;
      slow = 0;
    }
    if (slow > 100){slow = 100;}
  }

  // general math methods go below here

  /**
   * checks 2 the distance of 2 circles to see if the radius of the 2 circles is less then the distance and thus coliding
   * 
   * @param X1 the x position of the first circle
   * @param X2 the x position of the second circle
   * @param Y1 the y position of the first circle
   * @param Y2 the y position of the second circle
   * @param R1 the radius of the first circle
   * @param R2 the radius of the second circle
   * @return a boolean determining if the 2 circles colide
   */
  public boolean collision(float X1, float X2, float Y1, float Y2, float R1, float R2){
    return ( Math.sqrt(Math.pow(Math.abs(Y1 - Y2), 2) + Math.pow(Math.abs(X1 - X2), 2) ) <= R1 + R2);
  }

  /**
   * determins if an object is off screen
   * it only is true if the object is more than double it's length / height off screen
   * 
   * @param x the object's x position
   * @param y the object's y position
   * @param maxSize the object's width or height whichever is longer
   * @return a boolean determining if it is in fact off very far off screen
   */
  public boolean offScreen(float x, float y, float maxSize){
    float size = maxSize * 2;
    return ( (x + size < 0) || (x - size > width) || (y + size < 0) || (y - size > height) );
  }
}

/*
  note i will only javadoc on the used methods.
  also it turns out i don't know how to use nested classes. :<
*/