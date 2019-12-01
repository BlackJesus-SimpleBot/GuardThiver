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
        return ctx.getPlayers().getLocal().getHealth() >= 10;
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

		if (ctx.getPlayers().getLocal().getHealth() < 9) {
			SimpleItem Lobster = ctx.inventory.populate().filter(379).next();
			if (Lobster != null) {
				if (Lobster.click("Eat")) {
					ctx.sleepCondition(() -> ctx.getPlayers().getLocal().getHealth() > 9, 2000);

				}
			}
		} else {
			if (ctx.getPlayers().getLocal().getAnimation() == 424) {
				ctx.sleepCondition(() -> ctx.getPlayers().getLocal().getAnimation() != 424, 2500);
			} else {
				SimpleNpc Guard = ctx.getNpcs().populate().filter("Guard").nextNearest();
				if (Guard != null) {
					if (Guard.validateInteractable()) {
						if (Guard.click("Pickpocket")) {
							ctx.sleepCondition(() -> ctx.getPlayers().getLocal().getAnimation() != -1, 500);
						}
					}
				}
			}
		}
		ctx.sleep(200, 400);
	}
    @Override
    public String status() {
        return "Stealing";
    }

}