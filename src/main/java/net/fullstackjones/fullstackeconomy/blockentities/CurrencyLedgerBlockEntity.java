package net.fullstackjones.fullstackeconomy.blockentities;

import net.fullstackjones.fullstackeconomy.menu.CurrencyLedgerMenu;
import net.fullstackjones.fullstackeconomy.services.CurrencyService;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static net.fullstackjones.fullstackeconomy.registration.BlockEntityRegistraion.BANKLEDGER_BLOCKENTITY;

public class CurrencyLedgerBlockEntity extends BlockEntity implements MenuProvider {

    private CurrencyService currencyService;

    public CurrencyLedgerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BANKLEDGER_BLOCKENTITY.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.fullstackeconomy.currencyledger");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new CurrencyLedgerMenu(containerId, playerInventory);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.level instanceof ServerLevel serverLevel) {
            this.currencyService = new CurrencyService(serverLevel);
        }
    }
}
