package net.fullstackjones.fullstackeconomy.registration;

import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabRegistration {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FullStackEconomy.MODID);

    public static final Supplier<CreativeModeTab> FULLSTACKECONOMYTAB = CREATIVE_MODE_TAB.register(
            "fullstackeconomy_tab",
            () -> CreativeModeTab.builder().icon(() ->
                            new ItemStack(BlockRegistration.COMPANYLEDGER_BLOCK.get()))
                    .title(Component.translatable("creativetab.fullstackeconomy.fullstackeconomy_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BlockRegistration.COMPANYLEDGER_BLOCK);
                        output.accept(BlockRegistration.CURRENCYLEDGER_BLOCK);
                        output.accept(BlockRegistration.MINT_BLOCK);
                        output.accept(ItemRegistration.COPPERCOIN_ITEM);
                        output.accept(ItemRegistration.SILVERCOIN_ITEM);
                        output.accept(ItemRegistration.GOLDCOIN_ITEM);
                        output.accept(ItemRegistration.COINPRESS_PLATE_ITEM);
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
