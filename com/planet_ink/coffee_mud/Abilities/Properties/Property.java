package com.planet_ink.coffee_mud.Abilities.Properties;
import com.planet_ink.coffee_mud.Abilities.StdAbility;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.Vector;

public class Property implements Ability, Cloneable
{
	public String ID() { return "Property"; }
	public String name(){ return "a Property";}
	public String Name(){return name();}
	public String description(){return "";}
	public String displayText(){return "";}
	protected boolean borrowed=false;
	protected String miscText="";
	protected Environmental affected=null;
	protected int canAffectCode(){return 0;}
	protected int canTargetCode(){return 0;}
	public int abilityCode(){return 0;}
	public void setAbilityCode(int newCode){}
	public int adjustedLevel(MOB mob){return -1;}
	public boolean bubbleAffect(){return false;}
	public long flags(){return 0;}
	public long getTickStatus(){return Tickable.STATUS_NOT;}

	public void setName(String newName){}
	public void setDescription(String newDescription){}
	public void setDisplayText(String newDisplayText){}
	public MOB invoker(){return null;}
	public void setInvoker(MOB mob){}
	public static final String[] empty={};
	public String[] triggerStrings(){return empty;}
	public boolean invoke(MOB mob, Vector commands, Environmental target, boolean auto){return false;}
	public boolean invoke(MOB mob, Environmental target, boolean auto){return false;}
	public boolean autoInvocation(MOB mob){return false;}
	public void unInvoke(){}
	public boolean canBeUninvoked(){return false;}
	public boolean isAutoInvoked(){return true;}
	public boolean isNowAnAutoEffect(){return true;}

	public boolean canBeTaughtBy(MOB teacher, MOB student){return false;}
	public boolean canBePracticedBy(MOB teacher, MOB student){return false;}
	public boolean canBeLearnedBy(MOB teacher, MOB student){return false;}
	public void teach(MOB teacher, MOB student){}
	public void practice(MOB teacher, MOB student){}
	public int maxRange(){return Integer.MAX_VALUE;}
	public int minRange(){return Integer.MIN_VALUE;}

	public void startTickDown(MOB invokerMOB, Environmental affected, int tickTime)
	{
		if(affected.fetchAffect(ID())==null)
			affected.addAffect(this);
	}

	public int profficiency(){return 0;}
	public void setProfficiency(int newProfficiency){}
	public boolean profficiencyCheck(int adjustment, boolean auto){return false;}
	public void helpProfficiency(MOB mob){}

	public Environmental affecting(){return affected;}
	public void setAffectedOne(Environmental being){affected=being;}

	public boolean putInCommandlist(){return false;}
	public int quality(){return Ability.INDIFFERENT;}

	public int classificationCode(){ return Ability.PROPERTY;}
	public boolean isBorrowed(Environmental toMe){ return borrowed;	}
	public void setBorrowed(Environmental toMe, boolean truefalse)	{ borrowed=truefalse; }

	protected static final EnvStats envStats=new DefaultEnvStats();
	public EnvStats envStats(){return envStats;}
	public EnvStats baseEnvStats(){return envStats;}
	
	public void recoverEnvStats(){}
	public void setBaseEnvStats(EnvStats newBaseEnvStats){}
	public Environmental newInstance()
	{ return new Property();}
	
	private static final String[] CODES={"CLASS","TEXT"};
	public String[] getStatCodes(){return CODES;}
	private int getCodeNum(String code){
		for(int i=0;i<CODES.length;i++)
			if(code.equalsIgnoreCase(CODES[i])) return i;
		return -1;
	}
	public String getStat(String code){
		switch(getCodeNum(code))
		{
		case 0: return ID();
		case 1: return text();
		}
		return "";
	}
	public void setStat(String code, String val)
	{
		switch(getCodeNum(code))
		{
		case 0: return;
		case 1: setMiscText(val); break;
		}
	}
	public boolean sameAs(Environmental E)
	{
		if(!(E instanceof Property)) return false;
		for(int i=0;i<CODES.length;i++)
			if(!E.getStat(CODES[i]).equals(getStat(CODES[i])))
				return false;
		return true;
	}
	private void cloneFix(Ability E){}
	
