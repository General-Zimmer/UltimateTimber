package com.songoda.ultimatetimber.animation;

import com.songoda.ultimatetimber.UltimateTimber;
import com.songoda.ultimatetimber.adapter.VersionAdapter;
import com.songoda.ultimatetimber.manager.ConfigurationManager;
import com.songoda.ultimatetimber.manager.TreeDefinitionManager;
import com.songoda.ultimatetimber.tree.DetectedTree;
import com.songoda.ultimatetimber.tree.ITreeBlock;
import com.songoda.ultimatetimber.tree.TreeBlockSet;
import com.songoda.ultimatetimber.tree.TreeDefinition;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TreeAnimationDisintegrate extends TreeAnimation {

    public TreeAnimationDisintegrate(DetectedTree detectedTree, Player player) {
        super(TreeAnimationType.DISINTIGRATE, detectedTree, player);
    }

    @Override
    public void playAnimation(Runnable whenFinished) {
        UltimateTimber ultimateTimber = UltimateTimber.getInstance();
        TreeDefinitionManager treeDefinitionManager = ultimateTimber.getTreeDefinitionManager();
        VersionAdapter versionAdapter = ultimateTimber.getVersionAdapter();

        boolean useCustomSound = ConfigurationManager.Setting.USE_CUSTOM_SOUNDS.getBoolean();
        boolean useCustomParticles = ConfigurationManager.Setting.USE_CUSTOM_PARTICLES.getBoolean();

        if (useCustomSound)
            versionAdapter.playFallingSound(this.detectedTree.getDetectedTreeBlocks().getInitialLogBlock());

        List<ITreeBlock<Block>> orderedLogBlocks = new ArrayList<>(this.detectedTree.getDetectedTreeBlocks().getLogBlocks());
        orderedLogBlocks.sort(Comparator.comparingInt(x -> x.getLocation().getBlockY()));

        List<ITreeBlock<Block>> leafBlocks = new ArrayList<>(this.detectedTree.getDetectedTreeBlocks().getLeafBlocks());
        Collections.shuffle(leafBlocks);

        Player p = this.player;
        TreeDefinition td = this.detectedTree.getTreeDefinition();

        new BukkitRunnable() {
            @Override
            public void run() {
                List<ITreeBlock<Block>> toDestroy = new ArrayList<>();

                if (!orderedLogBlocks.isEmpty()) {
                    ITreeBlock<Block> treeBlock = orderedLogBlocks.get(0);
                    orderedLogBlocks.remove(treeBlock);
                    toDestroy.add(treeBlock);
                } else if (!leafBlocks.isEmpty()) {
                    ITreeBlock<Block> treeBlock = leafBlocks.get(0);
                    leafBlocks.remove(treeBlock);
                    toDestroy.add(treeBlock);

                    if (!leafBlocks.isEmpty()) {
                        treeBlock = leafBlocks.get(0);
                        leafBlocks.remove(treeBlock);
                        toDestroy.add(treeBlock);
                    }
                }

                if (!toDestroy.isEmpty()) {
                    ITreeBlock<Block> first = toDestroy.get(0);
                    if (useCustomSound)
                        versionAdapter.playLandingSound(first);

                    for (ITreeBlock<Block> treeBlock : toDestroy) {
                        if (useCustomParticles)
                            versionAdapter.playFallingParticles(treeBlock);
                        treeDefinitionManager.dropTreeLoot(td, treeBlock, p);
                        TreeAnimationDisintegrate.this.replaceBlock(treeBlock.getBlock());
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(ultimateTimber, 0, 1);
    }

}
