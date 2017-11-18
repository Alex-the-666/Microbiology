package com.github.alexthe666.microbiology.server.item;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

public class ItemVial extends Item {

    public ItemVial() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setUnlocalizedName( "microbiology.vial");
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
        this.setRegistryName("vial");
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
            items.add(new ItemStack(this, 1, 2));
        }
    }

    public String getUnlocalizedName(ItemStack stack) {
        return stack.getMetadata() == 1 ? "item.microbiology.vial.freshwater" : stack.getMetadata() == 2 ? "item.microbiology.vial.seawater" : "item.microbiology.vial";
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return stack.getMetadata() == 0 ? 2000 : 0;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode;
            double d0 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * 1.0D;
            double d1 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * 1.0D + (double) entityplayer.getEyeHeight();
            double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * 1.0D;
            float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * 1.0F;
            float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * 1.0F;
            Vec3d vec3d = new Vec3d(d0, d1, d2);
            float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
            float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
            float f5 = -MathHelper.cos(-f1 * 0.017453292F);
            float f6 = MathHelper.sin(-f1 * 0.017453292F);
            float f7 = f4 * f5;
            float f8 = f3 * f5;
            Vec3d vec3d1 = vec3d.addVector((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
            RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);
            if (raytraceresult == null) {
                return;
            }
            if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
                return;
            } else {
                Block block = worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock();
                boolean flag1 = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
                if (!flag1) {
                    return;
                }
                if (!entityplayer.capabilities.isCreativeMode) {
                    stack.shrink(1);
                }
                boolean isOcean = BiomeDictionary.hasType(worldIn.getBiome(raytraceresult.getBlockPos()), BiomeDictionary.Type.OCEAN) || BiomeDictionary.hasType(worldIn.getBiome(raytraceresult.getBlockPos()), BiomeDictionary.Type.BEACH);
                entityplayer.addItemStackToInventory(new ItemStack(MicrobiologyItemRegistry.VIAL, 1, isOcean ? 2 : 1));
                entityplayer.addStat(StatList.getObjectUseStats(this));
                worldIn.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.1F);
            }

            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityplayer, EnumHand hand) {
        ItemStack itemStackIn = entityplayer.getHeldItem(hand);
        double d0 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * 1.0D;
        double d1 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * 1.0D + (double) entityplayer.getEyeHeight();
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * 1.0D;
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * 1.0F;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * 1.0F;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        entityplayer.setActiveHand(hand);
        Vec3d vec3d1 = vec3d.addVector((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
        RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);
        if (raytraceresult == null) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
        }
        if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
        } else {
            Block block = worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock();
            boolean flag1 = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
            if (!flag1) {
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
            }
            entityplayer.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
        }
    }
}
