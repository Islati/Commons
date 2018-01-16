package com.caved_in.commons.menus.confirmation;

import com.caved_in.commons.inventory.menu.*;
import com.caved_in.commons.item.Wool;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

public class ConfirmationMenu extends ItemMenu {
	private ConfirmationMenuItem confirmItem = null;
	private ConfirmationMenuItem denyItem = null;

	private boolean actionPerformed = false;

	private boolean switchMenuAfterSelection = false;
	private Menu selectionSwitchMenu;

	private boolean exitMenuAfterSelection = false;

	public static ConfirmationMenu of(String title) {
		return new ConfirmationMenu(title);
	}

	protected ConfirmationMenu(String title) {
		super(title, 1);
		confirmItem = new ConfirmationMenuItem(MenuConfirmationOption.CONFIRM, Menu::closeMenu);
		denyItem = new ConfirmationMenuItem(MenuConfirmationOption.DENY, Menu::closeMenu);
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

	public ConfirmationMenu switchAfterSelection(boolean switchAfterSelection, Menu menu) {
		this.selectionSwitchMenu = menu;
		this.switchMenuAfterSelection = switchAfterSelection;
		return this;
	}

	public ConfirmationMenu exitAfterSelection() {
		this.exitMenuAfterSelection = true;
		return this;
	}

	public ConfirmationMenu denyOnClose() {
		addBehaviour(MenuAction.CLOSE, (menu, player) -> {
			if (actionPerformed) {
				return;
			}

			denyItem.action.perform((Menu)menu, player);
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

			if (switchMenuAfterSelection) {
				getMenu().switchMenu(player,selectionSwitchMenu);
				return;
			}

			if (exitMenuAfterSelection) {
				getMenu().closeMenu(player);
			}
		}
	}

	@FunctionalInterface
	public interface Action {

		void perform(Menu menu, Player player);

	}
}
