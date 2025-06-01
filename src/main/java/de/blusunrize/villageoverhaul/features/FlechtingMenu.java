package de.blusunrize.villageoverhaul.features;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

public class FlechtingMenu extends AbstractContainerMenu
{
	private final ContainerLevelAccess access;
	public final Container container;
	private final ResultContainer resultContainer;

	public FlechtingMenu(int containerId, Inventory playerInventory)
	{
		this(containerId, playerInventory, ContainerLevelAccess.NULL);
	}

	public FlechtingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access)
	{
		super(FletchingFeature.MENU_FLETCHING.get(), containerId);
		this.access = access;
		this.container = new SimpleContainer(3)
		{
			public void setChanged()
			{
				FlechtingMenu.this.slotsChanged(this);
				super.setChanged();
			}
		};
		this.resultContainer = new ResultContainer()
		{
			public void setChanged()
			{
				FlechtingMenu.this.slotsChanged(this);
				super.setChanged();
			}
		};

		this.addSlot(new Slot(this.container, 0, 44, 14)
		{
			public boolean mayPlace(ItemStack stack)
			{
				return stack.is(FletchingFeature.TAG_ARROWHEAD);
			}
		});
		this.addSlot(new Slot(this.container, 1, 44, 33)
		{
			public boolean mayPlace(ItemStack stack)
			{
				return stack.is(Tags.Items.RODS_WOODEN);
			}
		});
		this.addSlot(new Slot(this.container, 2, 44, 52)
		{
			public boolean mayPlace(ItemStack stack)
			{
				return stack.is(FletchingFeature.TAG_FLETCHING);
			}
		});

		this.addSlot(new Slot(this.resultContainer, 3, 116, 33)
		{
			public boolean mayPlace(ItemStack stack)
			{
				return false;
			}

			public void onTake(Player player, ItemStack stack)
			{
				for(int i = 0; i < 3; i++)
					FlechtingMenu.this.slots.get(i).remove(1);
				stack.getItem().onCraftedBy(stack, player.level(), player);
				access.execute((p_39219_, p_39220_) -> {
					long l = p_39219_.getGameTime();
				});
				super.onTake(player, stack);
			}
		});


		for(int k = 0; k < 3; k++)
			for(int i1 = 0; i1 < 9; i1++)
				this.addSlot(new Slot(playerInventory, i1+k*9+9, 8+i1*18, 84+k*18));
		for(int l = 0; l < 9; l++)
			this.addSlot(new Slot(playerInventory, l, 8+l*18, 142));
	}

	@Override
	public boolean stillValid(Player player)
	{
		return stillValid(this.access, player, Blocks.FLETCHING_TABLE);
	}

	@Override
	public void slotsChanged(Container inventory)
	{
		ItemStack tip = this.container.getItem(0);
		ItemStack stick = this.container.getItem(1);
		ItemStack fletching = this.container.getItem(2);
		ItemStack output = this.resultContainer.getItem(3);

		if(!tip.isEmpty()&&!stick.isEmpty()&&!fletching.isEmpty())
			this.setupResultSlot(tip, stick, fletching, output);
		else
			this.resultContainer.removeItemNoUpdate(2);
	}

	private void setupResultSlot(ItemStack tip, ItemStack stick, ItemStack fletching, ItemStack currentOutput)
	{
		ItemStack tempOutput = new ItemStack(Items.ARROW, 6);
		if(!ItemStack.matches(tempOutput, currentOutput))
		{
			this.resultContainer.setItem(2, tempOutput);
			this.broadcastChanges();
		}
	}

	@Override
	public boolean canTakeItemForPickAll(ItemStack stack, Slot slot)
	{
		return slot.container!=this.resultContainer&&super.canTakeItemForPickAll(stack, slot);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index)
	{
		ItemStack output = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if(slot!=null&&slot.hasItem())
		{
			ItemStack slotItem = slot.getItem();
			output = slotItem.copy();
			if(index==3)
			{
				slotItem.getItem().onCraftedBy(slotItem, player.level(), player);
				if(!this.moveItemStackTo(slotItem, 4, 40, true))
					return ItemStack.EMPTY;

				slot.onQuickCraft(slotItem, output);
			}
			else if(index > 3)
			{
				for(int i = 0; i < 3; i++)
				{
					Slot ingredientSlot = this.getSlot(i);
					if(ingredientSlot.mayPlace(slotItem)&&!this.moveItemStackTo(slotItem, ingredientSlot.index, ingredientSlot.index+1, false))
						return ItemStack.EMPTY;
				}

				if(index < 30)
				{
					if(!this.moveItemStackTo(slotItem, 30, 39, false))
						return ItemStack.EMPTY;
				}
				else if(index < 39&&!this.moveItemStackTo(slotItem, 3, 30, false))
					return ItemStack.EMPTY;
			}
			else if(!this.moveItemStackTo(slotItem, 4, 40, false))
				return ItemStack.EMPTY;

			if(slotItem.isEmpty())
				slot.setByPlayer(ItemStack.EMPTY);

			slot.setChanged();
			if(slotItem.getCount()==output.getCount())
				return ItemStack.EMPTY;

			slot.onTake(player, slotItem);
			this.broadcastChanges();
		}

		return output;
	}

	@Override
	public void removed(Player player)
	{
		super.removed(player);
		this.resultContainer.removeItemNoUpdate(2);
		this.access.execute((p_39152_, p_39153_) -> {
			this.clearContainer(player, this.container);
		});
	}
}
