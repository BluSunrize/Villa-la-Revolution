package de.blusunrize.villageoverhaul.features;

import de.blusunrize.villageoverhaul.VillageOverhaul;
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
	private static final ResourceLocation BG_LOCATION = ResourceLocation.fromNamespaceAndPath(VillageOverhaul.MODID, "textures/gui/fletching_table.png");

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
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
	{
		int i = this.leftPos;
		int j = this.topPos;
		guiGraphics.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}
}