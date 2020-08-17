package io.dsub.game;

import io.dsub.game.model.LocalPlayer;
import io.dsub.game.model.Player;

public interface Winnable {
    Player getCurrentWinner();
}
