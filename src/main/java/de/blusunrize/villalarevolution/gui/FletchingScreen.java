package de.blusunrize.villalarevolution.gui;

import de.blusunrize.villalarevolution.VillaLaRevolution;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FletchingScreen extends AbstractContainerScreen<FlechtingMenu>
{
	private static final ResourceLocation BG_LOCATION = ResourceLocation.fromNamespaceAndPath(VillaLaRevolution.MODID, "textures/gui/fletching_table.png");

	private static final Component[] SLOT_TOOLTIPS = {
			Component.translatable("villalarevolution.fletching.slot.head"),
			Component.translatable("villalarevolution.fletching.slot.shaft"),
			Component.translatable("villalarevolution.fletching.slot.fletching"),
			Component.translatable("villalarevolution.fletching.slot.additional")
	};

	public FletchingScreen(FlechtingMenu menu, Inventory playerInventory, Component title)
	{
		super(menu, playerInventory, title);
		this.titleLabelY -= 2;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
	{
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if(this.hoveredSlot!=null
				&&this.hoveredSlot.index < SLOT_TOOLTIPS.length
				&&!this.hoveredSlot.hasItem()
				&&this.menu.getCarried().isEmpty())
			guiGraphics.renderTooltip(this.font, SLOT_TOOLTIPS[this.hoveredSlot.index], mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
	{
		int i = this.leftPos;
		int j = this.topPos;
		guiGraphics.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}
}