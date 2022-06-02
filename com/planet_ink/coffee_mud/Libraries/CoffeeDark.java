package com.planet_ink.coffee_mud.Libraries;
import com.planet_ink.coffee_mud.core.exceptions.BadEmailAddressException;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.CMProps.Str;
import com.planet_ink.coffee_mud.core.CMSecurity.DbgFlag;
import com.planet_ink.coffee_mud.core.MiniJSON.MJSONException;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.CharCreationLibrary.LoginSession;
import com.planet_ink.coffee_mud.Libraries.interfaces.PlayerLibrary.ThinPlayer;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.ShipDirComponent.ShipDir;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;
import com.planet_ink.coffee_mud.core.MiniJSON;

import java.util.*;
import java.util.Map.Entry;
import java.lang.ref.WeakReference;
import java.math.*;
/*
   Copyright 2013-2022 Bo Zimmerman

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
public class CoffeeDark extends StdLibrary implements GalacticMap
{
	@Override
	public String ID()
	{
		return "CoffeeDark";
	}
	protected static final double		ZERO_ALMOST				= 0.000001;
	protected static final BigDecimal	ZERO					= BigDecimal.valueOf(0.0);
	protected static final BigDecimal	ALMOST_ZERO				= BigDecimal.valueOf(ZERO_ALMOST);
	protected static final BigDecimal	ONE						= BigDecimal.valueOf(1L);
	protected static final BigDecimal	TWO						= BigDecimal.valueOf(2L);
	protected static final BigDecimal	TEN						= BigDecimal.valueOf(10L);
	protected static final BigDecimal	ONE_THOUSAND			= BigDecimal.valueOf(1000);
	protected static final double		PI_ALMOST				= Math.PI - ZERO_ALMOST;
	protected static final double		PI_TIMES_2_ALMOST		= Math.PI * 2.0 - ZERO_ALMOST;
	protected static final double		PI_TIMES_2				= Math.PI * 2.0;
	protected static final double		PI_BY_2					= Math.PI / 2.0;
	protected static final double		PI_TIMES_1ANDAHALF		= Math.PI * 1.5;
	protected final int					QUADRANT_WIDTH			= 10;

	protected RTree<SpaceObject>		space					= new RTree<SpaceObject>();

	private static Filterer<Area> planetsAreaFilter=new Filterer<Area>()
	{
		@Override
		public boolean passesFilter(final Area obj)
		{
			return (obj instanceof SpaceObject) && (!(obj instanceof SpaceShip));
		}
	};

	@Override
	public boolean isObjectInSpace(final SpaceObject O)
	{
		synchronized(space)
		{
			return space.contains(O);
		}
	}

	@Override
	public void delObjectInSpace(final SpaceObject O)
	{
		synchronized(space)
		{
			space.remove(O);
		}
	}

	@Override
	public void addObjectToSpace(final SpaceObject O, final long[] coords)
	{
		synchronized(space)
		{
			O.coordinates()[0]=coords[0];
			O.coordinates()[1]=coords[1];
			O.coordinates()[2]=coords[2];
			space.insert(O); // won't accept dups, so is ok
		}
	}

	@Override
	public void addObjectToSpace(final SpaceObject O)
	{
		synchronized(space)
		{
			space.insert(O); // won't accept dups, so is ok
		}
	}

	@Override
	public Enumeration<Area> spaceAreas()
	{
		return new FilteredEnumeration<Area>(CMLib.map().areas(),planetsAreaFilter);
	}

	@Override
	public long getDistanceFrom(final long[] coord1, final long[] coord2)
	{
		final BigInteger coord_0 = BigInteger.valueOf(coord1[0]).subtract(BigInteger.valueOf(coord2[0]));
		final BigInteger coord_0m = coord_0.multiply(coord_0);
		final BigInteger coord_1 = BigInteger.valueOf(coord1[1]).subtract(BigInteger.valueOf(coord2[1]));
		final BigInteger coord_1m = coord_1.multiply(coord_1);
		final BigInteger coord_2 = BigInteger.valueOf(coord1[2]).subtract(BigInteger.valueOf(coord2[2]));
		final BigInteger coord_2m = coord_2.multiply(coord_2);
		final BigInteger coords_all = coord_0m.add(coord_1m).add(coord_2m);
		return Math.round(Math.sqrt(coords_all.doubleValue()));
	}

	@Override
	public long getDistanceFrom(final SpaceObject O1, final SpaceObject O2)
	{
		return getDistanceFrom(O1.coordinates(),O2.coordinates());
	}

	protected BigDecimal getBigDistanceFrom(final long[] coord1, final long[] coord2)
	{
		final BigDecimal coord_0 = BigDecimal.valueOf(coord1[0]).subtract(BigDecimal.valueOf(coord2[0]));
		final BigDecimal coord_0m = coord_0.multiply(coord_0);
		final BigDecimal coord_1 = BigDecimal.valueOf(coord1[1]).subtract(BigDecimal.valueOf(coord2[1]));
		final BigDecimal coord_1m = coord_1.multiply(coord_1);
		final BigDecimal coord_2 = BigDecimal.valueOf(coord1[2]).subtract(BigDecimal.valueOf(coord2[2]));
		final BigDecimal coord_2m = coord_2.multiply(coord_2);
		final BigDecimal coords_all = coord_0m.add(coord_1m).add(coord_2m);
		final BigDecimal val = bigSqrt(coords_all);
		return val;
	}

	@Override
	public String getSectorName(final long[] coordinates)
	{
		final String[] xsecs=CMProps.getListFileStringList(CMProps.ListFile.TECH_SECTOR_X_NAMES);
		final String[] ysecs=CMProps.getListFileStringList(CMProps.ListFile.TECH_SECTOR_Y_NAMES);
		final String[] zsecs=CMProps.getListFileStringList(CMProps.ListFile.TECH_SECTOR_Z_NAMES);

		final long dmsPerXSector = SpaceObject.Distance.GalaxyRadius.dm / xsecs.length;
		final long dmsPerYSector = SpaceObject.Distance.GalaxyRadius.dm / ysecs.length;
		final long dmsPerZSector = SpaceObject.Distance.GalaxyRadius.dm / zsecs.length;

		final int secDeX = (int)((coordinates[0] % SpaceObject.Distance.GalaxyRadius.dm) / dmsPerXSector / 2);
		final int secDeY = (int)((coordinates[1] % SpaceObject.Distance.GalaxyRadius.dm) / dmsPerYSector / 2);
		final int secDeZ = (int)((coordinates[2] % SpaceObject.Distance.GalaxyRadius.dm) / dmsPerZSector / 2);

		final StringBuilder sectorName = new StringBuilder("");
		sectorName.append(xsecs[(secDeX < 0)?(xsecs.length+secDeX):secDeX]).append(" ");
		sectorName.append(ysecs[(secDeY < 0)?(ysecs.length+secDeY):secDeY]).append(" ");
		sectorName.append(zsecs[(secDeZ < 0)?(zsecs.length+secDeZ):secDeZ]);
		return sectorName.toString();
	}

	@Override
	public long[] getInSectorCoords(final long[] coordinates)
	{
		final String[] xsecs=CMProps.getListFileStringList(CMProps.ListFile.TECH_SECTOR_X_NAMES);
		final String[] ysecs=CMProps.getListFileStringList(CMProps.ListFile.TECH_SECTOR_Y_NAMES);
		final String[] zsecs=CMProps.getListFileStringList(CMProps.ListFile.TECH_SECTOR_Z_NAMES);

		final long dmsPerXSector = SpaceObject.Distance.GalaxyRadius.dm / xsecs.length;
		final long dmsPerYSector = SpaceObject.Distance.GalaxyRadius.dm / ysecs.length;
		final long dmsPerZSector = SpaceObject.Distance.GalaxyRadius.dm / zsecs.length;

		final int secDeX = (int)((coordinates[0] % SpaceObject.Distance.GalaxyRadius.dm) / dmsPerXSector / (2 * (coordinates[0]<0?-1:1)));
		final int secDeY = (int)((coordinates[1] % SpaceObject.Distance.GalaxyRadius.dm) / dmsPerYSector / (2 * (coordinates[0]<0?-1:1)));
		final int secDeZ = (int)((coordinates[2] % SpaceObject.Distance.GalaxyRadius.dm) / dmsPerZSector / (2 * (coordinates[0]<0?-1:1)));

		final long[] sectorCoords = Arrays.copyOf(coordinates, 3);
		for(int i=0;i<sectorCoords.length;i++)
		{
			if(sectorCoords[i]<0)
				sectorCoords[i]*=-1;
		}
		sectorCoords[0] -= (secDeX * dmsPerXSector) / 1000;
		sectorCoords[1] -= (secDeY * dmsPerYSector) / 1000;
		sectorCoords[2] -= (secDeZ * dmsPerZSector) / 1000;
		return sectorCoords;
	}

	@Override
	public void accelSpaceObject(final SpaceObject O, final double[] accelDirection, final double newAcceleration)
	{
		final double newSpeed = accelSpaceObject(O.direction(),O.speed(),accelDirection,newAcceleration);
		O.setSpeed(newSpeed);
	}

	@Override
	public double getAngleDelta(final double[] fromAngle, final double[] toAngle)
	{
		final double x1=Math.sin(fromAngle[1])*Math.cos(fromAngle[0]);
		final double y1=Math.sin(fromAngle[1])*Math.sin(fromAngle[0]);
		final double z1=Math.cos(fromAngle[1]);
		final double x2=Math.sin(toAngle[1])*Math.cos(toAngle[0]);
		final double y2=Math.sin(toAngle[1])*Math.sin(toAngle[0]);
		final double z2=Math.cos(toAngle[1]);
		double pitchDOTyaw=x1*x2+y1*y2+z1*z2;
		if(pitchDOTyaw>1)
			pitchDOTyaw=(2-pitchDOTyaw);
		if(pitchDOTyaw<-1)
			pitchDOTyaw=(-1*pitchDOTyaw)-2;
		final double finalDelta=Math.acos(pitchDOTyaw);
		if(Double.isNaN(finalDelta) || Double.isInfinite(finalDelta))
		{
			Log.errOut("finalDelta = "+ finalDelta+"= ("+fromAngle[0]+","+fromAngle[1]+") -> ("+toAngle[0]+","+toAngle[1]+")");
			Log.errOut("pitchDOTyaw = " + pitchDOTyaw+", x1 = "+ x1 + ", y1 = "+ y1 + ", z1 = "+ z1 + ", x2 = "+ x2 + ", y2 = "+ y2);
		}
		return finalDelta;
	}

	@Override
	public double[] getFacingAngleDiff(final double[] fromAngle, final double[] toAngle)
	{
		final double fromYaw = fromAngle[0];
		final double fromPitch = (fromAngle[1] > Math.PI) ? Math.abs(Math.PI-fromAngle[1]) : fromAngle[1];

		final double toYaw = toAngle[0];
		final double toPitch = (toAngle[1] > Math.PI) ? Math.abs(Math.PI-toAngle[1]) : toAngle[1];

		final double[] delta = new double[2];
		if(toYaw != fromYaw)
		{
			if(toYaw > fromYaw)
			{
				delta[0]=(toYaw-fromYaw);
				if(delta[0] > Math.PI)
					delta[0] = -((PI_TIMES_2-toYaw)+fromYaw);
			}
			else
			{
				delta[0]=(toYaw-fromYaw);
				if(delta[0] < -Math.PI)
					delta[0] = -((PI_TIMES_2-fromYaw)+toYaw);
			}
		}
		delta[1]=(toPitch-fromPitch);
		return delta;
	}

	@Override
	public double accelSpaceObject(final double[] curDirection, final double curSpeed, final double[] accelDirection, final double newAcceleration)
	{
		if(newAcceleration <= 0.0)
			return curSpeed;

		final double curDirectionYaw = curDirection[0];
		final double curDirectionPitch = (curDirection[1] > Math.PI) ? Math.abs(Math.PI-curDirection[1]) : curDirection[1];

		final double accelDirectionYaw = accelDirection[0];
		final double accelDirectionPitch = (accelDirection[1] > Math.PI) ? Math.abs(Math.PI-accelDirection[1]) : accelDirection[1];

		final double currentSpeed = curSpeed;
		final double acceleration = newAcceleration;

		double yawDelta = (curDirectionYaw >  accelDirectionYaw) ? (curDirectionYaw - accelDirectionYaw) : (accelDirectionYaw - curDirectionYaw);
		// 350 and 10, diff = 340 + -360 = 20
		if(yawDelta > Math.PI)
			yawDelta=PI_TIMES_2-yawDelta;

		double pitchDelta = (curDirectionPitch >  accelDirectionPitch) ? (curDirectionPitch - accelDirectionPitch) : (accelDirectionPitch - curDirectionPitch);
		// 170 and 10 = 160, which is correct!
		if(pitchDelta > Math.PI)
			pitchDelta=Math.PI-pitchDelta;
		if(Math.abs(pitchDelta-Math.PI)<ZERO_ALMOST)
			yawDelta=0.0;

		final double anglesDelta =  getAngleDelta(curDirection, accelDirection);
		final double accelerationMultiplier = acceleration / currentSpeed;
		double newDirectionYaw;
		if(yawDelta < 0.1)
			newDirectionYaw = accelDirectionYaw;
		else
		{
			newDirectionYaw = curDirectionYaw + ((curDirectionYaw > accelDirectionYaw) ? -(accelerationMultiplier * Math.sin(yawDelta)) : (accelerationMultiplier * Math.sin(yawDelta)));
			if((newDirectionYaw > 0.0) && ((PI_TIMES_2 - newDirectionYaw) < ZERO_ALMOST))
				newDirectionYaw=0.0;
		}
		if (newDirectionYaw < 0.0)
			newDirectionYaw = PI_TIMES_2 + newDirectionYaw;
		double newDirectionPitch;
		if(pitchDelta < 0.1)
			newDirectionPitch = accelDirectionPitch;
		else
			newDirectionPitch = curDirectionPitch + ((curDirectionPitch > accelDirectionPitch) ? -(accelerationMultiplier * Math.sin(pitchDelta)) : (accelerationMultiplier * Math.sin(pitchDelta)));
		if (newDirectionPitch < 0.0)
			newDirectionPitch = PI_TIMES_2 + newDirectionPitch;

		double newSpeed = currentSpeed + (acceleration * Math.cos(anglesDelta));
		if(newSpeed < 0)
		{
			newSpeed = -newSpeed;
			newDirectionYaw = accelDirectionYaw;
			newDirectionPitch = accelDirectionPitch;
		}
		curDirection[0]=newDirectionYaw;
		curDirection[1]=newDirectionPitch;
		if(Double.isInfinite(newSpeed) || Double.isNaN(newSpeed))
		{
			Log.errOut("Invalid new speed: "+newSpeed + "("+currentSpeed+"+"+"("+acceleration+"*Math.cos("+anglesDelta+"))");
			return curSpeed;
		}
		return newSpeed;
	}

	@Override
	public double[] getOppositeDir(final double[] dir)
	{
		if((dir[1]<ZERO_ALMOST)||(dir[1]>PI_ALMOST))
			return new double[]{dir[0], Math.PI-dir[1]};
		final double[] newDir = new double[]{Math.PI+dir[0],Math.PI-dir[1]};
		if(newDir[0] >= PI_TIMES_2)
			newDir[0] -= PI_TIMES_2;
		return newDir;
	}

	@Override
	public ShipDirComponent.ShipDir getDirectionFromDir(final double[] facing, final double roll, final double[] direction)
	{
		//Log.debugOut("facing="+(Math.toDegrees(facing[0]) % 360.0)+","+(Math.toDegrees(facing[1]) % 180.0));
		//Log.debugOut("direction="+(Math.toDegrees(direction[0]) % 360.0)+","+(Math.toDegrees(direction[1]) % 180.0));
		double yD = ((Math.toDegrees(facing[0]) % 360.0) - (Math.toDegrees(direction[0]) % 360.0)) % 360.0;
		if(yD < 0)
			yD = 360.0 + yD;
		final double pD = Math.abs(((Math.toDegrees(facing[1]) % 180.0) - (Math.toDegrees(direction[1]) % 180.0)) % 180.0);
		//Log.debugOut("yD,pD="+yD+","+pD);
		double rD = (yD + (Math.toDegrees(roll) % 360.0)) % 360.0;
		if(rD < 0)
			rD = 360.0 + rD;
		//Log.debugOut("rD="+rD);
		if(pD<45 || pD > 135)
		{
			if(yD < 45.0 || yD > 315.0)
				return ShipDir.FORWARD;
			if(yD> 135.0 && yD < 225.0)
				return ShipDir.AFT;
		}
		if(rD >= 315.0 || rD<45.0)
			return ShipDir.DORSEL;
		if(rD >= 45.0 && rD <135.0)
			return ShipDir.PORT;
		if(rD >= 135.0 && rD <225.0)
			return ShipDir.VENTRAL;
		if(rD >= 225.0 && rD <315.0)
			return ShipDir.STARBOARD;
		return ShipDir.AFT;
	}

	@Override
	public double[] getDirection(final SpaceObject fromObj, final SpaceObject toObj)
	{
		return getDirection(fromObj.coordinates(),toObj.coordinates());
	}

	protected void moveSpaceObject(final SpaceObject O, final long x, final long y, final long z)
	{
		synchronized(space)
		{
			final boolean reAdd=space.contains(O);
			if(reAdd)
				space.remove(O);
			O.coordinates()[0]=x;
			O.coordinates()[1]=y;
			O.coordinates()[2]=z;
			if(reAdd)
				space.insert(O);
		}
	}

	@Override
	public void moveSpaceObject(final SpaceObject O, final long[] coords)
	{
		moveSpaceObject(O, coords[0], coords[1], coords[2]);
	}

	@Override
	public void moveSpaceObject(final SpaceObject O)
	{
		if(O.speed()>0)
		{
			final double x1=Math.cos(O.direction()[0])*Math.sin(O.direction()[1]);
			final double y1=Math.sin(O.direction()[0])*Math.sin(O.direction()[1]);
			final double z1=Math.cos(O.direction()[1]);
			moveSpaceObject(O,O.coordinates()[0]+Math.round(CMath.mul(O.speed(),x1)),
							O.coordinates()[1]+Math.round(CMath.mul(O.speed(),y1)),
							O.coordinates()[2]+Math.round(CMath.mul(O.speed(),z1)));
		}
	}

	@Override
	public double[] getDirection(final long[] fromCoords, final long[] toCoords)
	{
		final double[] dir=new double[2];
		final double x=toCoords[0]-fromCoords[0];
		final double y=toCoords[1]-fromCoords[1];
		final double z=toCoords[2]-fromCoords[2];
		if((x!=0)||(y!=0))
		{
			if(x<0)
				dir[0]=Math.PI-Math.asin(y/Math.sqrt((x*x)+(y*y)));
			else
				dir[0]=Math.asin(y/Math.sqrt((x*x)+(y*y)));
			if(dir[0] > 2*Math.PI)
				dir[0] = Math.abs(2*Math.PI-dir[0]);
		}
		if((x!=0)||(y!=0)||(z!=0))
			dir[1]=Math.acos(z/Math.sqrt((z*z)+(y*y)+(x*x)));
		if(dir[1] > Math.PI)
			dir[1] = Math.abs(Math.PI-dir[1]);
		return dir;
	}

	@Override
	public long[] moveSpaceObject(final long[] coordinates, final double[] direction, final long speed)
	{
		if(speed>0)
		{
			final double x1=Math.cos(direction[0])*Math.sin(direction[1]);
			final double y1=Math.sin(direction[0])*Math.sin(direction[1]);
			final double z1=Math.cos(direction[1]);
			return new long[]{coordinates[0]+Math.round(CMath.mul(speed,x1)),
							coordinates[1]+Math.round(CMath.mul(speed,y1)),
							coordinates[2]+Math.round(CMath.mul(speed,z1))};
		}
		return coordinates;
	}

	@Override
	public long[] getLocation(final long[] oldLocation, final double[] direction, final long distance)
	{
		final double x1=Math.cos(direction[0])*Math.sin(direction[1]);
		final double y1=Math.sin(direction[0])*Math.sin(direction[1]);
		final double z1=Math.cos(direction[1]);
		final long[] location=new long[3];
		location[0]=oldLocation[0]+Math.round(CMath.mul(distance,x1));
		location[1]=oldLocation[1]+Math.round(CMath.mul(distance,y1));
		location[2]=oldLocation[2]+Math.round(CMath.mul(distance,z1));
		return location;
	}

	@Override
	public long getRelativeSpeed(final SpaceObject O1, final SpaceObject O2)
	{
		return Math.round(Math.sqrt(( CMath.bigMultiply(O1.speed(),O1.coordinates()[0])
										.subtract(CMath.bigMultiply(O2.speed(),O2.coordinates()[0]).multiply(CMath.bigMultiply(O1.speed(),O1.coordinates()[0])))
										.subtract(CMath.bigMultiply(O2.speed(),O2.coordinates()[0])))
									.add(CMath.bigMultiply(O1.speed(),O1.coordinates()[1])
										.subtract(CMath.bigMultiply(O2.speed(),O2.coordinates()[1]).multiply(CMath.bigMultiply(O1.speed(),O1.coordinates()[1])))
										.subtract(CMath.bigMultiply(O2.speed(),O2.coordinates()[1])))
									.add(CMath.bigMultiply(O1.speed(),O1.coordinates()[2])
										.subtract(CMath.bigMultiply(O2.speed(),O2.coordinates()[2]).multiply(CMath.bigMultiply(O1.speed(),O1.coordinates()[2])))
										.subtract(CMath.bigMultiply(O2.speed(),O2.coordinates()[2]))).doubleValue()));
	}

	@Override
	public SpaceObject findSpaceObject(final String s, final boolean exactOnly)
	{
		final Iterable<SpaceObject> i=new Iterable<SpaceObject>()
		{
			@Override
			public Iterator<SpaceObject> iterator()
			{
				return new EnumerationIterator<SpaceObject>(space.objects());
			}

		};
		return (SpaceObject)CMLib.english().fetchEnvironmental(i, s, exactOnly);
	}

	@Override
	public SpaceObject getSpaceObject(final CMObject o, final boolean ignoreMobs)
	{
		if(o instanceof SpaceObject)
		{
			if(o instanceof Boardable)
			{
				final Item I=((Boardable)o).getBoardableItem();
				if(I instanceof SpaceObject)
					return (SpaceObject)I;
			}
			return (SpaceObject)o;
		}
		if(o instanceof Item)
		{
			if(((Item)o).container()!=null)
				return getSpaceObject(((Item)o).container(),ignoreMobs);
			else
				return getSpaceObject(((Item)o).owner(),ignoreMobs);
		}
		if(o instanceof MOB)
			return ignoreMobs?null:getSpaceObject(((MOB)o).location(),false);
		if(o instanceof Room)
			return getSpaceObject(((Room)o).getArea(),ignoreMobs);
		if(o instanceof Area)
		{
			for(final Enumeration<Area> a=((Area)o).getParents();a.hasMoreElements();)
			{
				final SpaceObject obj=getSpaceObject(a.nextElement(),ignoreMobs);
				if(obj != null)
					return obj;
			}
		}
		return null;
	}

	@Override
	public Enumeration<SpaceObject> getSpaceObjects()
	{
		return this.space.objects();
	}

	@Override
	public Enumeration<Entry<SpaceObject, List<WeakReference<TrackingVector<SpaceObject>>>>>  getSpaceObjectEntries()
	{
		return this.space.objectEntries();
	}

	@Override
	public List<SpaceObject> getSpaceObjectsByCenterpointWithin(final long[] centerCoordinates, final long minDistance, final long maxDistance)
	{
		final List<SpaceObject> within=new ArrayList<SpaceObject>(1);
		if((centerCoordinates==null)||(centerCoordinates.length!=3))
			return within;
		synchronized(space)
		{
			space.query(within, new BoundedObject.BoundedCube(centerCoordinates, maxDistance));
		}
		if(within.size()<1)
			return within;
		for (final Iterator<SpaceObject> o=within.iterator();o.hasNext();)
		{
			final SpaceObject O=o.next();
			final long dist=getDistanceFrom(O.coordinates(),centerCoordinates);
			if((dist<minDistance)||(dist>maxDistance))
				o.remove();
		}
		return within;
	}

	@Override
	public List<SpaceObject> getSpaceObjectsWithin(final SpaceObject ofObj, final long minDistance, final long maxDistance)
	{
		final List<SpaceObject> within=new ArrayList<SpaceObject>(1);
		if(ofObj==null)
			return within;
		synchronized(space)
		{
			space.query(within, new BoundedObject.BoundedCube(ofObj.coordinates(), maxDistance));
		}
		for (final Iterator<SpaceObject> o=within.iterator();o.hasNext();)
		{
			final SpaceObject O=o.next();
			if(O!=ofObj)
			{
				final long dist=Math.round(Math.abs(getDistanceFrom(O,ofObj) - O.radius() - ofObj.radius()));
				if((dist<minDistance)||(dist>maxDistance))
					o.remove();
			}
		}
		if(within.size()<=1)
			return within;
		Collections.sort(within, new Comparator<SpaceObject>()
		{
			@Override
			public int compare(final SpaceObject o1, final SpaceObject o2)
			{
				final long distTo1=getDistanceFrom(o1,ofObj);
				final long distTo2=getDistanceFrom(o2,ofObj);
				if(distTo1==distTo2)
					return 0;
				return distTo1>distTo2?1:-1;
			}
		});
		return within;
	}

	@Override
	public List<LocationRoom> getLandingPoints(final SpaceObject ship, final Environmental O)
	{
		final List<LocationRoom> rooms=new LinkedList<LocationRoom>();
		final Area A;
		if(O instanceof Area)
			A=(Area)O;
		else
		if(O instanceof Boardable)
			A=((Boardable)O).getArea();
		else
		if(O instanceof Room)
			A=((Room)O).getArea();
		else
			return rooms;
		for(final Enumeration<Room> r=A.getMetroMap();r.hasMoreElements();)
		{
			final Room R2=r.nextElement();
			if(R2 instanceof LocationRoom)
			{
				rooms.add((LocationRoom)R2);
			}
		}
		Collections.sort(rooms,new Comparator<LocationRoom>()
		{
			final SpaceObject sship = ship;

			@Override
			public int compare(final LocationRoom o1, final LocationRoom o2)
			{
				if(o1.domainType()==Room.DOMAIN_OUTDOORS_SPACEPORT)
				{
					if(o2.domainType()!=Room.DOMAIN_OUTDOORS_SPACEPORT)
						return -1;
				}
				else
				if(o2.domainType()==Room.DOMAIN_OUTDOORS_SPACEPORT)
					return 1;
				if(ship != null)
				{
					final long distanceFrom1=getDistanceFrom(sship.coordinates(), o1.coordinates());
					final long distanceFrom2=getDistanceFrom(sship.coordinates(), o2.coordinates());
					if(distanceFrom1 > distanceFrom2)
						return 1;
					if(distanceFrom1 < distanceFrom2)
						return -1;
					return 0;
				}
				else
					return 0;
			}
		});
		return rooms;
	}

	@Override
	public int numSpaceObjects()
	{
		return space.count();
	}

	public static BigDecimal bigSqrt(final BigDecimal A)
	{
		if(A.doubleValue()<0)
			return ZERO;
		final int SCALE=0;
		BigDecimal x0 = new BigDecimal("0");
		BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
		int times=0;
		while ((!x0.equals(x1))&&(++times<20))
		{
			x0 = x1;
			x1 = A.divide(x0, SCALE, RoundingMode.UP);
			x1 = x1.add(x0);
			x1 = x1.divide(TWO, SCALE, RoundingMode.UP);
		}
		return x1;
	}

	protected final double getDirDiffSum(final double[] d1, final double d2[])
	{
		final double sum1=d1[0]>d2[0]?d1[0]-d2[0]:d2[0]-d1[0];
		final double sum2=d1[0]>d2[0]?(PI_TIMES_2-d1[0]+d2[0]):(PI_TIMES_2-d2[0]+d1[0]);
		final double sum3=d1[1]>d2[1]?d1[1]-d2[1]:d2[1]-d1[1];
		return sum1>sum2?(sum2+sum3):(sum1+sum3);
	}

	@Override
	public double getMinDistanceFrom(final long[] prevPos, final long[] curPos, final long[] objPos)
	{
		if(Arrays.equals(prevPos, curPos))
			return this.getDistanceFrom(curPos, objPos);
		final BigVector bigPrevPos = new BigVector(prevPos);
		final BigVector bigCurPos = new BigVector(curPos);
		final BigVector bigObjPos = new BigVector(objPos);

		final BigVector AB = bigCurPos.subtract(bigPrevPos);
		final BigVector BE = bigObjPos.subtract(bigCurPos);
		final BigVector AE = bigObjPos.subtract(bigPrevPos);

		if(AB.dotProduct(BE).doubleValue() > 0)
			return BE.magnitude().doubleValue();
		else
		if(AB.dotProduct(AE).doubleValue() < 0)
			return AE.magnitude().doubleValue();
		else
		{
			final BigVector bigDistance = bigPrevPos.subtract(bigCurPos);
			bigDistance.unitVectorFrom(); // divides each point by the vectors magnitude
			final BigDecimal dp = BE.dotProduct(bigDistance);
			return bigCurPos.add(bigDistance.scalarProduct(dp)).subtract(bigObjPos).magnitude().doubleValue();
		}
	}

	@Override
	public boolean shutdown()
	{
		space.clear();
		return true;
	}

}
