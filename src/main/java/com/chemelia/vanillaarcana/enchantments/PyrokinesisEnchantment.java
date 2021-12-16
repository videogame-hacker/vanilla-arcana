package com.chemelia.vanillaarcana.enchantments;

import com.chemelia.vanillaarcana.RegistryHandler;
import com.chemelia.vanillaarcana.VanillaArcana;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfig.Server;

///////////////
//PYROKINESIS//
///////////////
//I:    Blaze fireball
//II:   Ghast fireball
//III:  Conjure lava? (infinite lava bucket)
//IV:   Summon blaze
//V:    Dragon fireball? Fire everywhere

//PSEUDOCODE
/*
getSpellXPCost(enchantmentLevel){
    return enchanmentLevel * 3;
}

@Override
public void onSwingItem(){
    if (player.xp > getSpellXPCost){
        player.xp.decrease(getSpellXPCost);
        switch (enchantmentLevel){
            case 1:
                EntityType.SMALL_FIREBALL.spawn(location, velocity);
                break;
            case 2:
                spawnGhastFireball(location, velocity);
                break;
            case 3:
                placeBlock(LAVA);
                break;
            case 4: 
                spawnFriendlyBlaze();
            case 5:

            default:
                break;
        }
    } else {
        spawnSmokeParticles();
    }
}
*/


public class PyrokinesisEnchantment extends Enchantment {
    public static final String ID = VanillaArcana.MOD_ID + ":pyrokinesis";

    public PyrokinesisEnchantment() {
        super(Rarity.UNCOMMON, RegistryHandler.WAND_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    // Handles a right click :)
    // See WandItem#use
    public boolean handleUse(Level world, Player player, ItemStack stack) {
        if (world.isClientSide()) {
            return false;
        }

        int level = EnchantmentHelper.getItemEnchantmentLevel(this, stack);

        Vec3 look = player.getLookAngle();
        Vec3 pos = player.getEyePosition().add(look.scale(0.9));
        Vec3 velocity = look.scale(0.5);

        SmallFireball fireball = new SmallFireball(world, player, 0, 0, 0);
        fireball.setPos(pos.x, pos.y, pos.z);
        fireball.xPower = velocity.x;
        fireball.yPower = velocity.y;
        fireball.zPower = velocity.z;

        world.addFreshEntity(fireball);

        player.getCooldowns().addCooldown(stack.getItem(), 20 / level);

        return true;
    }

    @Override
    public int getMaxLevel(){
        return 3;
    }
}
