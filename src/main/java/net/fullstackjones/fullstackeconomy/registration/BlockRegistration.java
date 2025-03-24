package net.fullstackjones.fullstackeconomy.registration;

import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.fullstackjones.fullstackeconomy.blocks.BankLedgerBlock;
import net.fullstackjones.fullstackeconomy.blocks.CompanyLedgerBlock;
import net.fullstackjones.fullstackeconomy.blocks.MintBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FullStackEconomy.MODID);

    public static final DeferredBlock<CompanyLedgerBlock> COMPANYLEDGER_BLOCK = registerBlock(
            "companyledger",
            CompanyLedgerBlock::new);

    public static final DeferredBlock<BankLedgerBlock> BANKLEDGER_BLOCK = registerBlock(
            "bankledger",
            BankLedgerBlock::new);

    public static final DeferredBlock<MintBlock> MINT_BLOCK = registerBlock(
            "mint",
            MintBlock::new);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ItemRegistration.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
