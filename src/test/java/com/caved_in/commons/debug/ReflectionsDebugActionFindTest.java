package com.caved_in.commons.debug;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.reflections.Reflections;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class ReflectionsDebugActionFindTest {

	@Test
	public void reflectForDebugActions() {
		Reflections reflections = new Reflections("com.caved_in.commons.debug.actions");

		Set<Class<? extends DebugAction>> debugActions = reflections.getSubTypesOf(DebugAction.class);

		for (Class<? extends DebugAction> action : debugActions) {
			String className = action.getCanonicalName();
			System.out.println(String.format("Action class is '%s'", className));
		}

		assertThat(debugActions.size() > 0);
	}
}
