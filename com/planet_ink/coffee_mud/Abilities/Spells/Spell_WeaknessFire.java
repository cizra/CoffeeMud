package com.planet_ink.coffee_mud.Abilities.Spells;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;


public class Spell_WeaknessFire extends Spell
{
	public String ID() { return "Spell_WeaknessFire"; }
	public String name(){return "Weakness to Fire";}
	public String displayText(){return "(Weakness to Fire)";}
	public int quality(){return MALICIOUS;};
	protected int canAffectCode(){return CAN_MOBS;}
	public Environmental newInstance(){return new Spell_WeaknessFire();}
	public int classificationCode(){return Ability.SPELL|Ability.DOMAIN_TRANSMUTATION;}

	public boolean okMessage(Environmental myHost, CMMsg msg)
	{
		if(!super.okMessage(myHost,msg))
			return false;

		if((affected==null)||(!(affected instanceof MOB)))
			return true;

		MOB mob=(MOB)affected;
		if((msg.amITarget(mob))&&(Util.bset(msg.targetCode(),CMMsg.MASK_HURT))
		   &&(msg.sourceMinor()==CMMsg.TYP_FIRE))
		{
			int recovery=(int)Math.round(Util.mul((msg.targetCode()-CMMsg.MASK_HURT),1.5));
			SaucerSupport.adjustDamageMessage(msg,recovery);
		}
		return true;
	}

	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking,tickID)) return false;
		if(tickID!=Host.TICK_MOB) return false;
		if((affecting()!=null)&&(affecting() instanceof MOB))
		{
			MOB dummy=(MOB)affecting();
			Room room=dummy.location();
			if(room!=null)
			{
				if((room.getArea().weatherType(room)==Area.WEATHER_HEAT_WAVE)
				&&(Dice.rollPercentage()>dummy.charStats().getSave(CharStats.SAVE_FIRE)))
				{
					int damage=Dice.roll(1,8,0);
					ExternalPlay.postDamage(invoker,dummy,null,damage,CMMsg.MASK_GENERAL|CMMsg.TYP_FIRE,Weapon.TYPE_BURNING,"The scorching heat <DAMAGE> <T-NAME>!");
				}
				else
				if((room.getArea().weatherType(room)==Area.WEATHER_DUSTSTORM)
				&&(Dice.rollPercentage()>dummy.charStats().getSave(CharStats.SAVE_FIRE)))
				{
					int damage=Dice.roll(1,16,0);
					ExternalPlay.postDamage(invoker,dummy,null,damage,CMMsg.MASK_GENERAL|CMMsg.TYP_FIRE,Weapon.TYPE_BURNING,"The burning hot dust <DAMAGE> <T-NAME>!");
				}
				else
				if((room.getArea().weatherType(room)==Area.WEATHER_DROUGHT)
				&&(Dice.rollPercentage()>dummy.charStats().getSave(CharStats.SAVE_FIRE)))
				{
					int damage=Dice.roll(1,8,0);
					ExternalPlay.postDamage(invoker,dummy,null,damage,CMMsg.MASK_GENERAL|CMMsg.TYP_FIRE,Weapon.TYPE_BURNING,"The burning dry heat <DAMAGE> <T-NAME>!");
				}
			}
		}
		return true;
	}


	public void unInvoke()
	{
		// undo the affects of this spell
		if((affected==null)||(!(affected instanceof MOB)))
			return;
		MOB mob=(MOB)affected;
		if(canBeUninvoked())
			mob.tell("Your fire weakness is now gone.");

		super.unInvoke();

	}

	public void affectCharStats(MOB affectedMOB, CharStats affectedStats)
	{
		super.affectCharStats(affectedMOB,affectedStats);
		affectedStats.setStat(CharStats.SAVE_FIRE,affectedStats.getStat(CharStats.SAVE_FIRE)-100);
	}
	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto)
	{
		MOB target=getTarget(mob,commands,givenTarget);
		if(target==null) return false;

		if(!super.invoke(mob,commands,givenTarget,auto))
			return false;

		boolean success=profficiencyCheck(0,auto);
		if(success)
		{
			FullMsg msg=new FullMsg(mob,target,this,affectType(auto),auto?"A shimmering flamable field appears around <T-NAMESELF>.":"^S<S-NAME> invoke(s) a flamable field around <T-NAMESELF>.^?");
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				success=maliciousAffect(mob,target,0,-1);
			}
		}
		else
			maliciousFizzle(mob,target,"<S-NAME> attempt(s) to invoke weakness to fire, but fail(s).");

		return success;
	}
}
