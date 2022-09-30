package script.utilities;

import java.util.ArrayList;
import java.util.List;

import org.dreambot.api.Client;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.Shop;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.depositbox.DepositBox;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;

import script.ProfitableHerblore;

public class Walkz {

	/**
	 * returns true if have jewelry in invy or equipment. If in invy, equips and then teleports
	 * both to avoid having to handle tele menu interface, and to ensure it teleports in combat.
	 * DOES NOT CHECK BANK FOR JEWELRY! Returns false if no jewelry specified in invy or equipment.
	 * @param jewelry
	 * @param teleName
	 * @return
	 */
	public static boolean useJewelry(int jewelry, String teleName)
	{
		List<Integer> wearableJewelry = new ArrayList<Integer>();
		EquipmentSlot equipSlot = null;
		if(jewelry == InvEquip.wealth) 
			{
			equipSlot = EquipmentSlot.RING;
			wearableJewelry = InvEquip.wearableWealth;
			}
		if(jewelry == InvEquip.glory) 
			{
			equipSlot = EquipmentSlot.AMULET;
			wearableJewelry = InvEquip.wearableGlory;
			}
		if(jewelry == InvEquip.combat) 
			{
			equipSlot = EquipmentSlot.HANDS;
			wearableJewelry = InvEquip.wearableCombats;
			}
		if(jewelry == InvEquip.skills) 
			{
			equipSlot = EquipmentSlot.AMULET;
			wearableJewelry = InvEquip.wearableSkills;
			}
		if(jewelry == InvEquip.games) 
			{
			equipSlot = EquipmentSlot.AMULET;
			wearableJewelry = InvEquip.wearableGames;
			}
		if(jewelry == InvEquip.duel) 
			{
			equipSlot = EquipmentSlot.RING;
			wearableJewelry = InvEquip.wearableDuel;
			}
		if(jewelry == InvEquip.passage) 
			{
			equipSlot = EquipmentSlot.AMULET;
			wearableJewelry = InvEquip.wearablePassages;
			}
		if(InvEquip.invyContains(wearableJewelry))
		{
			final int jewelryID = InvEquip.getInvyItem(wearableJewelry);
			InvEquip.equipItem(jewelryID);
			Sleep.sleepUntil(() -> Equipment.contains(jewelryID), s.calculate(4444, 3333));
		}
		if(InvEquip.equipmentContains(wearableJewelry))
		{
			if(Shop.isOpen()) Shop.close();
			else if(Bank.isOpen()) Bank.close();
			else if(GrandExchange.isOpen()) GrandExchange.close();
			else if(DepositBox.isOpen()) DepositBox.close();
			Tabs.open(Tab.EQUIPMENT);
			Sleep.sleepUntil(() -> Tabs.isOpen(Tab.EQUIPMENT),s.calculate(3333, 2222));
			if(Tabs.isOpen(Tab.EQUIPMENT))
			{
				if(Equipment.interact(equipSlot, teleName))
				{
					Logger.log("Just used Jewelry teleport: " + teleName +" in slot: " + equipSlot);
					Sleep.sleepUntil(() -> ProfitableHerblore.l.isAnimating(),s.calculate(4444,4444));
					Sleep.sleepUntil(() -> !ProfitableHerblore.l.isAnimating(), 
							() -> ProfitableHerblore.l.isAnimating(), s.calculate(3333,2222),69);
					s.sleep(111,1111);
				}
			}
			return true;
		}
		
		return false;
	}
	public static boolean isStaminated()
	{
		if(Walking.getRunEnergy() >= 80) return true;
		return (PlayerSettings.getBitValue(25) == 0 ? false : true);
	}
	public static boolean teleportVarrock(long timeout)
	{
		return teleport(id.varrockTele,Locations.varrockTeleSpot,timeout);
	}
	public static void drinkStamina()
	{
		if(InvEquip.invyContains(id.staminas))
		{
			if(Tabs.isOpen(Tab.INVENTORY))
			{
				if(Inventory.interact(InvEquip.getInvyItem(id.staminas),"Drink"))
				{
					s.sleep(69,69);
				}
				return;
			}
			
			Tabs.open(Tab.INVENTORY);
			
		}
	}
	/**
	 * Returns true if in telespot, false otherwise. Tries to teleport using tab, withdraws or buys more.
	 * @param tabID
	 * @param teleSpot
	 * @param timeout
	 * @return
	 */
	public static boolean teleport(int tabID, Area teleSpot, long timeout)
	{
		Logger.log("Entering generic teletab function");
		Timer timer = new Timer(timeout);
		while(!timer.finished() && Client.getGameState() == GameState.LOGGED_IN
				&& ScriptManager.getScriptManager().isRunning() && !ScriptManager.getScriptManager().isPaused())
		{
			if(teleSpot.contains(ProfitableHerblore.l)) return true;
			
			s.sleep(69,69);
			
			//check if have fally tab in invy, use it
			if(Inventory.contains(tabID))
			{
				if(Bank.isOpen()) Bank.close();
				if(Tabs.open(Tab.INVENTORY))
				{
					if(Inventory.interact(tabID, "Break"))
					{
						Sleep.sleepUntil(() -> teleSpot.contains(ProfitableHerblore.l), () -> ProfitableHerblore.l.isAnimating(), s.calculate(4444,2222),50);
					}
				}
				continue;
			}
			//check if we have opened bank one time since script start
			if(!InvEquip.checkedBank()) continue;
			
			//if none found in invy OR bank, stop
			if(!Bank.contains(tabID)) 
			{
				InvEquip.buyItem(tabID, (int) Calculations.nextGaussianRandom(12,3),timeout);
				if(!InvEquip.bankContains(InvEquip.wearableWealth)) InvEquip.buyItem(InvEquip.wealth5, 3, timeout);
			}
			
			//found item in bank - withdraw it
			else InvEquip.withdrawOne(tabID, timeout);
		}
		return false;
	}
	public static boolean goToGE(long timeout)
	{
		Timer timer = new Timer(timeout);
		while(!timer.finished() && Client.getGameState() == GameState.LOGGED_IN
				&& ScriptManager.getScriptManager().isRunning() && !ScriptManager.getScriptManager().isPaused())
		{
			s.sleep(69,69);
			//check if already in good GE area
			if(Locations.clickableGEArea.contains(ProfitableHerblore.l)) return true;
			//check if within walkable area to GE
			if(Locations.dontTeleToGEAreaJustWalk.contains(ProfitableHerblore.l))
			{
				if(Walking.shouldWalk(6) && Walking.walk(BankLocation.GRAND_EXCHANGE.getTile()))
				{
					s.sleep(666, 1111);
				}
				continue;
			}
			//check if have ring of wealth equipped, use it
			if(InvEquip.equipmentContains(InvEquip.wearableWealth))
			{
				if(Tabs.isOpen(Tab.EQUIPMENT))
				{
					if(Equipment.interact(EquipmentSlot.RING, "Grand Exchange"))
					{
						Sleep.sleepUntil(() -> Locations.teleGE.contains(ProfitableHerblore.l), () -> ProfitableHerblore.l.isAnimating(),s.calculate(3333,2222),50);
					}
				}
				else
				{
					if(Widgets.isOpen()) Widgets.closeAll();
					Tabs.open(Tab.EQUIPMENT);
				}
				continue;
			}
		
			//check inventory for ring of wealth / tablet, use it
			boolean ringFound = false;
			for(int ring : InvEquip.wearableWealth)
			{
				if(Inventory.contains(ring))
				{
					InvEquip.equipItem(ring);
					ringFound = true;
					break;
				}
			}
			if(ringFound) continue;
			if(Inventory.contains(id.varrockTele))
			{
				if(Bank.isOpen()) Bank.close();
				if(InvEquip.closeBankEquipment())
				{
					teleportVarrock(30000);
				}
				continue;
			}
			
			//check if we have opened bank one time since script start
			if(!InvEquip.checkedBank()) continue;
			
			//check bank for wealth / varrock tabs
			int ring = InvEquip.getFirstInBank(InvEquip.wearableWealth);
			
			//if no ring found in equip OR invy OR bank, check bank for varrock tab
			if(ring == -1) 
			{
				if(Bank.contains(id.varrockTele))
				{
					InvEquip.withdrawOne(id.varrockTele, timeout);
					continue;
				} 
				//no options left - go to GE by walking
				else if(Walking.shouldWalk(6) && Walking.walk(Locations.GE)) s.sleep(666, 666);
			}
			//found item in bank - withdraw it
			else
			{
				InvEquip.withdrawOne(ring, timeout);
			}
		}
		
		return false;
	}
	
}
