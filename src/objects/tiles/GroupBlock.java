package objects.tiles;

import java.util.LinkedList;

public class GroupBlock {
    LinkedList<LinkedList<Tile>> blocks;

    public GroupBlock() {
        blocks = new LinkedList<>();
    }

    public void add(int n, Tile tile) {
        while (n >= blocks.size()) blocks.add(new LinkedList<>());

        blocks.get(n).add(tile);
    }

    public void toggle(int n) {
        for (Tile tile : blocks.get(n)) tile.clickFun();
    }
}
