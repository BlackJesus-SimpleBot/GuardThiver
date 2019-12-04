package me.GuardThiver.tasks;

import simple.hooks.scripts.task.Task;
import simple.hooks.wrappers.SimpleItem;
import simple.hooks.wrappers.SimpleNpc;
import simple.robot.api.ClientContext;

public class StealTask extends Task {

	public StealTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean condition() {
		return ctx.players.getLocal().getHealth() >= 9;
	}

	@Override
	public void run() {
		int coinPouchCount = ctx.inventory.populate().filter(22526).population(true);

		if (coinPouchCount >= 27) {
			SimpleItem coinPouch = ctx.inventory.populate().filter(22526).next();
			if (coinPouch != null) {
				if (coinPouch.click("Open-all")) {
					ctx.sleepCondition(() -> ctx.inventory.populate().filter(22526).population(true) < 27, 2000);
				}
			}
		}

		if (ctx.combat.health() <= 9) {
			SimpleItem Lobster = ctx.inventory.populate().filter(379).next();
			if (Lobster != null) {
				if (Lobster.click("Eat")) {
					ctx.sleepCondition(() -> ctx.combat.health() > 9, 2000);

				}
			}
		} else {
			if (ctx.players.getLocal().getAnimation() == 424) {
				ctx.sleepCondition(() -> ctx.players.getLocal().getAnimation() != 424, 2500);
			} else {
				ctx.sleep(1000);
			SimpleNpc Guard = ctx.npcs.populate().filter("Guard").nextNearest();
				if (Guard != null) {
					if (Guard.validateInteractable()) {
						if (Guard.click("Pickpocket")) {
							ctx.sleepCondition(() -> ctx.players.getLocal().getAnimation() != -1, 1000);
						}
					}
				}
			}
		}
		ctx.sleep(200, 1000);
	}

	@Override
	public String status() {
		return "Stealing";
	}

}
