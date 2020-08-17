package io.dsub.game.model;

import io.dsub.util.InputUtil;

public class LocalPlayer extends Player implements Inputtable {
    public LocalPlayer() {
        super();
    }

    public LocalPlayer(String name) {
        super(name);
    }

    @Override
    public void getKeyboardInput() {
        this.setLastInput(InputUtil.takeInput());
    }
}
