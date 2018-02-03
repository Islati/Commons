package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.util.Set;

public class DebugReflectionsPackageScan implements DebugAction {
	@Override
	public void doAction(Player player, String... args) {
		Reflections reflections = new Reflections("com.caved_in.commons.debug.actions");
//        Reflections reflections = new Reflections(
//                new ConfigurationBuilder()
//                        .setUrls(ClasspathHelper.forPackage("com.caved_in.commons.debug.actions"))
//                        .setScanners(new SubTypesScanner())
//                        .filterInputsBy(new FilterBuilder().includePackage("com.caved_in.commons.debug.actions"))
//        );

		Set<Class<? extends DebugAction>> debugActions = reflections.getSubTypesOf(DebugAction.class);

		for (Class<? extends DebugAction> clazz : debugActions) {
			try {
				DebugAction instance = clazz.newInstance();
				Chat.format(player, "&c%sFound Debug Action: &e%s&c,&6 name &c-> &6%s", clazz.getCanonicalName(), instance.getActionName());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getActionName() {
		return "reflection_package_scan";
	}
}
