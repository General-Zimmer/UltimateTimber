package com.songoda.ultimatetimber.adapter;

import com.songoda.ultimatetimber.tree.FallingTreeBlock;
import com.songoda.ultimatetimber.tree.ITreeBlock;
import com.songoda.ultimatetimber.tree.TreeBlockSet;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public interface VersionAdapter {

    /**
     * Gets the version adapter type
     *
     * @return The VersionAdapterType
     */
    VersionAdapterType getVersionAdapterType();

    /**
     * Parses a String into an IBlockData instance
     *
     * @param blockDataString The String to parse
     * @return An IBlockData instance that the given String represents
     */
    IBlockData parseBlockDataFromString(String blockDataString);

    /**
     * Parses a String into an ItemStack
     *
     * @param itemStackString The String to parse
     * @return An ItemStack that the given String represents
     */
    ItemStack parseItemStackFromString(String itemStackString);

    /**
     * Get the items that a tree block should drop when it breaks
     *
     * @param treeBlock The tree block
     * @return A Set of ItemStacks that should be dropped
     */
    Collection<ItemStack> getBlockDrops(ITreeBlock treeBlock);

    /**
     * Applies damage to a tool
     *
     * @param player The Player who's tool to apply damage to
     * @param damage The amount of damage to apply
     */
    void applyToolDurability(Player player, int damage);

    /**
     * Checks if a given tool has enough durability remaining
     *
     * @param tool The tool to check
     * @param requiredAmount The amount of durability required
     * @return True if enough durability is remaining to not break the tool, otherwise false
     */
    boolean hasEnoughDurability(ItemStack tool, int requiredAmount);

    /**
     * Gets the item in the player's main hand
     *
     * @param player The Player to get the item from
     * @return The ItemStack in the Player's main hand
     */
    ItemStack getItemInHand(Player player);

    /**
     * Removes the item in the player's main hand
     *
     * @param player The Player to remove the item from
     */
    void removeItemInHand(Player player);

    /**
     * Plays particles to indicate a tree block has started falling
     *
     * @param treeBlock The TreeBlock to play the particles for
     */
    void playFallingParticles(ITreeBlock treeBlock);

    /**
     * Plays particles to indicate a tree block has hit the ground
     *
     * @param treeBlock The TreeBlock to play the particles for
     */
    void playLandingParticles(ITreeBlock treeBlock);

    /**
     * Plays a sound to indicate a tree block has started falling
     *
     * @param treeBlock The TreeBlock to play the sound for
     */
    void playFallingSound(ITreeBlock treeBlock);

    /**
     * Plays a sound to indicate a tree block has hit the ground
     *
     * @param treeBlock The TreeBlock to play the sound for
     */
    void playLandingSound(ITreeBlock treeBlock);

}