	public Environmental copyOf()
	{
		try
		{
			Property E=(Property)this.clone();
			E.cloneFix(this);
			return E;

		}
		catch(CloneNotSupportedException e)
		{
			return this.newInstance();
		}
	}
	protected int getParmVal(String text, String key, int defaultValue)
	{
		text=text.toUpperCase();
		key=key.toUpperCase();
		int x=text.indexOf(key);
		while(x>=0)
		{
			if((x==0)||(!Character.isLetter(text.charAt(x-1))))
			{
				while((x<text.length())&&(text.charAt(x)!='=')&&(!Character.isDigit(text.charAt(x))))
					x++;
				if((x<text.length())&&(text.charAt(x)=='='))
				{
					while((x<text.length())&&(!Character.isDigit(text.charAt(x))))
						x++;
					if(x<text.length())
					{
						text=text.substring(x);
						x=0;
						while((x<text.length())&&(Character.isDigit(text.charAt(x))))
							x++;
						return Util.s_int(text.substring(0,x));
					}
				}
				x=-1;
			}
			else
				x=text.toUpperCase().indexOf(key.toUpperCase(),x+1);
		}
		return defaultValue;
	}
	
	public int compareTo(Object o){ return CMClass.classID(this).compareToIgnoreCase(CMClass.classID(o));}
	
	protected static int getVal(String text, String key)
	{
		text=text.toUpperCase();
		key=key.toUpperCase();
		int x=text.indexOf(key);
		while(x>=0)
		{
			if((x==0)||(!Character.isLetter(text.charAt(x-1))))
			{
				while((x<text.length())&&(text.charAt(x)!='+')&&(text.charAt(x)!='-'))
					x++;
				if(x<text.length())
				{
					char pm=text.charAt(x);
					while((x<text.length())&&(!Character.isDigit(text.charAt(x))))
						x++;
					if(x<text.length())
					{
						text=text.substring(x);
						x=0;
						while((x<text.length())&&(Character.isDigit(text.charAt(x))))
							x++;
						if(pm=='+')
							return Util.s_int(text.substring(0,x));
						else
							return -Util.s_int(text.substring(0,x));
					}
				}
				x=-1;
			}
			else
				x=text.toUpperCase().indexOf(key.toUpperCase(),x+1);
		}
		return 0;
	}

	protected static double getDoubleVal(String text, String key)
	{
		text=text.toUpperCase();
		key=key.toUpperCase();
		int x=text.indexOf(key);
		while(x>=0)
		{
			if((x==0)||(!Character.isLetter(text.charAt(x-1))))
			{
				while((x<text.length())&&(text.charAt(x)!='+')&&(text.charAt(x)!='-'))
					x++;
				if(x<text.length())
				{
					char pm=text.charAt(x);
					while((x<text.length())
					&&(!Character.isDigit(text.charAt(x)))
					&&(text.charAt(x)!='.'))
						x++;
					if(x<text.length())
					{
						text=text.substring(x);
						x=0;
						while((x<text.length())
						&&((Character.isDigit(text.charAt(x)))||(text.charAt(x)=='.')))
							x++;
						if(text.substring(0,x).indexOf(".")<0)
						{
							if(pm=='+')
								return new Integer(Util.s_int(text.substring(0,x))).doubleValue();
							else
								return new Integer(-Util.s_int(text.substring(0,x))).doubleValue();
						}
						else
						{
							if(pm=='+')
								return Util.s_double(text.substring(0,x));
							else
								return -Util.s_double(text.substring(0,x));
						}
					}
				}
				x=-1;
			}
			else
				x=text.toUpperCase().indexOf(key.toUpperCase(),x+1);
		}
		return 0;
	}
	
