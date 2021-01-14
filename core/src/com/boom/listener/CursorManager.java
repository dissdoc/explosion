package com.boom.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

import static com.boom.Config.*;

public class CursorManager {

    private static CursorManager instance;

    private static final int CENTER = 16;

    private Cursor run;
    private Cursor shoot;
    private Cursor take;

    public static CursorManager getInstance() {
        if (instance == null) {
            instance = new CursorManager();
        }

        return instance;
    }

    public void init() {
        Pixmap pixRun = new Pixmap(Gdx.files.internal(SystemHud.CURSOR_RUN));
        run = Gdx.graphics.newCursor(pixRun, CENTER, CENTER);
        pixRun.dispose();

        Pixmap pixShoot = new Pixmap(Gdx.files.internal(SystemHud.CURSOR_SHOOT));
        shoot = Gdx.graphics.newCursor(pixShoot, CENTER, CENTER);
        pixShoot.dispose();

        Pixmap pixTake = new Pixmap(Gdx.files.internal(SystemHud.CURSOR_TAKE));
        take = Gdx.graphics.newCursor(pixTake, CENTER, CENTER);
        pixTake.dispose();
    }

    public void render() {
        if (ControlManager.getInstance().canShoot()) {
            Gdx.graphics.setCursor(shoot);
        } else if (ControlManager.getInstance().canDraw()) {
            Gdx.graphics.setCursor(run);
        }
    }
}
