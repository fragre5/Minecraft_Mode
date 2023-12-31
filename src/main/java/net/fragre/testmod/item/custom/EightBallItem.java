package net.fragre.testmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EightBallItem extends Item {
    public EightBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            outputNumber(player);
            player.getCooldowns().addCooldown(this, 20);
        }

        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {

        if (Screen.hasShiftDown()) {
            components.add(Component.literal("Right click to get a random number").withStyle(ChatFormatting.AQUA));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        }

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    private void outputNumber(Player player) {
        int number = getRandomNumber();
        if (number == 4) {
            player.sendSystemMessage(Component.literal("You win! and your number is " + number));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100));
        } else {
            player.sendSystemMessage(Component.literal("You loose! " + number));
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
        }

    }

    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
