package HerbPrice;


import org.dreambot.api.methods.MethodProvider;

import script.ProfitableHerblore.Herb;

public class HerbPrice {
	public int grimyLow;
	public int grimyHigh;
	public int unfLow;
	public int unfHigh;
	public int profitMargin;
	public Herb herb;
	
	public HerbPrice (Herb herb, int grimyLow, int grimyHigh, int unfLow, int unfHigh)
	{
		this.herb = herb;
		this.grimyLow = grimyLow;
		this.grimyHigh = grimyHigh;
		this.unfLow = unfLow;
		this.unfHigh = unfHigh;
		this.profitMargin = unfLow - grimyHigh;
	}
	public void printHerbPrices()
	{
		MethodProvider.log("Herb ["+this.herb.toString()+"] grimyLow ["+grimyLow+"] grimyHigh ["+grimyHigh+"] UnfLow ["+unfLow+"] UnfHigh ["+unfHigh+"] profitMargin ["+profitMargin+"]");
	}
}