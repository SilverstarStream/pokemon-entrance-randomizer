package com.dabomstew.pkrandom.pokemon;

/*----------------------------------------------------------------------------*/
/*--  PokemonGroup.java - Represents a group of Pokemon local to a          --*/
/*--                      Location, used to scale levels and evolutions.    --*/
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

// Group is an awful name for what this is, but I really can't come up with a much better one.
// These are used to represent groupings of TrainerPokemon or Encounter levels
// A PokemonGroup represents any number of sets of Pokemon that share the same badgeRegion and are reasonably grouped together.
// A Location could have multiple PokemonGroups, and those Groups might or might not share a badgeRegion.
// Having multiple PokemonGroups with the same badgeRegion allows for the averaging to better reflect differences in levels between those two areas.
// e.g. The averaging would skew high if a single EncounterSet with lower levels was in the same group as 8 Sets with large swings in levels
public class PokemonGroup {
    public int badgeRegion; // number of badges required to access this area in vanilla.
    public int[] indices; // the IDs of whatever this represents, contextualized by whatever uses this data, either for Trainers or Encounter
    public int groupAvgLevel; // the average level of all levels of whatever the indices point to.

    public PokemonGroup(int badgeRegion, int[] indices) {
        this.badgeRegion = badgeRegion;
        this.indices = indices;
    }
}
