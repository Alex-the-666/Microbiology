package com.github.alexthe666.microbiology.server.recipe;

import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipePetriDish extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private ItemStack output = ItemStack.EMPTY;
    private ItemStack liquid = ItemStack.EMPTY;
    private ItemStack solid = ItemStack.EMPTY;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasDish = false;
        ItemStack liquid = null;
        ItemStack solid = null;
        for(int i = 0; i < 9; i++){
            if(inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() != null && inv.getStackInSlot(i).getItem() == MicrobiologyItemRegistry.EMPTY_PETRI_DISH){
                hasDish = true;
            }
        }
        for(int i = 0; i < 9; i++){
            if(inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() != null && matchesList("microbiologySoil", inv.getStackInSlot(i))){
                solid = inv.getStackInSlot(i);
                this.solid = solid;
            }
        }
        for(int i = 0; i < 9; i++){
            if(inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() != null && (matchesList("microbiologySaltwater", inv.getStackInSlot(i)) || matchesList("microbiologyFreshwater", inv.getStackInSlot(i)))){
                liquid = inv.getStackInSlot(i);
                this.liquid = liquid;
            }
        }
        return hasDish && solid != null && liquid != null;
    }

    public static final boolean matchesList(String list, ItemStack stack){
        for(ItemStack stack2 : OreDictionary.getOres(list)){
            if(stack.getItem() == stack2.getItem() && stack.getMetadata() == stack2.getMetadata()){
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack stack = new ItemStack(MicrobiologyItemRegistry.PETRI_DISH, 1, 0);
        NBTTagCompound tag = new NBTTagCompound();
        ResourceLocation resourcelocationFluid = Item.REGISTRY.getNameForObject(this.liquid.getItem());
        ResourceLocation resourcelocationSoil = Item.REGISTRY.getNameForObject(this.solid.getItem());
        tag.setString("ItemFluid", resourcelocationFluid == null ? "" : resourcelocationFluid.toString());
        tag.setInteger("ItemFluidData", this.liquid.getMetadata());
        tag.setString("ItemSoil", resourcelocationSoil == null ? "" : resourcelocationSoil.toString());
        tag.setInteger("ItemSoilData", this.solid.getMetadata());
        stack.setTagCompound(tag);
        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        ItemStack stack = new ItemStack(MicrobiologyItemRegistry.PETRI_DISH, 1, 0);
        NBTTagCompound tag = new NBTTagCompound();
        ResourceLocation resourcelocationFluid = Item.REGISTRY.getNameForObject(this.liquid.getItem());
        ResourceLocation resourcelocationSoil = Item.REGISTRY.getNameForObject(this.solid.getItem());
        tag.setString("ItemFluid", resourcelocationFluid == null ? "" : resourcelocationFluid.toString());
        tag.setInteger("ItemFluidData", this.liquid.getMetadata());
        tag.setString("ItemSoil", resourcelocationSoil == null ? "" : resourcelocationSoil.toString());
        tag.setInteger("ItemSoilData", this.solid.getMetadata());
        stack.setTagCompound(tag);
        return stack;
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
