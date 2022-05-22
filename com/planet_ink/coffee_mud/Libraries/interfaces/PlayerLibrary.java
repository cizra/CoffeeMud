package com.planet_ink.coffee_mud.Libraries.interfaces;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.GenericBuilder.GenItemCode;
import com.planet_ink.coffee_mud.Libraries.interfaces.GenericBuilder.GenMOBCode;
import com.planet_ink.coffee_mud.Libraries.interfaces.PlayerLibrary.ThinPlayer;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;
/*
   Copyright 2008-2022 Bo Zimmerman

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
public interface PlayerLibrary extends CMLibrary
{
	public PlayerAccount getLoadAccount(String calledThis);
	public PlayerAccount getLoadAccountByEmail(String email);

	public PlayerAccount getAccount(String calledThis);
	public PlayerAccount getAccountAllHosts(String calledThis);

	public void addAccount(PlayerAccount acct);

	public boolean accountExists(String name);
	public boolean accountExistsAllHosts(String name);

	public Enumeration<PlayerAccount> accounts();
	public Enumeration<PlayerAccount> accounts(String sort, Map<String, Object> cache);

	public boolean isSameAccount(final MOB player1, final MOB player2);
	public boolean isSameAccountIP(final MOB player1, final MOB player2);

	public void obliterateAccountOnly(PlayerAccount deadAccount);

	public int numPlayers();
	public void addPlayer(MOB newOne);
	public void delPlayer(MOB oneToDel);
	public Enumeration<MOB> players();

	public MOB getPlayer(String calledThis);
	public MOB getPlayerAllHosts(String calledThis);

	public MOB getLoadPlayer(String last);
	public MOB getLoadPlayerByEmail(String email);

	public List<String> getPlayerLists();
	public List<String> getPlayerListsAllHosts();

	public boolean isLoadedPlayer(final MOB M);
	public boolean isLoadedPlayer(final String mobName);

	public boolean playerExists(String name);
	public boolean playerExistsAllHosts(String name);

	public String getLiegeOfUserAllHosts(final String userName);

	public MOB findPlayerOnline(final String srchStr, final boolean exactOnly);

	public void obliteratePlayer(MOB deadMOB, boolean deleteAssets, boolean quiet);

	public void renamePlayer(MOB mob, String oldName);

	public void unloadOfflinePlayer(final MOB mob);

	public void forceTick();
	public int savePlayers();

	public ThinPlayer getThinPlayer(final String mobName);
	public PlayerLibrary.ThinnerPlayer newThinnerPlayer();
	public Enumeration<ThinPlayer> thinPlayers(String sort, Map<String, Object> cache);

	public PlayerSortCode getCharThinSortCode(String codeName, boolean loose);
	public String getThinSortValue(ThinPlayer player, PlayerSortCode code);
	public String getSortValue(MOB player, PlayerSortCode code);

	public Object getPlayerValue(final String playerName, final PlayerCode code);
	public void setPlayerValue(final String playerName, final PlayerCode code, final Object value);

	public Set<MOB> getPlayersHere(Room room);
	public void changePlayersLocation(MOB mob, Room room);

	public void resetAllPrideStats();
	public Pair<Long,int[]>[] parsePrideStats(final String[] nextPeriods, final String[] prideStats);
	public int bumpPrideStat(final MOB mob, final AccountStats.PrideStat stat, final int amt);
	public List<Pair<String,Integer>> getTopPridePlayers(TimeClock.TimePeriod period, AccountStats.PrideStat stat);
	public List<Pair<String,Integer>> getTopPrideAccounts(TimeClock.TimePeriod period, AccountStats.PrideStat stat);

	public enum PlayerSortCode
	{
		NAME("CHARACTER"),
		CLASS("CHARCLASS"),
		RACE("RACE"),
		LEVEL("LVL"),
		AGE("HOURS"),
		LAST("DATE"),
		EMAIL("EMAILADDRESS"),
		IP("LASTIP")
		;
		public String altName;
		private PlayerSortCode(final String ln)
		{
			this.altName=ln;
		}
	}

	public enum PlayerCode
	{
		NAME,
		PASSWORD,
		CHARCLASS,
		RACE,
		HITPOINTS,
		LEVEL,
		MANA,
		MOVES,
		DESCRIPTION,
		ALIGNMENT,
		EXPERIENCE,
		DEITY,
		PRACTICES,
		TRAINS,
		AGE,
		MONEY,
		WIMP,
		QUESTPOINTS,
		LOCATION,
		STARTROOM,
		LASTDATE,
		CHANNELMASK,
		ATTACK,
		ARMOR,
		DAMAGE,
		MATTRIB,
		LEIGE,
		HEIGHT,
		WEIGHT,
		COLOR,
		LASTIP,
		EMAIL,
		TATTS,
		EXPERS,
		ACCOUNT,
		FACTIONS,
		INVENTORY,
		ABLES,
		AFFBEHAV,
		CLANS
	}

	public static interface ThinPlayer
	{
		public String name();
		public String charClass();
		public String race();
		public int level();
		public int age();
		public long last();
		public String email();
		public String ip();
		public int exp();
		public int expLvl();
		public String liege();
		public String worship();
	}

	public static interface ThinnerPlayer
	{
		public String name();
		ThinnerPlayer name(String name);
		public String password();
		ThinnerPlayer password(String password);
		public long expiration();
		ThinnerPlayer expiration(long expiration);
		public String accountName();
		ThinnerPlayer accountName(String accountName);
		public String email();
		ThinnerPlayer email(String email);
		public MOB loadedMOB();
		ThinnerPlayer loadedMOB(MOB mob);
	}
}