	protected static String getStr(String text, String key)
	{
		String oldText=text;
		text=text.toUpperCase();
		key=key.toUpperCase();
		int x=text.indexOf(key);
		while(x>=0)
		{
			if((x==0)||(!Character.isLetter(text.charAt(x-1))))
			{
				while((x<text.length())&&(text.charAt(x)!='='))
					x++;
				if(x<text.length())
				{
					boolean endWithQuote=false;
					while((x<text.length())&&(!Character.isLetterOrDigit(text.charAt(x))))
						if(text.charAt(x)=='\"'){ endWithQuote=true; x++; break;}
						else
							x++;
					if(x<text.length())
					{
						oldText=oldText.substring(x);
						text=text.substring(x);
						x=0;
						while((x<text.length())
							&&((Character.isLetterOrDigit(text.charAt(x)))
							||((endWithQuote)&&(text.charAt(x)!='\"'))))
							x++;
						return oldText.substring(0,x).trim();
					}

				}
				x=-1;
			}
			else
				x=text.toUpperCase().indexOf(key.toUpperCase(),x+1);
		}
		return "";
	}

	protected String getParmStr(String text, String key, String defaultValue)
	{
		text=text.toUpperCase();
		key=key.toUpperCase();
		int x=text.indexOf(key);
		while(x>=0)
		{
			if((x==0)||(!Character.isLetter(text.charAt(x-1))))
			{
				while((x<text.length())&&(text.charAt(x)!='='))
					x++;
				if((x<text.length())&&(text.charAt(x)=='='))
				{
					boolean endWithQuote=false;
					while((x<text.length())&&(!Character.isLetterOrDigit(text.charAt(x))))
						if(text.charAt(x)=='\"'){ endWithQuote=true; x++; break;}
						else
							x++;
					if(x<text.length())
					{
						text=text.substring(x);
						x=0;
						while((x<text.length())
							&&((Character.isLetterOrDigit(text.charAt(x)))
							||((endWithQuote)&&(text.charAt(x)!='\"'))))
							x++;
						return text.substring(0,x).trim();
					}
				}
				x=-1;
			}
			else
				x=text.toUpperCase().indexOf(key.toUpperCase(),x+1);
		}
		return defaultValue;
	}
	
	public void setMiscText(String newMiscText)
	{ miscText=newMiscText;}
	public String text()
	{ return miscText;}
	public boolean appropriateToMyAlignment(int alignment){return true;}
	public String accountForYourself(){return "";}
	public int affectType(){return 0;}
	public String requirements(){return "";}

	public boolean canAffect(Environmental E)
	{
		if((E==null)&&(canAffectCode()==0)) return true;
		if(E==null) return false;
		if((E instanceof MOB)&&((canAffectCode()&Ability.CAN_MOBS)>0)) return true;
		if((E instanceof Item)&&((canAffectCode()&Ability.CAN_ITEMS)>0)) return true;
		if((E instanceof Exit)&&((canAffectCode()&Ability.CAN_EXITS)>0)) return true;
		if((E instanceof Room)&&((canAffectCode()&Ability.CAN_ROOMS)>0)) return true;
		if((E instanceof Area)&&((canAffectCode()&Ability.CAN_AREAS)>0)) return true;
		return false;
	}
	
	public boolean canTarget(Environmental E)
	{ return false;}

	public void affectEnvStats(Environmental affected, EnvStats affectableStats)
	{}
	public void affectCharStats(MOB affectedMob, CharStats affectableStats)
	{}
	public void affectCharState(MOB affectedMob, CharState affectableMaxState)
	{}
	public void affect(Environmental myHost, Affect affect)
	{
		return;
	}
	public boolean okAffect(Environmental myHost, Affect affect)
	{
		return true;
	}
	public boolean tick(Tickable ticking, int tickID)
	{ return true;	}
	public void makeLongLasting(){}
	public void makeNonUninvokable(){}


	public void addAffect(Ability to){}
	public void addNonUninvokableAffect(Ability to){}
	public void delAffect(Ability to){}
	public int numAffects(){ return 0;}
	public Ability fetchAffect(int index){return null;}
	public Ability fetchAffect(String ID){return null;}
	public void addBehavior(Behavior to){}
	public void delBehavior(Behavior to){}
	public int numBehaviors(){return 0;}
	public Behavior fetchBehavior(int index){return null;}
	public Behavior fetchBehavior(String ID){return null;}
	public boolean isGeneric(){return false;}
}
