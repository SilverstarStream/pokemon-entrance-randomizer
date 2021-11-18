package com.dabomstew.pkrandom.pokemon;

/*----------------------------------------------------------------------------*/
/*--  TrainerPokemon.java - represents a Pokemon owned by a trainer.        --*/
/*--                                                                        --*/
/*--  Part of "Pokemon Entrance Randomizer" by SilverstarStream             --*/
/*--  Modified from "Universal Pokemon Randomizer ZX" by the UPR-ZX team    --*/
/*--  Originally part of "Universal Pokemon Randomizer" by Dabomstew        --*/
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

import com.dabomstew.pkrandom.exceptions.*;
import com.fasterxml.jackson.annotation.*;
// Please see https://github.com/SilverstarStream/pokemon-entrance-randomizer#external-libraries for instructions on adding jackson

import java.util.*;

@JsonIgnoreProperties(value = {"pokemon", "move1", "move2", "move3", "move4",
        "heldItem", "hasMegaStone", "hasZCrystal", "absolutePokeNumber", "strength"})
public class TrainerPokemon {

    public Pokemon pokemon;
    public int level;

    public int move1;
    public int move2;
    public int move3;
    public int move4;

    public int heldItem = 0;
    public boolean hasMegaStone;
    public boolean hasZCrystal;
    public int abilitySlot = 0;
    public int forme = 0;
    public String formeSuffix = "";
    public int absolutePokeNumber = 0;

    public int forcedGenderFlag = 0;
    public byte nature = 0;
    public byte hpEVs = 0;
    public byte atkEVs = 0;
    public byte defEVs = 0;
    public byte spatkEVs = 0;
    public byte spdefEVs = 0;
    public byte speedEVs = 0;
    public int IVs = 0;
    // In gens 3-5, there is a byte or word that corresponds
    // to the IVs a trainer's pokemon has. In X/Y, this byte
    // also encodes some other information, possibly related
    // to EV spread. Because of the unknown part in X/Y,
    // we store the whole "strength byte" so we can
    // write it unchanged when randomizing trainer pokemon.
    public int strength = 0;

    public boolean resetMoves = false;

    @JsonAlias("pokemon")
    public String jsonPokemon = "";
    @JsonAlias("move1")
    public String jsonMove1 = "";
    @JsonAlias("move2")
    public String jsonMove2 = "";
    @JsonAlias("move3")
    public String jsonMove3 = "";
    @JsonAlias("move4")
    public String jsonMove4 = "";
    @JsonAlias("heldItem")
    public String jsonHeldItem = "";

    public void convertJsonValues(Map<String, Pokemon> pokeMap, Map<String, Integer> moveMap, Map<String, Integer> itemMap) throws LeaderTeamsException {
        // Validate that pokemon, level, and at least one move was supplied
        if (this.jsonPokemon == null || this.jsonPokemon.isEmpty()) {
            throw new LeaderTeamsException("\"pokemon\" must be supplied.");
        }
        if (this.level < 1 || this.level > 100) {
            throw new LeaderTeamsException("\"level\" was either not supplied or is not in the range 1-100.");
        }
        if ((this.jsonMove1 == null || this.jsonMove1.isEmpty()) && !this.resetMoves) {
            throw new LeaderTeamsException("Either \"move1\" must be supplied or \"resetMoves\" set to true.");
        }

        this.pokemon = pokeMap.get(this.jsonPokemon.toUpperCase());
        if (this.pokemon == null) {
            throw new LeaderTeamsException("pokemon: \"" + this.jsonPokemon + "\" isn't recognized as a Pokemon available this generation. Is it misspelled?");
        }

        try {
            this.move1 = moveMap.get(this.jsonMove1.toUpperCase());
        } catch (NullPointerException e) {
            throw new LeaderTeamsException("move1: \"" + this.jsonMove1 + "\" isn't recognized as a move available this generation. Is it misspelled?");
        }
        try {
            this.move2 = moveMap.get(this.jsonMove2.toUpperCase());
        } catch (NullPointerException e) {
            throw new LeaderTeamsException("move2: \"" + this.jsonMove2 + "\" isn't recognized as a move available this generation. Is it misspelled?");
        }
        try {
            this.move3 = moveMap.get(this.jsonMove3.toUpperCase());
        } catch (NullPointerException e) {
            throw new LeaderTeamsException("move3: \"" + this.jsonMove3 + "\" isn't recognized as a move available this generation. Is it misspelled?");
        }
        try {
            this.move4 = moveMap.get(this.jsonMove4.toUpperCase());
        } catch (NullPointerException e) {
            throw new LeaderTeamsException("move4: \"" + this.jsonMove4 + "\" isn't recognized as a move available this generation. Is it misspelled?");
        }

        try {
            this.heldItem = itemMap.get(this.jsonHeldItem.toUpperCase());
        } catch (NullPointerException e) {
            throw new LeaderTeamsException("heldItem: \"" + this.jsonHeldItem + "\" isn't recognized as an item available this generation. Is it misspelled?");
        }
    }

    public String toString() {
        String s = pokemon.name + formeSuffix;
        if (heldItem != 0) {
            // This can be filled in with the actual name when written to the log.
            s += "@%s";
        }
        s+= " Lv" + level;
        return s;
    }

    public boolean canMegaEvolve() {
        if (heldItem != 0) {
            for (MegaEvolution mega: pokemon.megaEvolutionsFrom) {
                if (mega.argument == heldItem) {
                    return true;
                }
            }
        }
        return false;
    }

    public TrainerPokemon copy() {
        TrainerPokemon tpk = new TrainerPokemon();
        tpk.pokemon = pokemon;
        tpk.level = level;

        tpk.move1 = move1;
        tpk.move2 = move2;
        tpk.move3 = move3;
        tpk.move4 = move4;

        tpk.strength = strength;
        tpk.heldItem = heldItem;
        tpk.abilitySlot = abilitySlot;
        tpk.forme = forme;
        tpk.formeSuffix = formeSuffix;
        tpk.absolutePokeNumber = absolutePokeNumber;

        tpk.resetMoves = resetMoves;

        return tpk;
    }
}
