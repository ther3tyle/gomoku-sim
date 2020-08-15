package io.dsub.game;

import io.dsub.game.model.Player;
import io.dsub.game.model.Position;

public interface Playable {
    void play(Player player, Position pos);
}
