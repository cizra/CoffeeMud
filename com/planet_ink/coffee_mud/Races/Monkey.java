package com.planet_ink.coffee_mud.Races;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Monkey extends StdRace
{
	public String ID(){	return "Monkey"; }
	public String name(){ return "Monkey"; }
	public int shortestMale(){return 18;}
	public int shortestFemale(){return 18;}
	public int heightVariance(){return 6;}
	public int lightestWeight(){return 50;}
	public int weightVariance(){return 60;}
	public long forbiddenWornBits(){return Integer.MAX_VALUE-Item.ON_HEAD-Item.ON_FEET-Item.ON_NECK-Item.HELD-Item.WIELD-Item.ON_EARS-Item.ON_EYES;}
	public String racialCategory(){return "Primate";}

	//                                an ey ea he ne ar ha to le fo no gi mo wa ta wi
	private static final int[] parts={0 ,2 ,2 ,1 ,1 ,2 ,2 ,1 ,2 ,2 ,1 ,0 ,1 ,1 ,1 ,0 };
	public int[] bodyMask(){return parts;}

	protected static Vector resources=new Vector();
	public boolean playerSelectable(){return false;}

	public void affectCharStats(MOB affectedMOB, CharStats affectableStats)
	{
		super.affectCharStats(affectedMOB, affectableStats);
		affectableStats.setStat(CharStats.STRENGTH,13);
		affectableStats.setStat(CharStats.DEXTERITY,15);
		affectableStats.setStat(CharStats.INTELLIGENCE,1);
	}
	public Weapon myNaturalWeapon()
	{ return funHumanoidWeapon();	}
	public String healthText(MOB mob)
	{
		double pct=(Util.div(mob.curState().getHitPoints(),mob.maxState().getHitPoints()));

		if(pct<.10)
			return "^r" + mob.name() + "^r is hovering on deaths door!^N";
		else
		if(pct<.20)
			return "^r" + mob.name() + "^r is covered in blood and matted hair.^N";
		else
		if(pct<.30)
			return "^r" + mob.name() + "^r is bleeding badly from lots of wounds.^N";
		else
		if(pct<.40)
			return "^y" + mob.name() + "^y has large patches of bloody matted fur.^N";
		else
		if(pct<.50)
			return "^y" + mob.name() + "^y has some bloody matted fur.^N";
		else
		if(pct<.60)
			return "^p" + mob.name() + "^p has a lot of cuts and gashes.^N";
		else
		if(pct<.70)
			return "^p" + mob.name() + "^p has a few cut patches.^N";
		else
		if(pct<.80)
			return "^g" + mob.name() + "^g has a cut patch of fur.^N";
		else
		if(pct<.90)
			return "^g" + mob.name() + "^g has some disheveled fur.^N";
		else
		if(pct<.99)
			return "^g" + mob.name() + "^g has some misplaced hairs.^N";
		else
			return "^c" + mob.name() + "^c is in perfect health^N";
	}

	public void executeMsg(Environmental myHost, CMMsg msg)
	{
		super.executeMsg(myHost,msg);
		// the sex rules
		if(!(myHost instanceof MOB)) return;

		MOB myChar=(MOB)myHost;
		if((msg.amITarget(myChar))
		&&(Dice.rollPercentage()<10)
		&&(msg.tool()!=null)
		&&(msg.tool().ID().equals("Social"))
		&&(msg.tool().Name().equals("MATE <T-NAME>")
			||msg.tool().Name().equals("SEX <T-NAME>")))
		{
			Ability A=CMClass.getAbility("Disease_Aids");
			if(A!=null)
				A.invoke(msg.source(),myChar,true);
		}
	}

	public Vector myResources()
	{
		synchronized(resources)
		{
			if(resources.size()==0)
			{
				resources.addElement(makeResource
				("a "+name().toLowerCase()+" hide",EnvResource.RESOURCE_FUR));
				resources.addElement(makeResource
				("some "+name().toLowerCase()+" fingers",EnvResource.RESOURCE_HIDE));
				resources.addElement(makeResource
				("a pound of "+name().toLowerCase()+" flesh",EnvResource.RESOURCE_MEAT));
				resources.addElement(makeResource
				("some "+name().toLowerCase()+" blood",EnvResource.RESOURCE_BLOOD));
				resources.addElement(makeResource
				("a pile of "+name().toLowerCase()+" bones",EnvResource.RESOURCE_BONE));
			}
		}
		return resources;
	}
}
