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

import java.io.*;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class LeaderTeams {
    // maps a string like "Candice2" to a list of strings like "Snorunt 20".
    // Depends on getLeaderTeams to interpret those types of strings, but this guarantees that the strings will be in this format.
    private Map<String, List<String>> leaderTeams;
    private List<String> leaderNames;
    private int gymCount;
    private String filename;

    public LeaderTeams(List<String> leaderNames, int gymCount, File file) throws FileNotFoundException {
        this.leaderNames = leaderNames;
        this.gymCount = gymCount;
        this.filename = file.getName();
        Scanner sc = new Scanner(new FileInputStream(file), "UTF-8");
        this.leaderTeams = parseTeamsFile(sc);
        sc.close();
        /*} catch (FileNotFoundException e) {
            System.out.println("Error: Could not find " + this.filename + "\n" +
                    "Is there a leader_teams folder in the same folder as the randomizer .jar?" + "\n" +
                    "Is there a \"" + this.filename + "\" file in that folder?");
        }*/
    }

    private void error(int lineNumber, String message) {
        throw new LeaderTeamsException("Problem with line " + lineNumber + " in " + this.filename + ":\n" + message);
    }

    private Map<String, List<String>> parseTeamsFile(Scanner sc) {
        int lineCounter = 0;
        // Eat lines until "# Teams" is found
        while (sc.hasNextLine()) {
            lineCounter++;
            String line = sc.nextLine().trim();
            if (line.compareTo("# Teams") == 0 || line.compareTo("# teams") == 0) {
                break;
            }
        }
        if (!sc.hasNextLine()) {
            throw new LeaderTeamsException(this.filename + " must contain a \"# Teams\" line before the team lists.");
        }

        Map<String, List<String>> teamStrings = new HashMap<>();

        // Find the first Gym Leader name
        String eatenName = null;
        while (sc.hasNextLine()) {
            lineCounter++;
            String line = sc.nextLine().trim();
            if (line.length() != 0) {
                if (Character.isLetter(line.charAt(0))) {
                    if (this.leaderNames.contains(line)) {
                        eatenName = line;
                        break;
                    }
                    else {
                        throw new LeaderTeamsException(line + "\n" +
                                line + " is not recognized as a gym leader for this game. Is it misspelled?");
                    }
                }
                else {
                    error(lineCounter, line + "\n" +
                            "Expected a gym leader's name after # Teams.");
                }
            }
        }

        // Deal with the rest of the lines.
        int teamCounter = 0;
        while (sc.hasNextLine()) {
            lineCounter++;
            String line = sc.nextLine().trim();
            if (line.length() == 0) {
                continue;
            }
            if (Character.isLetter(line.charAt(0))) { // the line is a leader's name
                if (teamCounter != gymCount) {
                    throw new LeaderTeamsException("Problem with gym leader " + eatenName + " (line " + lineCounter + ") in " + this.filename + ":\n" +
                            eatenName + " has " + teamCounter + " teams listed. Every leader needs " + gymCount + " teams.");
                }
                else if (this.leaderNames.contains(line)) {
                    eatenName = line;
                    teamCounter = 0;
                }
                else {
                    error(lineCounter, line + "\n" +
                            "\"" + line + "\" is not recognized as a gym leader for this game. Is it misspelled?");
                }
            }
            else if (!Character.isDigit(line.charAt(0))) { // the line is not a leader's name or a team
                error(lineCounter, line + "\n" +
                        "Cannot parse \"" + line + "\" as a leader name or a team.");
            }
            else { // the line is a team
                if (!line.contains(":")) {
                    error(lineCounter, line + "\n" +
                            "This line should have a \":\" after the gym number.");
                }
                String indexStr = line.substring(0, line.indexOf(":")).trim();
                int index = 0;
                try {
                    index = Integer.parseInt(indexStr);
                }
                catch (NumberFormatException e) {
                    error(lineCounter, line + "\n" +
                            "Unable to parse \"" + indexStr + "\" as a number.");
                }
                if (index < 1 || index > this.gymCount) {
                    error(lineCounter, line + "\n" +
                            "Cannot handle gym number " + index + ". Must be in the range 1-" + gymCount);
                }
                List<String> team = new ArrayList<>();
                String teamStr = line.substring(line.indexOf(":") + 1).trim();
                if (teamStr.compareTo("vanilla") == 0 || teamStr.compareTo("Vanilla") == 0) {
                    team = null;
                }
                else {
                    String[] teamArr = teamStr.split("/");
                    for (String str : teamArr) {
                        String mon = str.trim();
                        if (mon.length() == 0) {
                            error(lineCounter, line + "\n" +
                                    "There's an extra / on this line.");
                        }
                        else if (Character.isLetter(mon.charAt(0)) && Character.isDigit(mon.charAt(mon.length() - 1))) {
                            for (int i = mon.lastIndexOf(" ") - 1; i > 0; i--) {
                                char current = mon.charAt(i);
                                if (current != ' ') {
                                    break;
                                }
                                mon = mon.substring(0, i) + mon.substring(i + 1);
                            }
                            // guarantees that there is exactly 1 space between the mon and level.
                            team.add(mon);
                        }
                        else {
                            error(lineCounter, line + "\n" +
                                    "Unable to parse \"" + mon + "\" as a Pokemon and a level. It should be like \"Furret 20\".");
                        }
                    }
                }
                if (team != null && (team.size() == 0 || team.size() > 6)) {
                    error(lineCounter, line + "\n" +
                            "A team must have 1-6 Pokemon. Found " + team.size() + " Pokemon.");
                }
                teamStrings.put(eatenName + index, team);
                teamCounter++;
            }
        } // end while (sc.hasNextLine())
        return teamStrings;
    }

    public List<List<TrainerPokemon>> getLeaderTeams(List<Trainer> gymLeaders, List<Integer> gymOrder, Map<String, Pokemon> pokeNameLookup) {
        List<List<TrainerPokemon>> teams = new ArrayList<>();
        // this.leaderTeams has keys like "Roark2" and maps to a list with strings like "Onix 30"
        for (int i = 0; i < gymLeaders.size(); i++) {
            Trainer t = gymLeaders.get(i);
            String tname = t.fullDisplayName.substring(t.fullDisplayName.lastIndexOf(' ') + 1);
            int newGymNum = gymOrder.indexOf(i) + 1;
            if (!this.leaderNames.contains(tname)) {// the trainer name is bad
                throw new LeaderTeamsException("Error in custom gym leader teams " + this.filename + "\n" +
                                "Did not parse a Gym Leader's name correctly: " + tname);
            }
            List<String> pokeList = this.leaderTeams.get(tname + newGymNum);
            if (pokeList == null) {
                teams.add(null);
                continue; // vanilla team, don't change it
            }
            List<TrainerPokemon> newPokemon = new ArrayList<>();
            for (String str : pokeList) {
                String pokeName = str.substring(0, str.lastIndexOf(' ')).toUpperCase();
                String levelStr = str.substring(str.lastIndexOf(' ') + 1);
                Pokemon poke = pokeNameLookup.get(pokeName);
                if (poke == null) {
                    throw new LeaderTeamsException("Error in custom gym leader teams " + this.filename + "\n" +
                                    "Problem with " + tname + "'s team #" + newGymNum + ": \"" + str + "\"\n" +
                                    "Could not find a Pokemon with the name \"" + pokeName + "\". Is it misspelled?");
                }
                int level;
                try {
                    level = Integer.parseInt(levelStr);
                } catch (NumberFormatException e) {
                    throw new LeaderTeamsException("Error in custom gym leader teams " + this.filename + "\n" +
                                    "Problem with " + tname + "'s team #" + newGymNum + ": \"" + str + "\"\n" +
                                    "Could not parse \"" + levelStr + "\" as a number.");
                }
                if (level < 1 || level > 100) {
                    throw new LeaderTeamsException("Error in custom gym leader teams " + this.filename + "\n" +
                                    "Problem with " + tname + "'s team #" + newGymNum + ": \"" + str + "\"\n" +
                                    level + " is an invalid level.");
                }
                TrainerPokemon tp = t.pokemon.get(0).copy(); // just use the first Pokemon in the original team as reference
                tp.pokemon = poke;
                tp.level = level;
                tp.resetMoves = true;
                newPokemon.add(tp);
            }
            //t.pokemon = newPokemon;
            teams.add(newPokemon);
        }
        return teams;
    }
}
