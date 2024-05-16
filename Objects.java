import processing.core.PApplet;

public class Objects extends PApplet{

    class Snowball {
        float x, y, velX, velY;
        int r;
        double g;
        boolean hidden;
        Snowball (float X, float Y, float XSpeed, float YSpeed, int size, double growth){
            x = X; y = Y;
            velX = XSpeed; velY = YSpeed;
            r = size;
            hidden = false;
        }
        public void update(){
            x += velX;
            y += velY;
            r += g;
        }
        public void display(){
            circle(x,y,r);
        }
        public void hide(){
            hidden = true;
        }
    }

    class Wall {
        float x, y, velX, velY;
        int w, h;
        Wall(float X, float Y, int width, int height, float XSpeed, float YSpeed){
            x = X; y = Y;
            velX = XSpeed; velY = YSpeed;
            w = width; h = height;
        }
        public void update(){
            x += velX;
            y += velY;
        }
        public void display(){
            rect(x + (w/2), y + (h/2), w, h);
        }
    }

    class Wind {
        float x, y, velX, velY;
        double wind;
        int w, h;
        Wind(float X, float Y, int width, int height, float XSpeed, float YSpeed, double windSpeed){

        }
    }
}