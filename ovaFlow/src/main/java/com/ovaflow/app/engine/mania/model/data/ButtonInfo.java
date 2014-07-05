package com.ovaflow.app.engine.mania.model.data;

/**
 * Created by ArthurXu on 04/07/2014.
 */
public class ButtonInfo {

    private int imageId;
    private float scaleX;
    private float scaleY;
    private float posY;
    private float posX;

    public static ButtonInfo[] generateButtons(int num, float scaleX, float scaleY) {
        ButtonInfo[] binfos = new ButtonInfo[num];
        for (int i = 0; i < num; i++)
         binfos[i] =  new ButtonInfo(scaleX, scaleY, 0f, 0f, 0);
        return binfos;
    }

    public ButtonInfo(float scaleX, float scaleY, float posY, float posX, int imageId) {
        this.imageId = imageId;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.posY = posY;
        this.posX = posX;
    }

    public void setButton(float posY, float posX, int imageId) {
        this.posX = posX;
        this.posY = posY;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getPosY() {
        return posY;
    }

    public float getPosX() {
        return posX;
    }
}
