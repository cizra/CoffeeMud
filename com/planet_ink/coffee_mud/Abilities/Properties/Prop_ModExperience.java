package com.planet_ink.coffee_mud.Abilities.Properties;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.CMath.CompiledFormula;
import com.planet_ink.coffee_mud.core.CMath.CompiledOperation;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.MaskingLibrary;
import com.planet_ink.coffee_mud.Libraries.interfaces.MaskingLibrary.CompiledZMask;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/*
   Copyright 2004-2022 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public class Prop_ModExperience extends Property
{
	@Override
	public String ID()
	{
		return "Prop_ModExperience";
	}

	@Override
	public String name()
	{
		return "Modifying Experience Gained";
	}

	@Override
	protected int canAffectCode()
	{
		return Ability.CAN_MOBS | Ability.CAN_ITEMS | Ability.CAN_AREAS | Ability.CAN_ROOMS;
	}

	protected enum DirectionCheck
	{
		POSITIVE, NEGATIVE, POSINEGA
	}

	protected String			operationFormula	= "";
	protected boolean			selfXP				= false;
	protected boolean			rideOK				= false;
	protected boolean			targetOnly			= false;
	protected DirectionCheck	dir					= DirectionCheck.POSITIVE;
	protected CompiledFormula	operation			= null;
	protected CompiledZMask		mask				= null;

	@Override
	public String accountForYourself()
	{
		return "Modifies experience gained: " + operationFormula;
	}

	public int translateAmount(int amount, final String val)
	{
		if(amount<0)
			amount=-amount;
		if(val.endsWith("%"))
			return (int)Math.round(CMath.mul(amount,CMath.div(CMath.s_int(val.substring(0,val.length()-1)),100)));
		return CMath.s_int(val);
	}

	public String translateNumber(final String val)
	{
		if(val.endsWith("%"))
			return "( @x1 * (" + val.substring(0,val.length()-1) + " / 100) )";
		return Integer.toString(CMath.s_int(val));
	}

	@Override
	public void setMiscText(final String newText)
	{
		super.setMiscText(newText);
		operation = null;
		mask=null;
		selfXP=false;
		targetOnly=false;
		String s=newText.trim();
		int x=s.indexOf(';');
		if(x>=0)
		{
			mask=CMLib.masking().getPreCompiledMask(s.substring(x+1).trim());
			s=s.substring(0,x).trim();
		}
		String us=s.toUpperCase();
		x=us.indexOf("SELF");
		if(x>=0)
		{
			selfXP=true;
			s=s.substring(0,x)+s.substring(x+4);
			us=s.toUpperCase();
		}
		x=us.indexOf("TARGET");
		if(x>=0)
		{
			targetOnly=true;
			s=s.substring(0,x)+s.substring(x+6);
			us=s.toUpperCase();
		}
		x=us.indexOf("RIDEOK");
		if(x>=0)
		{
			rideOK=true;
			s=s.substring(0,x)+s.substring(x+6);
			us=s.toUpperCase();
		}
		dir = DirectionCheck.POSITIVE;
		for(final DirectionCheck d : DirectionCheck.values())
		{
			x=us.indexOf(d.name());
			if(x>=0)
			{
				dir = d;
				s=s.substring(0,x)+s.substring(x+d.name().length());
				us=s.toUpperCase();
			}
		}

		operationFormula="Amount "+s;
		final List<String> ops = new ArrayList<String>();
		int paren=0;
		final StringBuilder curr=new StringBuilder("");
		for(int i=0;i<s.length();i++)
		{
			if(paren > 0)
			{
				if(s.charAt('i')=='(')
				{
					if(paren == 0)
					{
						if(curr.length()>0)
							ops.add(curr.toString().trim());
						curr.setLength(0);
					}
					paren++;
				}
				else
				if(s.charAt('i')==')')
					paren--;
				curr.append(s.charAt(i));
			}
			else
			switch(s.charAt(i))
			{
			case '=':
			case '+':
			case '-':
			case '*':
			case '/':
				if(curr.length()>0)
					ops.add(curr.toString().trim());
				curr.setLength(0);
				curr.append(s.charAt(i));
				break;
			default:
				curr.append(s.charAt(i));
				break;
			}
		}
		if(curr.length()>0)
			ops.add(curr.toString().trim());
		StringBuilder finalOps = new StringBuilder("");
		for(final String op : ops)
		{
			if(op.startsWith("="))
				finalOps = new StringBuilder(translateNumber(op.substring(1)).trim());
			else
			if(op.startsWith("(")&&(op.endsWith(")")))
				finalOps = new StringBuilder(op);
			else
			if(op.startsWith("+")||op.startsWith("-")||op.startsWith("*")||op.startsWith("/"))
			{
				if(finalOps.length()==0)
					finalOps.append("@x1");
				finalOps.append(" ").append(op.charAt(0)).append(" ");
				finalOps.append(translateNumber(op.substring(1)).trim());
			}
			else
				finalOps=new StringBuilder(translateNumber(s.trim()));
		}
		if(finalOps.length()>0)
			operation = CMath.compileMathExpression(finalOps.toString());
		operationFormula=CMStrings.replaceAll(operationFormula, "@x1", "Amount");
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(operation == null)
			setMiscText(text());
		if(((msg.sourceMinor()==CMMsg.TYP_EXPCHANGE)
			||(msg.sourceMinor()==CMMsg.TYP_RPXPCHANGE))
		&&(operation != null)
		&&((((msg.target()==affected)||(selfXP && (msg.source()==affected)))&&(affected instanceof MOB))
		   ||((affected instanceof Rideable)
				&&(!rideOK)
				&&(msg.target()!=null)
				&&((msg.source().riding()==affected)
					|| ((affected instanceof Item)&&(msg.target().Name().equals(affected.Name()))) // what the actual f?
					||((msg.target() instanceof Rider)&&(((Rider)msg.target()).riding()==affected))))
		   ||((affected instanceof Item)
			   &&(msg.source()==((Item)affected).owner())
			   &&(((Item)affected).amBeingWornProperly()))
		   ||(affected instanceof Room)
		   ||(affected instanceof Area)))
		{
			
			if((targetOnly)
			&&((msg.target()==null)
				||(msg.target()==msg.source())))
				return super.okMessage(myHost,msg);
				
			switch(dir)
			{
			case POSITIVE:
				if(msg.value()<0)
					return super.okMessage(myHost,msg);
				break;
			case NEGATIVE:
				if(msg.value()>0)
					return super.okMessage(myHost,msg);
				break;
			case POSINEGA:
				break;
			}
			if(mask!=null)
			{
				if(affected instanceof Item)
				{
					if((msg.target()==null)||(!(msg.target() instanceof MOB))||(!CMLib.masking().maskCheck(mask,msg.target(),true)))
						return super.okMessage(myHost,msg);
				}
				else
				if(!CMLib.masking().maskCheck(mask,msg.source(),true))
					return super.okMessage(myHost,msg);
			}
			msg.setValue((int)Math.round(CMath.parseMathExpression(operation, new double[]{msg.value()}, 0.0)));
		}
		return super.okMessage(myHost,msg);
	}
}
