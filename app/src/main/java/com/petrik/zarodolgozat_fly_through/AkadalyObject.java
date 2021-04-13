package com.petrik.zarodolgozat_fly_through;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class AkadalyObject extends AlapObject{

    public static int speed;

    public AkadalyObject(float x, float y,int width, int height){

        super(x,y,width,height);
        speed = 10*Allandok.SCREEN_WIDTH/1080;

    }

    public void draw(Canvas cavanas){
        this.x-=speed;
        cavanas.drawBitmap(this.bm,this.x,this.y,null);
    }

    public void randomY(){
        Random r = new Random();
        this.y = r.nextInt((0+this.height/4)+1)-this.height/4;
    }

    @Override
    public void setBm(Bitmap bm) {
        this.bm = Bitmap.createScaledBitmap(bm,width,height,true);
    }
}
