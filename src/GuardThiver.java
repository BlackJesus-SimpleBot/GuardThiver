package GuardThiver;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.GuardThiver.tasks.BankTask;
import me.GuardThiver.tasks.StealTask;
import simple.hooks.filters.SimpleSkills.Skills;
import simple.hooks.scripts.Category;
import simple.hooks.scripts.ScriptManifest;
import simple.hooks.scripts.task.Task;
import simple.hooks.scripts.task.TaskScript;
import simple.hooks.simplebot.ChatMessage;

@ScriptManifest(author = "BlackJesus", category = Category.THIEVING, description = "Pickpocket's guard's at falador bank's/eat's/opens pouches",
discord = "BlackJesus#7321", name = "Guard Thiver", servers = { "Zenyte" }, version = "0.2")

public class GuardThiver extends TaskScript {
	private long startTime = 0;
    private long startingThievingLevel;
    private long startingThievingExp;
    
    private List<Task> tasks = new ArrayList<Task>();
    
    @Override
    public void onExecute() {
    	startTime = System.currentTimeMillis();
        startingThievingLevel = ctx.skills.realLevel(Skills.THIEVING);
        startingThievingExp = ctx.skills.experience(Skills.THIEVING);
        tasks.addAll(Arrays.asList(new BankTask(ctx), new StealTask(ctx)));// Adds our tasks to our {task} list for execution
        System.out.println("Started GuardThiver!");
    }
    
    @Override
    public List<Task> tasks() {
        return tasks;// Tells our TaskScript these are the tasks we want executed
    }

    @Override
    public boolean prioritizeTasks() {
        return true;// Will prioritize tasks in order added in our {tasks} List
    }
    
    // This method is not needed as the TaskScript class will call it, itself
    @Override
    public void onProcess() {
        // Can add anything here before tasks have been ran
        super.onProcess();// Needed for the TaskScript to process the tasks
        //Can add anything here after tasks have been ran
    }

    @Override
    public void onTerminate() {
    }
    
    @Override public void onChatMessage(ChatMessage e) {}
    

    @Override
    public void paint(Graphics g) {
    	long runTime = System.currentTimeMillis() - startTime;
        long currentThievingLevel = ctx.skills.realLevel(Skills.THIEVING);
        long currentThievingExp = ctx.skills.experience(Skills.THIEVING);
        long thievingLevelsGained = currentThievingLevel - startingThievingLevel;
        long thievingExpGained = currentThievingExp - startingThievingExp;

        g.setColor(Color.GREEN);
        g.drawString("Runtime: " + formatTime(runTime), 4, 250);
        g.drawString("Starting Thieving Level: " + startingThievingLevel + " + (" + thievingLevelsGained + ")", 4, 270);
        g.drawString("Current Thieving Level: " + currentThievingLevel, 4, 290);
        g.drawString("Thieving Exp Gained: " + thievingExpGained, 4, 310);
    }
    private String formatTime(final long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

}
