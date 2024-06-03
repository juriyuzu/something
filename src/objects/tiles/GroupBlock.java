package objects.tiles;

import java.util.LinkedList;

public class GroupBlock {
    LinkedList<LinkedList<Block>> blocks;

    public GroupBlock() {
        blocks = new LinkedList<>();
    }

    public void add(int n, Block block) {
        while (n >= blocks.size()) blocks.add(new LinkedList<>());

        blocks.get(n).add(block);
    }

    public void toggle(int n) {
        for (Block block : blocks.get(n)) {
            if (!block.lock) {
                block.clickFun();
                block.lock = true;
            }
        }
    }

    public void unlock(int n) {
        for (Block block : blocks.get(n)) block.lock = false;
    }
}
