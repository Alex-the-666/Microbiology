package com.github.alexthe666.microbiology.server.item;

import com.github.alexthe666.microbiology.Microbiology;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyWorldData;
import com.github.alexthe666.microbiology.server.dimension.WorldInfoPetriDish;
import com.github.alexthe666.microbiology.server.recipe.RecipePetriDish;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemPetriDish extends Item {

    public ItemPetriDish() {
        this.setUnlocalizedName("microbiology.petri_dish");
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
        this.setRegistryName("petri_dish");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int f, boolean f1) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        } else if (stack.getTagCompound().getInteger("DimensionID") == 0) {
            getDimensionFor(stack, world);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getTagCompound() != null) {
            Item fluid = null;
            Item soil = null;
            int fluidmeta = stack.getTagCompound().getInteger("ItemFluidData");
            int soilmeta = stack.getTagCompound().getInteger("ItemSoilData");
            if (stack.getTagCompound().hasKey("ItemFluid", 8)) {
                fluid = Item.getByNameOrId(stack.getTagCompound().getString("ItemFluid"));
            } else {
                fluid = Item.getItemById(stack.getTagCompound().getInteger("ItemFluid"));
            }
            if (stack.getTagCompound().hasKey("ItemSoil", 8)) {
                soil = Item.getByNameOrId(stack.getTagCompound().getString("ItemSoil"));
            } else {
                soil = Item.getItemById(stack.getTagCompound().getInteger("ItemSoil"));
            }
            ItemStack fluidStack = new ItemStack(fluid, 1, fluidmeta);
            String fluidName = RecipePetriDish.matchesList("microbiologyFreshwater", fluidStack) ? I18n.format("microbiology.freshwater") : RecipePetriDish.matchesList("microbiologySaltwater", fluidStack) ? I18n.format("microbiology.saltwater") : fluid.getItemStackDisplayName(fluidStack);
            tooltip.add(fluidName);
            tooltip.add(soil.getItemStackDisplayName(new ItemStack(soil, 1, soilmeta)));
            tooltip.add("Dimension ID " + stack.getTagCompound().getInteger("DimensionID"));


        }
    }

    public static int getDimensionFor(ItemStack stack, World world) {
        if (stack.getTagCompound() != null && !world.isRemote) {
            if (stack.getTagCompound().getInteger("DimensionID") == 0 || !DimensionManager.isDimensionRegistered(stack.getTagCompound().getInteger("DimensionID"))) {
                NBTTagCompound compound = world.getWorldInfo().cloneNBTCompound(new NBTTagCompound());
                compound.setLong("RandomSeed", new Random().nextLong());
                WorldInfoPetriDish worldInfo = new WorldInfoPetriDish(compound);
                worldInfo.setWorldName("Petri Dish");
                int id = MicrobiologyWorldData.instance().createDimension(worldInfo);
                stack.getTagCompound().setInteger("DimensionID", id);
                return id;
            } else {
                return stack.getTagCompound().getInteger("DimensionID");
            }
        }
        return 0;
    }

}
