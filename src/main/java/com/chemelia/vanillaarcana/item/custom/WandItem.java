package com.chemelia.vanillaarcana.item.custom;

import com.chemelia.vanillaarcana.RegistryHandler;
import com.chemelia.vanillaarcana.enchantments.PyrokinesisEnchantment;
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

        PyrokinesisEnchantment pyrokinesis = RegistryHandler.PYROKINESIS.get();
        int pyrokinesisLevel = EnchantmentHelper.getItemEnchantmentLevel(pyrokinesis, stack);
        if (pyrokinesisLevel != 0 && pyrokinesis.handleUse(world, player, stack)) {
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(player.getItemInHand(pUsedHand));
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return 20;
    }
}
