package me.GuardThiver.tasks;

import net.runelite.api.coords.WorldPoint;
import simple.hooks.scripts.task.Task;
import simple.hooks.wrappers.SimpleItem;
import simple.hooks.wrappers.SimpleObject;
import simple.robot.api.ClientContext;

public class BankTask extends Task {

	private final WorldPoint BANK_TILE = new WorldPoint(2947, 3369, 0);
	private final WorldPoint Fal = new WorldPoint(2964, 3382, 0);

	public BankTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean condition() {
		SimpleItem lobster = ctx.inventory.populate().filter(379).next();
		return (lobster == null);
	}

	@Override
	public void run() {

		if (ctx.players.getLocal().getLocation().distanceTo(BANK_TILE) > 3) {
			ctx.pathing.step(BANK_TILE);
			ctx.sleepCondition(() -> ctx.players.getLocal().getLocation().distanceTo(BANK_TILE) <= 3, 5000);

		} else {

			if (!ctx.bank.bankOpen()) {
				SimpleObject bank = ctx.objects.populate().filter("Bank booth").nextNearest();
				if (bank != null && bank.validateInteractable()) {
					bank.click("Bank");
					ctx.sleepCondition(() -> ctx.players.getLocal().getAnimation() != -1, 5000);
				}
			}

			if (ctx.bank.bankOpen()) {
				ctx.bank.withdraw(379, 25);
				ctx.sleepCondition((() -> ctx.inventory.populate().filter(379).population(true) >=1), 5000);
				ctx.bank.closeBank();
				ctx.sleepCondition((() -> ctx.inventory.populate().filter(379).population(true) >=1), 5000);
				if (ctx.players.getLocal().getHealth() <= 9) {
					SimpleItem Lobster = ctx.inventory.populate().filter(379).next();
					if (Lobster != null) {
						if (Lobster.click("Eat")) {
							ctx.sleepCondition(() -> ctx.players.getLocal().getHealth() > 9, 2000);
							ctx.pathing.step(Fal);
						}

					}
				}
			}
		}
	}

	@Override
	public String status() {
		return "Banking";
	}

}
