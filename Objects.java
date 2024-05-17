import processing.core.PApplet;

public class Objects{

    static class Snowball{
        float x, y, velX, velY;
        double d;
        double g;
        boolean hidden = false;
        Snowball (float X, float Y, float XSpeed, float YSpeed, int size, double growth){
            x = X; y = Y;
            velX = XSpeed; velY = YSpeed;
            d = size; g = growth;
            hidden = false;
        }
        public void update(){
            x += velX;
            y += velY;
            d += g;
        }
        public void hide(){
            hidden = true;
        }
        public int getX(){return Math.round(x);}
        public int getY(){return Math.round(y);}
        public int getR(){return Math.round( (int)d);}
    }

    static class Wall{
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
    }

    static class Wind extends PApplet{
        float x, y, velX, velY;
        double wind;
        int w, h;
        Wind(float X, float Y, int width, int height, float XSpeed, float YSpeed, double windSpeed){
            windLine[] lines = new windLine[ Math.round( (float)Math.sqrt( Math.min(width, 2) * Math.pow(height,2) ) )];
            for (windLine line: lines){
                float lX = random(x-w, x+w);
                float lY = random(y-h, y+h);
                line = new windLine(lX, lY, lX + ((float)Math.pow(wind, 2) / w), lY, wind);
            }
        }

        static class windLine{
            float x, y, x2, y2;
            double s;
            windLine(float X, float Y, float X2, float Y2, double windSpeed){
                x = X; y = Y;
                x2 = X2; y2 = Y2;
                s = windSpeed;
            }
            public void update(){
                x += s;
            }
        }
    }

    static class health{
        float x, y, velX, velY;
        int w, h;
    }
}