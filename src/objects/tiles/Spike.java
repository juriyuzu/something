package objects.tiles;

import main.Game;
import main.Panel;
import utilities.AStar;

import javax.swing.*;
import java.awt.*;

public class Spike extends Block {
    public boolean reverse;

    public Spike(Panel panel, Game game, int x, int y, boolean reverse) {
        super(panel, game, x, y);

        type = TileType.HAZARD;
        on = true;
        this.reverse = reverse;
        if (reverse) imageOn = new ImageIcon("src/assets/game/tiles/spikeRed.png").getImage();
        else imageOn = new ImageIcon("src/assets/game/tiles/spike.png").getImage();
        image = imageOn;
        imageOff = new ImageIcon("src/assets/game/tiles/spikeOff.png").getImage();
        destinationAble = true;
        pauseAble = true;
        solid = false;
    }

    public void toggle() {
        on = !on;

        if (reverse) {
            if (on) image = imageOff;
            else image = imageOn;
        } else {
            if (on) image = imageOn;
            else image = imageOff;
        }
    }

    public void clickFun() {
        on = !on;

        if (!spikeCheck() && AStar.rectRect(game.player.x, game.player.y, game.player.w/2, game.player.h/2, x, y, w/2, h/2))
            game.player.dead();
    }

    public boolean spikeCheck() {
        return (on && !reverse) || (!on && reverse);
    }
}
