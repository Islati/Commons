package com.caved_in.commons.menu.menus.confirmation;

import com.caved_in.commons.item.Wool;
import com.caved_in.commons.menu.inventory.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

public class ConfirmationMenu extends ItemMenu {
	private ConfirmationMenuItem confirmItem = null;
	private ConfirmationMenuItem denyItem = null;

	private boolean actionPerformed = false;

	public static ConfirmationMenu of(String title) {
		return new ConfirmationMenu(title);
	}

	protected ConfirmationMenu(String title) {
		super(title, 1);
		confirmItem = new ConfirmationMenuItem(MenuConfirmationOption.CONFIRM, ItemMenu::closeMenu);
		denyItem = new ConfirmationMenuItem(MenuConfirmationOption.DENY, ItemMenu::closeMenu);
	}

	public ConfirmationMenu onConfirm(Action onConfirm) {
		confirmItem = new ConfirmationMenuItem(MenuConfirmationOption.CONFIRM, onConfirm);
		setItem(MenuConfirmationOption.CONFIRM.getSlot(), confirmItem);
		return this;
	}

	public ConfirmationMenu onDeny(Action onDeny) {
		denyItem = new ConfirmationMenuItem(MenuConfirmationOption.DENY, onDeny);
		setItem(MenuConfirmationOption.DENY.getSlot(), denyItem);
		return this;
	}

	public ConfirmationMenu onOpen(MenuOpenBehaviour onOpen) {
		addBehaviour(MenuAction.OPEN, onOpen);
		return this;
	}

	public ConfirmationMenu onClose(MenuCloseBehaviour onClose) {
		addBehaviour(MenuAction.CLOSE, onClose);
		return this;
	}

	public ConfirmationMenu exitOnClickOutside(boolean exit) {
		setExitOnClickOutside(exit);
		return this;
	}

	public ConfirmationMenu denyOnClose() {
		addBehaviour(MenuAction.CLOSE, (menu, player) -> {
			if (actionPerformed) {
				return;
			}

			denyItem.action.perform(menu, player);
		});
		return this;
	}

	public enum MenuConfirmationOption {
		CONFIRM(Wool.GREEN_WOOL, 0),
		DENY(Wool.RED_WOOL, 8);

		private MaterialData icon;
		private int slot;

		MenuConfirmationOption(MaterialData item, int slot) {
			this.icon = item;
			this.slot = slot;
		}

		public MaterialData getIcon() {
			return icon;
		}

		public int getSlot() {
			return slot;
		}
	}

	public class ConfirmationMenuItem extends MenuItem {
		private Action action;

		public ConfirmationMenuItem(MenuConfirmationOption option, Action action) {
			super(option == MenuConfirmationOption.CONFIRM ? "&a&lConfirm" : "&c&lDeny", option.getIcon());
			this.action = action;
		}

		@Override
		public void onClick(Player player, ClickType type) {
			actionPerformed = true;
			action.perform(getMenu(), player);
		}
	}

	@FunctionalInterface
	public interface Action {

		void perform(ItemMenu menu, Player player);

	}
}
