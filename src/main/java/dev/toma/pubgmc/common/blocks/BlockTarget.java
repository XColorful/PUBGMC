package dev.toma.pubgmc.common.blocks;

import dev.toma.pubgmc.api.block.IBulletReaction;
import dev.toma.pubgmc.api.capability.IPlayerData;
import dev.toma.pubgmc.api.capability.PlayerDataProvider;
import dev.toma.pubgmc.common.entity.EntityBullet;
import dev.toma.pubgmc.init.PMCBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTarget extends PMCBlock implements IBulletReaction {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool UPPER = PropertyBool.create("upper");
    public static final PropertyBool FEEDBACK = PropertyBool.create("feedback");

    public BlockTarget(String name) {
        super(name, Material.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(UPPER, false).withProperty(FEEDBACK, false));
        this.setDescription("label.pubgmc.target.description");
    }

    @SuppressWarnings("ConstantValue")
    @Override
    public void onBulletHit(EntityBullet bullet, Vec3d hit, @Nullable IBlockState state, @Nullable Entity entity) {
        if (state == null) {
            return;
        }
        if (bullet.getShooter() != null && bullet.getShooter() instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer) bullet.getShooter();
            float damage = bullet.getDamage();
            double delta = hit.y - (int) hit.y;
            boolean headShot = state.getBlock() == PMCBlocks.TARGET && state.getValue(UPPER) && delta > 0.5;
            ITextComponent message = new TextComponentTranslation(headShot ? "label.pubgmc.target.headshot" : "label.pubgmc.target.hit");
            if (headShot) {
                damage *= bullet.getHeadshotMultipler();
                message.getStyle().setColor(TextFormatting.RED);
            }
            ITextComponent damageComponent = new TextComponentString(String.format("%.2f", damage));
            message.appendSibling(damageComponent);
            player.sendMessage(message);
        }
    }

    @Override
    public boolean allowBulletInteraction(World world, @Nullable IBlockState state, @Nullable Entity entity) {
        if (state == null) {
            return false;
        }
        return state.getValue(FEEDBACK);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        EnumFacing facing = state.getValue(UPPER) ? EnumFacing.DOWN : EnumFacing.UP;
        worldIn.destroyBlock(pos.offset(facing), false);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!state.getValue(UPPER)) worldIn.setBlockState(pos.up(), state.withProperty(UPPER, true));
        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            IPlayerData data = playerIn.getCapability(PlayerDataProvider.PLAYER_DATA, null);
            data.getAimInfo().setAiming(false, 1.0F);
            boolean b = !state.getValue(FEEDBACK);
            EnumFacing facing1 = state.getValue(UPPER) ? EnumFacing.DOWN : EnumFacing.UP;
            worldIn.setBlockState(pos, state.withProperty(FEEDBACK, b));
            worldIn.setBlockState(pos.offset(facing1), worldIn.getBlockState(pos.offset(facing1)).withProperty(FEEDBACK, b));
            playerIn.sendStatusMessage(new TextComponentTranslation(b ? "label.pubgmc.target.enabled" : "label.pubgmc.target.disabled"), true);
        }
        return true;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(UPPER) ? 4 : 0) | (state.getValue(FEEDBACK) ? 8 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(UPPER, (meta & 4) != 0).withProperty(FEEDBACK, (meta & 8) != 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, UPPER, FEEDBACK);
    }
}
