package com.planet_ink.coffee_mud.Abilities.Traps;
import com.planet_ink.coffee_mud.Abilities.StdAbility;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Trap_Popper extends StdTrap
{
	public String ID() { return "Trap_Popper"; }
	public String name(){ return "popping noise";}
	protected int canAffectCode(){return Ability.CAN_ITEMS;}
	protected int canTargetCode(){return 0;}
	protected int trapLevel(){return 1;}
	public String requiresToSet(){return "";}
	public Environmental newInstance(){	return new Trap_Popper();}

	public void spring(MOB target)
	{
		if((target!=invoker())&&(target.location()!=null))
		{
			if(Dice.rollPercentage()<=target.charStats().getSave(CharStats.SAVE_TRAPS))
				target.location().show(target,null,null,CMMsg.MASK_GENERAL|CMMsg.MSG_NOISE,"<S-NAME> avoid(s) setting off a noise trap!");
			else
			if(target.location().show(target,target,this,CMMsg.MASK_GENERAL|CMMsg.MSG_NOISE,"<S-NAME> set(s) off a **POP** trap!"))
			{
				super.spring(target);
				Area A=target.location().getArea();
				for(Enumeration e=A.getMap();e.hasMoreElements();)
				{
					Room R=(Room)e.nextElement();
					if(R!=target.location())
						R.showHappens(CMMsg.MASK_GENERAL|CMMsg.MSG_NOISE,"You hear a loud **POP** coming from somewhere.");
				}
				if((canBeUninvoked())&&(affected instanceof Item))
					disable();
			}
		}
	}
}
