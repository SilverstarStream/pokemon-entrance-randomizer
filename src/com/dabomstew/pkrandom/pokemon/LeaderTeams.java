package com.dabomstew.pkrandom.pokemon;

/*----------------------------------------------------------------------------*/
/*--  LeaderTeams.java - Used to read custom gym leader team files.         --*/
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

import com.dabomstew.pkrandom.exceptions.*;
import com.fasterxml.jackson.core.exc.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.*;
// Please see https://github.com/SilverstarStream/pokemon-entrance-randomizer#external-libraries for instructions on adding jackson

import java.io.*;
import java.util.*;

public class LeaderTeams {
    private List<String> leaderNames;
    private int gymCount;
    private File file;

    public LeaderTeams(List<String> leaderNames, int gymCount, File file) {
        this.leaderNames = leaderNames;
        this.gymCount = gymCount;
        this.file = file;
    }

    private void parsingError(String name, int teamNumber, String message) {
        throw new LeaderTeamsException("Problem with " + name + "'s team " + teamNumber + " in " + this.file.getName() + ":\n" + message);
    }

    public Map<String, List<TrainerPokemon>> getTeams(List<Integer> gymOrder, Map<String, Pokemon> pokeMap, Map<String, Integer> moveMap, Map<String, Integer> itemMap) {
        ObjectMapper mapper = new ObjectMapper();
        validateInputFile(mapper, file, pokeMap, moveMap, itemMap);

        String name = "";
        int newGym = 0;
        Map<String, List<TrainerPokemon>> leaderTeams = new HashMap<>();
        try {
            JsonNode jsonRoot = mapper.readTree(file);
            for (int i = 0; i < this.leaderNames.size(); i++) {
                name = leaderNames.get(i);
                newGym = gymOrder.indexOf(i) + 1;
                JsonNode team = jsonRoot.at("/" + name + "/" + newGym);
                if (team.isEmpty()) {
                    // vanilla team
                    leaderTeams.put(name, null);
                }
                else if (team.size() > 6) {
                    parsingError(name, newGym, "A team cannot have more than 6 Pokemon. Found " + team.size() + " Pokemon.");
                }
                else {
                    List<TrainerPokemon> tPokemon = new ArrayList<>();
                    for (JsonNode node : team) {
                        TrainerPokemon tp = mapper.treeToValue(node, TrainerPokemon.class);
                        tp.convertJsonValues(pokeMap, moveMap, itemMap);
                        tPokemon.add(tp);
                    }
                    leaderTeams.put(name, tPokemon);
                }
            }
        } catch (UnrecognizedPropertyException e) {
            // when a property is supplied that doesn't exist
            parsingError(name, newGym, "This team contained a property that could not be recognized.\n" + e.getMessage());
        } catch (InvalidFormatException e) {
            // deserialization error, e.g. a String is passed to an int field and can't be parsed as an int
            parsingError(name, newGym, "Type mismatch in one of the fields.\n" + e.getMessage());
        } catch (LeaderTeamsException | IOException e) {
            parsingError(name, newGym, e.getMessage());
        }
        return leaderTeams;
    }

    private void validateInputFile(ObjectMapper mapper, File file, Map<String, Pokemon> pokeMap, Map<String, Integer> moveMap, Map<String, Integer> itemMap) {
        String name = "";
        int gymNum = 0;
        try {
            JsonNode jsonRoot = mapper.readTree(file);
            for (int i = 0; i < this.leaderNames.size(); i++) {
                name = this.leaderNames.get(i);
                for (gymNum = 1; gymNum <= this.gymCount; gymNum++) {
                    JsonNode team = jsonRoot.at("/" + name + "/" + gymNum);
                    if (team.isMissingNode()) {
                        // The json does not have a reference to name/i
                        throw new LeaderTeamsException("There is no team defined for \"" + name + "\" for gym #" + i +
                                " in " + this.file.getName());
                    }
                    // Validate every field in every team member in every team
                    for (JsonNode node : team) {
                        TrainerPokemon tp = mapper.treeToValue(node, TrainerPokemon.class);
                        tp.convertJsonValues(pokeMap, moveMap, itemMap);
                    }
                }
            }
        } catch (StreamReadException e) {
            throw new LeaderTeamsException("json parsing error in " + this.file.getName() + "\n" + e.getMessage());
        } catch (InvalidFormatException e) {
            // Deserialization error. e.g. a String is passed to an int field and can't be parsed as an int
            parsingError(name, gymNum,"Type mismatch in one of the fields.\n" + e.getMessage());
        } catch (UnrecognizedPropertyException e) {
            // when a property is supplied that doesn't exist in TrainerPokemon
            String message = e.getMessage();
            int firstQuoteIndex = message.indexOf('"');
            String a = message.substring(firstQuoteIndex + 1);
            int secondQuoteIndex = a.indexOf('"');
            String lighterMessage = message.substring(0, firstQuoteIndex + secondQuoteIndex + 2);
            parsingError(name, gymNum, "This team contained a field that could not be recognized.\n" + lighterMessage);
        } catch (MismatchedInputException e) {
            throw new LeaderTeamsException(this.file.getName() + " is valid json, but does not match the expected input structure.\n" +
                    "Please reference the included Specification text file or a valid teams file for the proper json structure.");
        } catch (LeaderTeamsException e) {
            parsingError(name, gymNum, e.getMessage());
        } catch (IOException e) {
            String absPath = this.file.getAbsolutePath();
            String parentPath = absPath.substring(0, absPath.lastIndexOf(File.separatorChar));
            String randomizerPath = parentPath.substring(0, parentPath.lastIndexOf(File.separatorChar));
            LeaderTeamsException ex = new LeaderTeamsException("The randomizer could not find " + this.file.getName() + ".\n" +
                    "Please make sure that there is a `leader_teams` folder in " + randomizerPath + ",\n" +
                    "and that the `" + this.file.getName() + "` file is in that folder.");
            ex.fileNotFound = true;
            throw ex;
        }
    }
}
