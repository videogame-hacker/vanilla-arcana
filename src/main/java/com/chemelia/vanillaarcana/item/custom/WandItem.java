package com.chemelia.vanillaarcana.item.custom;

import com.chemelia.vanillaarcana.RegistryHandler;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WandItem extends Item {
    public WandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand pUsedHand) {
        ItemStack stack = player.getItemInHand(pUsedHand);
        if (!(stack.getItem() instanceof WandItem))
            return InteractionResultHolder.pass(player.getItemInHand(pUsedHand));

        // TODO: Probably put this in, like, its own handler thing for Pyrokinesis :)
        int pyrokinesisLevel = EnchantmentHelper.getItemEnchantmentLevel(RegistryHandler.PYROKINESIS.get(), stack);
        if (pyrokinesisLevel != 0) {
            if (!world.isClientSide()) {
                Vec3 look = player.getLookAngle();
                Vec3 pos = player.getEyePosition().add(look.scale(0.9));
                Vec3 velocity = look.scale(0.5);

                SmallFireball fireball = new SmallFireball(world, player, 0, 0, 0);
                fireball.setPos(pos.x, pos.y, pos.z);
                fireball.xPower = velocity.x;
                fireball.yPower = velocity.y;
                fireball.zPower = velocity.z;

                world.addFreshEntity(fireball);

                player.getCooldowns().addCooldown(this, 20 / pyrokinesisLevel);

                return InteractionResultHolder.success(stack);
            }
        }

        return InteractionResultHolder.pass(player.getItemInHand(pUsedHand));
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return 20;
    }
}
