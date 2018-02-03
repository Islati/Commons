package com.devsteady.onyx.threading.tasks;

import com.devsteady.onyx.block.BlockData;
import com.devsteady.onyx.block.Blocks;
import com.devsteady.onyx.effect.Effects;

public class BlockRegenThread implements Runnable {
    private BlockData blockData;
    private boolean playEffect = false;


    public BlockRegenThread(BlockData blockData, boolean playEffect) {
        this.blockData = blockData;
        this.playEffect = playEffect;
    }

    @Override
    public void run() {
        Blocks.setBlock(blockData.getBlock(), blockData.getType());
        if (playEffect) {
            Effects.playBlockBreakEffect(blockData.getLocation(), Effects.BLOCK_EFFECT_RADIUS, blockData.getType());
        }
    }
}
