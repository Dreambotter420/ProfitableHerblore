package script.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dreambot.api.Client;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;


public class Combatz {
	public static List<Integer> foods = new ArrayList<Integer>();
	public static List<Integer> highFoods = new ArrayList<Integer>();
	public static Timer foodEatTimer = null;
	public static Timer foodAttemptTimer = null;
	public static int lowFood = -1;
	public static int nextRandMeleeBoostLvl = 0;
	public static int nextRandRangedBoostLvl = 0;
	public static void initializeFoods()
	{
		lowFood = id.bass;
		foods.add(id.pineapplePizza1);
		foods.add(id.jugOfWine);
		foods.add(id.bass);
		foods.add(id.swordfish);
		foods.add(id.monkfish);
		foods.add(id.pineapplePizza2);
		foods.add(id.summerPie1_2);
		foods.add(id.summerPie);
		foods.add(id.seaTurtle);
	}
	public static Item getFood()
	{
		return Inventory.get(InvEquip.getInvyItem(foods));
	}
	public static boolean hasAntidoteProtection()
	{
		if(PlayerSettings.getConfig(102) < 0 && PlayerSettings.getConfig(102) >= -40)
		{
			return true;
		}
		return false;
	}
	public static Timer antidoteDrinkTimer = null;
	
	public static boolean toggleAutoRetaliate(boolean on)
	{
		if(Combat.isAutoRetaliateOn() == on) return true;
		if(!Tabs.isOpen(Tab.COMBAT))
		{
			if(Tabs.open(Tab.COMBAT)) MethodProvider.sleepUntil(() -> Tabs.isOpen(Tab.COMBAT),Sleep.calculate(420, 696));
			return false;
		}
		if(Combat.toggleAutoRetaliate(on)) MethodProvider.sleepUntil(() -> Combat.isAutoRetaliateOn() == on, Sleep.calculate(2222, 2222));
		return Combat.isAutoRetaliateOn() == on;
	}
	
	public static int nextFoodHP = 0;
	public static boolean shouldEatFood(int maxHit)
    {
    	if(nextFoodHP == 0 || nextFoodHP < maxHit)
    	{
    		int tmp = (int) Calculations.nextGaussianRandom((maxHit + 3), maxHit);
    		if(tmp > Skills.getRealLevel(Skill.HITPOINTS)) nextFoodHP = (Skills.getRealLevel(Skill.HITPOINTS) - 2);
    		else if(tmp < maxHit) nextFoodHP = maxHit;
    		else nextFoodHP = tmp;
    	}
    	if(Skills.getBoostedLevels(Skill.HITPOINTS) <= nextFoodHP)
    	{
    		return true;
    	}
    	return false;
    }
	public static boolean eatFood()
	{
		if(foodAttemptTimer != null && !foodAttemptTimer.isPaused() && !foodAttemptTimer.finished())
		{
			MethodProvider.log("attempted to eat food: " +foodAttemptTimer.elapsed()+"ms ago, continuing");
			return false;
		}
		Timer timer = new Timer(5000);
		while(!timer.finished() && Client.getGameState() == GameState.LOGGED_IN
				&& ScriptManager.getScriptManager().isRunning() && !ScriptManager.getScriptManager().isPaused())
		{
			Sleep.sleep(69, 69);
			if(Tabs.isOpen(Tab.INVENTORY))
			{
				if(foodEatTimer != null && !foodEatTimer.finished())
				{
					MethodProvider.log("Called eatFood function, but food after-eat-timer still running! Waiting...");
					continue;
				}
				Item food = getFood();
				if(food == null) return false;
				final int foodID = food.getID();
				String action = null;
				for(String act : food.getActions())
				{
					if(act == null) continue;
					if(act.equals("Eat")) 
					{
						action = "Eat";
						break;
					}
					else if(act.equals("Drink")) 
					{
						action = "Drink";
						break;
					}
				}
				
				if(Inventory.interact(foodID, action))
				{
					MethodProvider.log("Attempted to eat food!");
					nextFoodHP = 0;
					foodAttemptTimer = new Timer(600);
					foodEatTimer = new Timer(1200); // when u eat a food, it waits until next tick to actually eat it.
					//once food is eaten then the game needs to wait 2 more ticks until you can attempt to eat another food
					//if u attempt to eat another food on the 2nd tick after, you will eat the food on the end of the 2nd tick
					//after 2nd tick after 1st eat completes, any more food eating is OK but will only register on end of tick
					return true;
				}
			}
			else
			{
				if(Widgets.isOpen())
				{
					Widgets.closeAll();
					Sleep.sleep(111, 111);
				}
				else Tabs.open(Tab.INVENTORY);
			}
		}
		return false;
	}
	
}
