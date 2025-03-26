package net.fullstackjones.fullstackeconomy.registration;

import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.fullstackjones.fullstackeconomy.blockentities.BankLedgerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.fullstackjones.fullstackeconomy.registration.BlockRegistration.BANKLEDGER_BLOCK;

public class BlockEntityRegistraion {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FullStackEconomy.MODID);

    public static final Supplier<BlockEntityType<BankLedgerBlockEntity>> BANKLEDGER_BLOCKENTITY = BLOCK_ENTITIES.register(
            "bankledger_blockentity",
            () -> BlockEntityType.Builder
                    .of(BankLedgerBlockEntity::new, BANKLEDGER_BLOCK.get())
                    .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
