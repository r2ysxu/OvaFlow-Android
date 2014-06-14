package com.ovaflow.app.engine.mania.controller;

import com.ovaflow.app.engine.mania.view.GameManiaGLRenderer;

/**
 * Created by ArthurXu on 09/06/2014.
 */
public class GameManiaEngine {

    private final GameManiaGLRenderer renderer;
    private final GameManiaTouchInputListener inputListener;

    protected long startTime;

    public GameManiaEngine() {
        renderer = new GameManiaGLRenderer();
        inputListener = new GameManiaTouchInputListener(renderer);
    }

    public void startGame() {
        startTime = System.currentTimeMillis();
    }

    public GameManiaTouchInputListener getInputListener() {
        return inputListener;
    }

    public GameManiaGLRenderer getRenderer() {
        return renderer;
    }
}
