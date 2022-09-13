package script.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;

import script.ProfitableHerblore;



public class API {
    
	public static void talkToNPC(String npcName)
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName);
		interactNPC(filter,null,false,false,null,null);
	}
	public static void talkToNPC(String npcName, String action)
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName) && n.hasAction(action);
		interactNPC(filter,action,false,false,null,null);
	}
	public static void talkToNPC(String npcName, String action, Area npcArea)
	{
		Filter<NPC> filter = n ->  n != null && n.getName().contains(npcName) && n.hasAction(action) && npcArea.contains(n);
		interactNPC(filter,action,false,false,npcArea,null);
	}
	public static void interactNPC(String npcName, String action, Area npcArea, boolean reachable, Condition condition)
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName) && n.hasAction(action) && npcArea.contains(n);
		interactNPC(filter, action, false, reachable,npcArea,condition);
	}
	public static void walkTalkToNPC(String npcName, String action, Area npcArea,String...waitPhrases)
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName) && n.hasAction(action) && npcArea.contains(n);
		interactNPC(filter, action, true, false,npcArea,null);
	}
	public static void walkTalkToNPC(String npcName, String action, Area npcArea)
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName) && n.hasAction(action) && npcArea.contains(n);
		interactNPC(filter, action, true, false,npcArea,null);
	}
	public static void walkTalkToNPC(String npcName, String action, boolean reachable, Area npcArea)
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName) && n.hasAction(action) && npcArea.contains(n);
		interactNPC(filter, action, true, true,npcArea,null);
	}
	public static void walkInteractNPC(String npcName, String action, Area npcArea, Condition condition)
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName) && n.hasAction(action) && npcArea.contains(n);
		interactNPC(filter, action, true, false,npcArea,condition);
	}
	public static void interactNPC(String npcName, String action, int combatLevel, Area npcArea, boolean reachable, Condition condition) 
	{
		Filter<NPC> filter = n -> n != null && n.getName().contains(npcName) && n.hasAction(action) && n.getLevel() == combatLevel && npcArea.contains(n);
		interactNPC(filter, action, false, true,npcArea,condition);
	}
	
	public static void interactNPC(String npcName, String action)
	{
		Filter<NPC> filter = n -> 
			n != null && 
			n.getName().contains(npcName) && 
			n.hasAction(action);
			interactNPC(filter, action, false, false,null,null);
	}
	public static void interactNPC(Filter<NPC> filter,String action,boolean walk,boolean reachable,Area area, Condition condition)
	{
		if(walk && !area.contains(ProfitableHerblore.l))
		{
			if(!Walkz.isStaminated()) Walkz.drinkStamina();
			if(Walkz.isStaminated() && Walking.getRunEnergy() > 5 && !Walking.isRunEnabled()) Walking.toggleRun();
			if(Walking.shouldWalk(6) && Walking.walk(area.getCenter())) Sleep.sleep(696, 666);
			return;
		}
		NPC npc = NPCs.closest(filter);
		if(npc == null)
		{
			MethodProvider.log("NPC null!");
			return;
		}
		if(reachable && !npc.canReach())
		{
			if(Walking.walk(npc)) Sleep.sleep(420, 696);
			return;
		}
		if(npc.distance() > 10) Walking.walk(npc);
		boolean interacted = false;
		if(action == null)
		{
			if(npc.interact()) interacted = true;
		}
		else if(npc.interact(action)) interacted = true;
		if(interacted)
		{
			if(condition == null) condition = Dialogues::inDialogue;
			final Condition finalCondition = condition;
			MethodProvider.sleepUntil(() -> finalCondition.verify(),
					() -> ProfitableHerblore.l.isMoving(),Sleep.calculate(6666, 4444),69);
		}
	}
	private static void interactGameObject(Filter<GameObject> filter,String action,boolean walkToArea,boolean reachable,Area area, Condition condition)
	{
		if(walkToArea && !area.contains(ProfitableHerblore.l))
		{
			if(!Walkz.isStaminated()) Walkz.drinkStamina();
			if(Walkz.isStaminated() && Walking.getRunEnergy() > 5 && !Walking.isRunEnabled()) Walking.toggleRun();
			if(Walking.shouldWalk(6) && Walking.walk(area.getCenter())) Sleep.sleep(696, 666);
			return;
		}
		GameObject go = GameObjects.closest(filter);
		if(go == null)
		{
			MethodProvider.log("GameObject null!");
			return;
		}
		if(reachable)
		{
			boolean reachableSurrounding = false;
			for(Tile t : go.getSurrounding())
			{
				if(t.canReach())
				{
					reachableSurrounding = true;
					break;
				}
			}
			if(reachableSurrounding == false)
			{
				if(Walking.shouldWalk(6) && Walking.walk(go)) Sleep.sleep(696, 666);
				return;
			}
			//must be able to reach it now, continue
		}
		if(go.distance() > 10) Walking.walk(go);
		boolean interacted = false;
		if(action == null)
		{
			if(go.interact()) interacted = true;
		}
		else if(go.interact(action)) interacted = true;
		if(interacted)
		{
			if(condition == null) condition = () -> go.getSurrounding().contains(ProfitableHerblore.l.getTile());
			final Condition finalCondition = condition;
			MethodProvider.sleepUntil(() -> finalCondition.verify(),
					() -> ProfitableHerblore.l.isMoving(),Sleep.calculate(6666, 4444),69);
		}
	}
	public static void walkTalkWithGameObject(String objectName, String action) {
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action);
		interactGameObject(filter, action, true,false,null,Dialogues::inDialogue);
	}
	public static void walkTalkWithGameObject(String objectName) {
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName);
		interactGameObject(filter, null, true,false,null,Dialogues::inDialogue);
	}
	public static void walkTalkWithGameObject(String objectName, Area searchArea) {
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && searchArea.contains(n);
		interactGameObject(filter, null, true,false,searchArea,Dialogues::inDialogue);
	}
	public static void walkTalkWithGameObject(String objectName, String action, Area searchArea, boolean standableTile) {
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName)  && n.hasAction(action) && searchArea.contains(n);
		interactGameObject(filter, action, true,true,searchArea,Dialogues::inDialogue);
	}
	public static void walkTalkWithGameObject(String objectName, String action, Area searchArea) {
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName)  && n.hasAction(action) && searchArea.contains(n);
		interactGameObject(filter, action, true,false,searchArea,Dialogues::inDialogue);
	}
	public static void interactWithGameObject(String objectName, String action)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName)  && n.hasAction(action);
		interactGameObject(filter, action, false,true,null,null);
	}
	public static void interactWithGameObject(String objectName, String action,Tile tile,Condition condition)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action) && n.getTile().equals(tile);
		interactGameObject(filter, action, false,true,null,condition);
	}
	public static void interactWithGameObject(String objectName, String action,Condition condition)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action);
		interactGameObject(filter, action, false,true,null,condition);
	}
	public static void interactWithGameObject(String objectName, String action,Tile tile)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action) && n.getTile().equals(tile);
		interactGameObject(filter, action, false,true,null,null);
	}
	public static void interactWithGameObject(String objectName, String action, Area gameObjectArea)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action) && gameObjectArea.contains(n);
		interactGameObject(filter, action, false,true,null,null);
	}
	public static void interactWithGameObject(String objectName, String action, Area gameObjectArea,Condition condition)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action) && gameObjectArea.contains(n);
		interactGameObject(filter, action, false,true,null,condition);
	}
	public static void walkInteractWithGameObject(String objectName, String action, Area gameObjectArea)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action) && gameObjectArea.contains(n);
		interactGameObject(filter, action, true,true,gameObjectArea,null);
	}
	public static void walkInteractWithGameObject(String objectName, String action, Area gameObjectArea, Condition condition)
	{
		Filter<GameObject> filter = n -> n != null && n.getName().contains(objectName) && n.hasAction(action) && gameObjectArea.contains(n);
		interactGameObject(filter, action, true,true,gameObjectArea,condition);
	}
	private static void interactGroundItem(Filter<GroundItem> filter,String action,boolean walkToArea,boolean reachable,Area area, Condition condition)
	{
		if(walkToArea && !area.contains(ProfitableHerblore.l))
		{
			if(!Walkz.isStaminated()) Walkz.drinkStamina();
			if(Walkz.isStaminated() && Walking.getRunEnergy() > 5 && !Walking.isRunEnabled()) Walking.toggleRun();
			if(Walking.shouldWalk(6) && Walking.walk(area.getCenter())) Sleep.sleep(696, 666);
			return;
		}
		GroundItem go = GroundItems.closest(filter);
		if(go == null)
		{
			MethodProvider.log("GroundItem null!");
			return;
		}
		if(reachable)
		{
			boolean reachableSurrounding = false;
			for(Tile t : go.getSurroundingArea(1).getTiles())
			{
				if(t.canReach())
				{
					reachableSurrounding = true;
					break;
				}
			}
			if(reachableSurrounding == false)
			{
				if(Walking.shouldWalk(6) && Walking.walk(go)) Sleep.sleep(696, 666);
				return;
			}
			//must be able to reach it now, continue
		}
		if(go.distance() > 10) Walking.walk(go);
		boolean interacted = false;
		if(action == null)
		{
			if(go.interact()) interacted = true;
		}
		else if(go.interact(action)) interacted = true;
		if(interacted)
		{
			final int count = Inventory.count(go.getID());
			if(condition == null) condition = () -> Inventory.count(go.getID()) > count;
			final Condition finalCondition = condition;
			MethodProvider.sleepUntil(() -> finalCondition.verify(),
					() -> ProfitableHerblore.l.isMoving(),Sleep.calculate(6666, 4444),69);
		}
	}
	public static void walkPickupGroundItem(int groundItemID, String action, boolean reachable,Area groundItemArea)
	{
		Filter<GroundItem> filter = n -> n != null && n.getID() == groundItemID && n.hasAction(action) && groundItemArea.contains(n);
		interactGroundItem(filter, action, true,reachable,groundItemArea,null);
	}
	public static void walkPickupGroundItem(String groundItemName, String action, Area groundItemArea)
	{
		Filter<GroundItem> filter = n -> n != null && n.getName().equals(groundItemName) && n.hasAction(action) && groundItemArea.contains(n);
		interactGroundItem(filter, action, true,true,groundItemArea,null);
	}
	public static int roundToMultiple (int number,int multiple){
	    int result = multiple;
	    //If not already multiple of given number
	    if (number % multiple != 0)
	    {
	        int division = number / multiple;
	        result = division * multiple;
	    }
	    return result;
	}
	public static int getRandomP2PWorld()
	{
		List<World> verifiedWorlds = new ArrayList<World>();
		for(World tmp : Worlds.noMinimumLevel())
		{
			if(	tmp.isMembers()
					&& !tmp.isPVP()
					&& !tmp.isTournamentWorld()
					&& !tmp.isDeadmanMode()
					&& !tmp.isHighRisk() 
					&& !tmp.isLeagueWorld()
					&& !tmp.isSuspicious()
					&& !tmp.isPvpArena() 
					&& !tmp.isTargetWorld()
					&& tmp.getWorld() != 302) //just avoid popular world)
			{
				verifiedWorlds.add(tmp);
			}
		}
		Collections.shuffle(verifiedWorlds);
		return verifiedWorlds.size() > 0 ? verifiedWorlds.get(0).getWorld() : 302; // default world 302 if none found
	}
	
	
	
	
	
	
	
	
	
	
	
}
