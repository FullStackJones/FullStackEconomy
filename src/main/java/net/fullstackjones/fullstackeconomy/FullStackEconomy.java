package net.fullstackjones.fullstackeconomy;

import net.fullstackjones.fullstackeconomy.networking.NetworkHandler;
import net.fullstackjones.fullstackeconomy.registration.*;
import net.fullstackjones.fullstackeconomy.screen.CurrencyLedgerScreen;
import net.fullstackjones.fullstackeconomy.services.CurrencyService;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static net.fullstackjones.fullstackeconomy.registration.MenuRegistration.BANKLEDGER_MENU;

@Mod(FullStackEconomy.MODID)
public class FullStackEconomy
{
    public static final String MODID = "fullstackeconomy";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FullStackEconomy(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ItemRegistration.register(modEventBus);
        BlockRegistration.register(modEventBus);
        CreativeTabRegistration.register(modEventBus);
        MenuRegistration.register(modEventBus);
        BlockEntityRegistraion.register(modEventBus);
        modEventBus.register(NetworkHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        MinecraftServer server = event.getServer();
        ServerLevel overworld = server.getLevel(ServerLevel.OVERWORLD);
        if (overworld != null) {
            CurrencyService.initialize(overworld);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(BANKLEDGER_MENU.get(), CurrencyLedgerScreen::new);
        }
    }
}
