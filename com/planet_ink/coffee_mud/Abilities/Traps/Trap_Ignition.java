package com.planet_ink.coffee_mud.Abilities.Traps;
import com.planet_ink.coffee_mud.Abilities.StdAbility;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Trap_Ignition extends StdTrap
{
	public String ID() { return "Trap_Ignition"; }
	public String name(){ return "ignition trap";}
	protected int canAffectCode(){return Ability.CAN_ITEMS;}
	protected int canTargetCode(){return 0;}
	protected int trapLevel(){return 8;}
	public String requiresToSet(){return "a container of lamp oil";}
	public Environmental newInstance(){	return new Trap_Ignition();}

	private Item getPoison(MOB mob)
	{
		if(mob==null) return null;
		if(mob.location()==null) return null;
		for(int i=0;i<mob.location().numItems();i++)
		{
			Item I=mob.location().fetchItem(i);
			if((I!=null)
			&&(I instanceof Drink)
			&&(((((Drink)I).containsDrink())&&(((Drink)I).liquidType()==EnvResource.RESOURCE_LAMPOIL)))
			   ||(I.material()==EnvResource.RESOURCE_LAMPOIL))
				return I;
		}
		return null;
	}

	public Trap setTrap(MOB mob, Environmental E, int classLevel, int qualifyingClassLevel)
	{
		if(E==null) return null;
		Item I=getPoison(mob);
		if((I!=null)&&(I instanceof Drink))
		{
			((Drink)I).setLiquidHeld(0);
			I.destroy();
		}
		return super.setTrap(mob,E,classLevel,qualifyingClassLevel);
	}

	public boolean canSetTrapOn(MOB mob, Environmental E)
	{
		if(!super.canSetTrapOn(mob,E)) return false;
		Item I=getPoison(mob);
		if((I==null)
		&&(mob!=null))
		{
			mob.tell("You'll need to set down a container of lamp oil first.");
			return false;
		}
		return true;
	}
	public void spring(MOB target)
	{
		if((target!=invoker())&&(target.location()!=null))
		{
			if(Dice.rollPercentage()<=target.charStats().getSave(CharStats.SAVE_TRAPS))
				target.location().show(target,null,null,CMMsg.MASK_GENERAL|CMMsg.MSG_NOISE,"<S-NAME> avoid(s) setting off a trap!");
			else
			if(target.location().show(target,target,this,CMMsg.MASK_GENERAL|CMMsg.MSG_NOISE,"<S-NAME> set(s) off a trap! "+Util.capitalize(affected.name())+" ignites!"))
			{
				super.spring(target);
				Ability B=CMClass.getAbility("Burning");
				if(B!=null)
				{
					B.setProfficiency(trapLevel()/5);
					B.invoke(invoker(),affected,true);
				}
				if((canBeUninvoked())&&(affected instanceof Item))
					disable();
			}
		}
	}
}
