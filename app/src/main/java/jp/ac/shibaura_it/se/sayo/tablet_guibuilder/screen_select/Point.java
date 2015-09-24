package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import java.io.Serializable;

/**
 * Created by matsu on 2014/10/27.
 */
public class Point {
    private int x;
    private int y;

    protected Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected int getX() {
        return x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }
}
