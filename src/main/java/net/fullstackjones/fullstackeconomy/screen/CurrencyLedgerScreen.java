package net.fullstackjones.fullstackeconomy.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.fullstackjones.fullstackeconomy.data.CurrencyData;
import net.fullstackjones.fullstackeconomy.menu.CurrencyLedgerMenu;
import net.fullstackjones.fullstackeconomy.networking.ClientPayloadHandler;
import net.fullstackjones.fullstackeconomy.networking.CurrencyDataCreatePayload;
import net.fullstackjones.fullstackeconomy.networking.UpdatedCurrenciesPayload;
import net.fullstackjones.fullstackeconomy.services.ResourceHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class CurrencyLedgerScreen extends AbstractContainerScreen<CurrencyLedgerMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"textures/gui/currencyledger.png");
    private static final WidgetSprites ADDBUTTON_SPRITE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"add_button"),
            ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"add_button")
    );
    private static final WidgetSprites LEFTARROW_SPRITE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"leftarrow_button"),
            ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"leftarrow_button")
    );
    private static final WidgetSprites RIGHTARROW_SPRITE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"rightarrow_button"),
            ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"rightarrow_button")
    );

    private static final WidgetSprites ENCHANTMENT_SLOT_SPRITE = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("container/enchanting_table/enchantment_slot"),
            ResourceLocation.withDefaultNamespace("container/enchanting_table/enchantment_slot_highlighted")
            //ResourceLocation.withDefaultNamespace("container/enchanting_table/enchantment_slot_disabled")
    );

    private static final ResourceLocation TEXT_FIELD_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation SCROLLMENU_SPRITE = ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID,"scrollmenu_widget");

    private static final ResourceLocation[] CURRENCYSYMBOLS = ResourceHelper.getCurrencySymbolResourceLocation(FullStackEconomy.MODID, "textures/gui/sprites/currencysymbols");
    private EditBox currencyNameField;
    private int currencySymbolIndex = 0;

    public CurrencyLedgerScreen(CurrencyLedgerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 250;
        this.imageHeight = 250;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Render the background and widgets
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // Render the currency text after the buttons
        if (ClientPayloadHandler.updatedCurrencies != null) {
            int yOffset = 88; // Starting Y position for rendering
            for (CurrencyData currency : ClientPayloadHandler.updatedCurrencies) {
                guiGraphics.drawString(this.font, currency.Name, this.leftPos + 10, this.topPos + yOffset + 4, 4210752, false);
                guiGraphics.blitSprite(ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID, currency.Symbol), this.leftPos + 50, this.topPos + yOffset + 4, 8, 8);
                yOffset += 16; // Increment Y position for the next currency
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blitSprite(TEXT_FIELD_SPRITE, this.leftPos + 5, this.topPos + 55, 110, 16);
        guiGraphics.blitSprite(SCROLLMENU_SPRITE, this.leftPos + 5, this.topPos + 88, 110, 80);
        guiGraphics.blitSprite(SCROLLMENU_SPRITE, this.leftPos + 125, this.topPos + 88, 110, 80);
        ResourceLocation currencySymbol = CURRENCYSYMBOLS[currencySymbolIndex];
        guiGraphics.blitSprite(currencySymbol, this.leftPos + 130, this.topPos + 55, 16, 16);
        renderCurrencyList(guiGraphics);
        renderCurrencyDetails(guiGraphics);
    }

    @Override
    protected void init() {
        super.init();
        subInit();
        PacketDistributor.sendToServer(new UpdatedCurrenciesPayload());
    }

    protected void subInit() {
        renderCurrencyCreationFields();

    }

    private void renderCurrencyCreationFields() {
        ImageButton leftArrowButton = new ImageButton(this.leftPos + 115,this.topPos + 55,16,16, LEFTARROW_SPRITE, Button ->{
            if(currencySymbolIndex == 0){
                currencySymbolIndex = CURRENCYSYMBOLS.length - 1;
            } else {
                currencySymbolIndex--;
            }
        });
        ImageButton rightArrowButton = new ImageButton(this.leftPos + 145,this.topPos + 55,16,16, RIGHTARROW_SPRITE, Button ->{
            if(currencySymbolIndex < CURRENCYSYMBOLS.length - 1){
                currencySymbolIndex++;
            } else {
                currencySymbolIndex = 0;
            }
        });
        ImageButton addButton = new ImageButton(this.leftPos + 165,this.topPos + 55,16,16, ADDBUTTON_SPRITE, Button ->{
            CurrencyDataCreatePayload payload = new CurrencyDataCreatePayload(currencyNameField.getValue(), CURRENCYSYMBOLS[currencySymbolIndex].getPath());
            PacketDistributor.sendToServer(payload);
        });

        this.currencyNameField = new EditBox(this.font, this.leftPos + 10,this.topPos + 59, 100, 16, Component.translatable("container.repair"));
        this.currencyNameField.setCanLoseFocus(false);
        this.currencyNameField.setBordered(false);
        this.currencyNameField.setMaxLength(50);
        this.currencyNameField.setResponder(this::onNameChanged);
        this.currencyNameField.setValue("");
        this.addRenderableWidget(this.currencyNameField);
        this.addRenderableWidget(leftArrowButton);
        this.addRenderableWidget(rightArrowButton);
        this.addRenderableWidget(addButton);
    }

    private void renderCurrencyList(GuiGraphics guiGraphics) {
        if (ClientPayloadHandler.updatedCurrencies != null) {
            int yOffset = 88; // Starting Y position for rendering
            for (CurrencyData currency : ClientPayloadHandler.updatedCurrencies) {
                // Add the button first
                ImageButton currencyButton = new ImageButton(this.leftPos + 5, this.topPos + yOffset, 89, 16, ENCHANTMENT_SLOT_SPRITE, Button -> {
                    // Button action here
                });
                this.addRenderableWidget(currencyButton);
                yOffset += 16; // Increment Y position for the next currency
            }
        }
    }

    private void renderCurrencyDetails(GuiGraphics guiGraphics) {
        if (ClientPayloadHandler.updatedCurrencies != null) {
            int yOffset = 88; // Starting Y position for rendering
            guiGraphics.drawString(this.font, "Coin & value:", this.leftPos + 130, this.topPos + yOffset + 4, 4210752, false);
            guiGraphics.drawString(this.font, "1", this.leftPos + 140, this.topPos + yOffset + 14, 4210752, false);
            guiGraphics.drawString(this.font, "0", this.leftPos + 160, this.topPos + yOffset + 14, 4210752, false);
        }
        // Render the currency details here
        // You can use guiGraphics.drawString() to draw text or other methods to draw UI elements
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        this.titleLabelY = 42;
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, "Currencies", this.titleLabelX, this.titleLabelY + 36, 4210752, false);
        guiGraphics.drawString(this.font, "Currency Details", this.titleLabelX + 120, this.titleLabelY + 36, 4210752, false);
    }

    private void onNameChanged(String name) {
        this.minecraft.player.connection.send(new ServerboundRenameItemPacket(name));
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return this.currencyNameField.keyPressed(keyCode, scanCode, modifiers) || this.currencyNameField.canConsumeInput() || super.keyPressed(keyCode, scanCode, modifiers);
    }
}
