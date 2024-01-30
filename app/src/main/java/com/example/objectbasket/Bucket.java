package com.example.objectbasket;

public class Bucket {
    float posy, posx, hity, hitx;

    public Bucket(float initx, float inity){
        this.posy = inity;
        this.posx = initx;
        this.hity = 50;
        this.hitx = 52;
    }


    public float getPosx() {
        return posx;
    }

    public float getHity() {
        return hity;
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public void setHity(float hity) {
        this.hity = hity;
    }

    public void setHitx(float hitx) {
        this.hitx = hitx;
        System.out.println("set: " + hitx);
    }
}
