package com.toma.pubgmc.common.blocks;

import java.util.Random;

import com.toma.pubgmc.init.PMCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockOre extends PMCBlock
{	
	private ItemStack drop;
	private int quantity = 1;
	private boolean isDropRandom = false;
	private int dropScale = 1;
	
	public BlockOre(String name)
	{
		super(name, Material.ROCK);
		setHardness(1.5f);
		setResistance(15f);
		setHarvestLevel("pickaxe", 1);
	}
	
	/*public BlockOre(String name, Block drop)
	{
		this(name);
		setDrop(new ItemStack(drop));
	}
	
	public BlockOre(String name, Item drop)
	{
		this(name);
		setDrop(new ItemStack(drop));
	}
	
	public BlockOre(String name, Block drop, int dropAmount, boolean randomDropAmount, int dropScale)
	{
		this(name);
		setDrop(new ItemStack(drop));
		quantity = dropAmount;
	}
	
	public BlockOre(String name, Item drop, int dropAmount, boolean randomDropAmount, int dropScale)
	{
		this(name);
		setDrop(new ItemStack(drop));
		quantity = dropAmount;
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		//setDrop(drop);
		System.out.println(drop);
		return getDrop().getItem();
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return isDropRandom ? quantity + random.nextInt(fortune + 1) + random.nextInt(dropScale) : quantity + random.nextInt(fortune + 1);
	}
	
	public ItemStack setDrop(ItemStack toDrop)
	{
		return drop = toDrop;
	}
	
	public ItemStack getDrop()
	{
		return drop;
	}*/
}
