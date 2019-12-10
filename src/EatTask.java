package me.GuardThiver.tasks;

import simple.hooks.scripts.task.Task;
import simple.hooks.wrappers.SimpleItem;
import simple.robot.api.ClientContext;

public class EatTask extends Task {

	public EatTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean condition() {
		return ctx.combat.health() < 9;
	}

	@Override
	public void run() {
		
			SimpleItem Lobster = ctx.inventory.populate().filter(379).next();
			if (Lobster != null) {
				if (Lobster.click("Eat")) {
					ctx.sleepCondition(() -> ctx.combat.health() > 9, 2000);

				}
			}
	}

	@Override
	public String status() {
		return "Eating";
	}

}