import processing.core.PApplet;

public class Objects{

    static float x, y, velX, velY;

    static class Snowball{
        double d;
        double g;
        boolean hidden = false;
        Snowball (float X, float Y, float XSpeed, float YSpeed, int size, double growth){
            x = X; y = Y;
            velX = XSpeed; velY = YSpeed;
            d = size; g = growth;
            hidden = false;
        }
        public void hide(){
            hidden = true;
        }

        public void update(){x += velX; y += velY; d += g;}
        public void slowDown(){velX /= 2; velY /= 2; g /= 2;}
        public void speedUp(){velX *= 2;velY *= 2; g *= 2;}

        public float getX(){return x;}
        public float getY(){return y;}
        public float getD(){return (float)d;}
    }

    static class Wall{
        int w, h;
        Wall(float X, float Y, int width, int height, float XSpeed, float YSpeed){
            x = X; y = Y;
            velX = XSpeed; velY = YSpeed;
            w = width; h = height;
        }

        public void update(){x += velX; y += velY;}
        public void slowDown(){velX /= 2; velY /= 2;}
        public void speedUp(){velX *= 2;velY *= 2;}

        public float getX(){return x;}
        public float getY(){return y;}
        public float getW(){return w;}
        public float getH(){return h;}
    }

    // probably the most complicated object and it's just for aescthetics
    static class Wind extends PApplet{
        static double wind;
        int w, h;
        WindLine[] lines;
        Wind(float X, float Y, int width, int height, float XSpeed, float YSpeed, double windSpeed){
            x = X; y = Y;
            velX = XSpeed; velY = YSpeed;
            w = width; h = height;
            wind = windSpeed;
            lines = new WindLine[Math.round( (float)Math.sqrt(w * h) )];
            for (int i = 0; i < lines.length; i++){
                float lX = random(x-w, x+w);
                float lY = random(y-h, y+h);
                lines[i] = new WindLine(lX, lY);
            }
        }

        public void update(){
            x += velX; y += velY;
            for (WindLine line : lines){
                line.update();
            }
        }
        public void slowDown(){velX /= 2; velY /= 2; wind /= 2;}
        public void speedUp(){velX *= 2;velY *= 2; wind *= 2;}

        public float getX(){return x;}
        public float getY(){return y;}
        public float getW(){return w;}
        public float getH(){return h;}
        public double getWindSpeed(){return wind;}

        public WindLine[] getWindLines(){return lines;}

        class WindLine{
            float wX1, wY, wX2;
            WindLine(float X, float Y){
                wX1 = X; wY = Y;
                wX2 = X + (float)(wind * 3);
            }

            public void update(){
                if (wind < 0){
                    if (wX2 <= x - (w / 2) ){
                        wY = random(y-h, y+h);
                        wX1 = x + (w / 2);
                    }
                } else if (wind > 0){
                    if (wX2 >= x + (w / 2) ){
                        wY = random(y-h, y+h);
                        wX1 = x - (w / 2);
                    }
                }
                wX1 += wind;
            }

            public float getX1(){return wX1;}
            public float getX2(){return wX2;}
            public float getY(){return wY;}
        }
    }

    static class Ice{
        int w, h;
        Ice(float X, float Y, int width, int height, float YSpeed){
            x = X; y = Y;
            velY = YSpeed;
            w = width; h = height;
        }
        public void update(){x += velX; y += velY;}
        public void slowDown(){velX /= 2; velY /= 2;}
        public void speedUp(){velX *= 2;velY *= 2;}
    }

    static class Health{
        Health(float X, float Y, float XSpeed, float YSpeed){
            x = X; y = Y;
            velX = XSpeed; velY = YSpeed;
        }
        public void update(){x += velX; y += velY;}
        public void slowDown(){velX /= 2; velY /= 2;}
        public void speedUp(){velX *= 2;velY *= 2;}
    }

    public static void update(){
        x += velX; y += velY;
    }
}