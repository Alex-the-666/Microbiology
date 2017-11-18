package com.github.alexthe666.microbiology.server.item;

import com.github.alexthe666.microbiology.Microbiology;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyTeleporter;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyWorldData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemResizer extends Item {

    public ItemResizer() {
        this.setUnlocalizedName("microbiology.resizer");
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
        this.setRegistryName("resizer");
    }


    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int dim = player.dimension;
        if ((!player.isBeingRidden()) && (player.getPassengers().isEmpty()) && (player instanceof EntityPlayerMP)) {
            EntityPlayerMP thePlayer = (EntityPlayerMP) player;
            if (thePlayer.timeUntilPortal > 0) {
                thePlayer.timeUntilPortal = 10;
            } else if (MicrobiologyWorldData.instance().getDimensionInfo().containsKey(dim)) {
                thePlayer.timeUntilPortal = 10;
                thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, 0, new MicrobiologyTeleporter(thePlayer.mcServer.getWorld(0)));
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
