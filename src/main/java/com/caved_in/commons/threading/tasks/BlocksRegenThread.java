package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.block.BlockData;
import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.effect.Effects;
import com.google.common.collect.Lists;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlocksRegenThread implements Runnable {
	private List<List<BlockData>> blockCollections;
	private boolean playEffect = true;

	public BlocksRegenThread(List<BlockData> blockCollection) {
		this.blockCollections = Lists.partition(blockCollection, 5);
	}

	public BlocksRegenThread(List<Block> blocks, boolean playEffect) {
		List<BlockData> blockDataList = new ArrayList<>();
		blocks.forEach(b -> blockDataList.add(new BlockData(b)));
		this.playEffect = playEffect;
		this.blockCollections = Lists.partition(blockDataList, 5);
	}

	@Override
	public void run() {
		for (List<BlockData> dataList : blockCollections) {
			if (playEffect) {
				/* For every block in block dataList, restore it and play a cool effect! */
				for (BlockData data : dataList) {
					Blocks.setBlock(data.getBlock(), data.getType());
					Effects.playBlockBreakEffect(data.getLocation(), Effects.BLOCK_EFFECT_RADIUS, data.getType());
				}
			} else {
				/* For every block in block dataList, restore it. */
				dataList.forEach(b -> Blocks.setBlock(b.getBlock(), b.getType()));
			}
		}

	}
}
