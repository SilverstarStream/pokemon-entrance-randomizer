package com.dabomstew.pkrandom.pokemon;

/*----------------------------------------------------------------------------*/
/*--  Location.java - Represents randomizable Locations within the game.    --*/
/*--                                                                        --*/
/*--  Part of "Pokemon Entrance Randomizer" by SilverstarStream             --*/
/*--  Pokemon and any associated names and the like are                     --*/
/*--  trademark and (C) Nintendo 1996-2020.                                 --*/
/*--                                                                        --*/
/*--  The custom code written here is licensed under the terms of the GPL:  --*/
/*--                                                                        --*/
/*--  This program is free software: you can redistribute it and/or modify  --*/
/*--  it under the terms of the GNU General Public License as published by  --*/
/*--  the Free Software Foundation, either version 3 of the License, or     --*/
/*--  (at your option) any later version.                                   --*/
/*--                                                                        --*/
/*--  This program is distributed in the hope that it will be useful,       --*/
/*--  but WITHOUT ANY WARRANTY; without even the implied warranty of        --*/
/*--  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the          --*/
/*--  GNU General Public License for more details.                          --*/
/*--                                                                        --*/
/*--  You should have received a copy of the GNU General Public License     --*/
/*--  along with this program. If not, see <http://www.gnu.org/licenses/>.  --*/
/*----------------------------------------------------------------------------*/

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

// Locations describe some set of maps with distinct loading zones between other Locations
public class Location {
    public List<Exit> exits = new ArrayList<>(); // all the Exits for this Location
    public int maxWeight; // the highest badge the player can hold and still have this Location placed on the map.
    public boolean isGymCity; // for map randomization
    public String name;
    public List<Location> prereqs = new ArrayList<>(); // the list of Locations that must be placed before this Location gets placed
    public List<PokemonGroup> encGroups = new ArrayList<>(); // the groups of wild Pokemon that will have their levels and evos randomized
    public List<PokemonGroup> trGroups = new ArrayList<>(); // the groups of trainers ^
    public int badgePlacement; // for map randomization; becomes set to the current badge when this Location is placed on the map.

    public Location(int maxWeight, String name, boolean isGymCity) {
        this.maxWeight = maxWeight;
        this.name = name;
        this.isGymCity = isGymCity;
    }

    public void addExit(int minWeight, String destName, int event,
                        int[] warpIDs, Map<Integer, Integer> em) {
        Exit newSet = new Exit(minWeight, destName, event, warpIDs, em, this);
        this.exits.add(newSet);
    }

    // This method is used to add which map in the matrix the Location belongs to (I don't remember the specific names or details)
    public void addExit(int minWeight, String destName, int event,
                        int[] warpIDs, Map<Integer, Integer> em, int matrixMap) {
        Exit newSet = new Exit(minWeight, destName, event, warpIDs, em, this, matrixMap);
        this.exits.add(newSet);
    }

    public void addWildGroup(int badgeRegion, int[] wildIndices) {
        PokemonGroup newGroup = new PokemonGroup(badgeRegion, wildIndices);
        this.encGroups.add(newGroup);
    }

    public void addWildFiller(int badgeRegion, int level) {
        PokemonGroup newGroup = new PokemonGroup(badgeRegion, new int[] {});
        newGroup.groupAvgLevel = level;
        this.encGroups.add(newGroup);
    }

    public void addTrainerGroup(int badgeRegion, int[] trIndices) {
        PokemonGroup newGroup = new PokemonGroup(badgeRegion, trIndices);
        this.trGroups.add(newGroup);
    }

    public void addTrainerFiller(int badgeRegion, int level) {
        PokemonGroup newGroup = new PokemonGroup(badgeRegion, new int[] {});
        newGroup.groupAvgLevel = level;
        this.trGroups.add(newGroup);
    }
}
