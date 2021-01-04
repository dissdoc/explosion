package com.boom.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

import static com.boom.Config.*;

public class CursorManager {

    private static int CENTER = 16;

    private Cursor run;
    private Cursor shoot;

    public CursorManager() {
        Pixmap pixRun = new Pixmap(Gdx.files.internal(SystemHud.CURSOR_RUN));
        run = Gdx.graphics.newCursor(pixRun, CENTER, CENTER);
        pixRun.dispose();

        Pixmap pixShoot = new Pixmap(Gdx.files.internal(SystemHud.CURSOR_SHOOT));
        shoot = Gdx.graphics.newCursor(pixShoot, CENTER, CENTER);
        pixShoot.dispose();
    }

    public void render() {
        if (ControlManager.getInstance().canShoot()) {
            Gdx.graphics.setCursor(shoot);
        } else if (ControlManager.getInstance().canDraw()) {
            Gdx.graphics.setCursor(run);
        }
    }
}
