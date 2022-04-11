package com.dabomstew.pkrandom.pokemon;

/*----------------------------------------------------------------------------*/
/*--  ScriptData.java - Represents a script.                                --*/
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
import java.util.*;

public class ScriptData {
    public int scriptNum; // file number
    public int headerEndOff = 0; // The offset of the headerEnd delimiter, typically 0x13FD
    public int enableOff; // The offset of the command that grants the player the badge
    public int nextJumpOff; // The offset of the end of the section of the script that resolves the player defeating the leader
    public int flagsSize = 0; // obsolete
    public byte[] script;
    public List<Byte> scriptList = new ArrayList<>(); // The script in list form for easy insertion and deletion of elements

    public List<Integer> setVarOffs = new ArrayList<>(); // List of the original offsets of the setVar commands that are stored
    public List<Integer> flagOffs = new ArrayList<>(); // ^ for flag commands
    public List<Integer> allOffs = new ArrayList<>(); // Contains the contents of both above Lists; should be sorted
    public Map<Integer, Integer> setVarMap = new HashMap<>(); // maps the offset of each setVar command to its argument
    public Map<Integer, Integer> flagMap = new HashMap<>(); // ^ for flag commands

    public List<JumpCommand> jumpCommands = new ArrayList<>();

    public List<Integer> effectOffs = new ArrayList<>();
    public Map<Integer, Integer> effectMap = new HashMap<>(); // map the offset to the argument of the visuals of a player getting a badge

    public ScriptData(int scriptNum, byte[] script) {
        this.scriptNum = scriptNum;
        this.script = script.clone();
        for (byte b : script) {
            this.scriptList.add(b);
        }
    }

    public void updateScriptArray() {
        this.script = new byte[this.scriptList.size()];
        for (int i = 0; i < this.scriptList.size(); i++) {
            this.script[i] = this.scriptList.get(i);
        }
        for (JumpCommand jump : jumpCommands) {
            int addr = jump.address;
            int argBytes = jump.argByteCount;
            int jumpDist = jump.jumpDist;
            this.scriptList.set(addr + argBytes + 2, (byte) (jumpDist & 0xFF));
            this.scriptList.set(addr + argBytes + 3, (byte) (jumpDist >> 8 & 0xFF));
            this.scriptList.set(addr + argBytes + 4, (byte) (jumpDist >> 16 & 0xFF));
            this.scriptList.set(addr + argBytes + 5, (byte) (jumpDist >> 24 & 0xFF));
            this.script[addr + argBytes + 2] = (byte) (jumpDist & 0xFF);
            this.script[addr + argBytes + 3] = (byte) (jumpDist >> 8 & 0xFF);
            this.script[addr + argBytes + 4] = (byte) (jumpDist >> 16 & 0xFF);
            this.script[addr + argBytes + 5] = (byte) (jumpDist >> 24 & 0xFF);
        }
    }

    public int readWord(int offset) {
        return (this.scriptList.get(offset) & 0xFF) | ((this.scriptList.get(offset + 1) & 0xFF) << 8);
    }

    public int readLong(int offset) {
        return (this.scriptList.get(offset) & 0xFF) | ((this.scriptList.get(offset + 1) & 0xFF) << 8) |
                ((this.scriptList.get(offset + 2) & 0xFF) << 16) | ((this.scriptList.get(offset + 3) & 0xFF) << 24);
    }

    public void toFile() {
        File outputFile = new File("" + this.scriptNum);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(this.script);
        } catch (IOException e) {
            throw new RandomizationException("Error writing script file " + this.scriptNum);
        }
        System.out.println("Exported script file " + this.scriptNum + " to system.");
    }

    public static class JumpCommand {
        public int address, argByteCount, jumpDist;

        public JumpCommand(int address, int argByteCount, int jumpDist) {
            this.address = address;
            this.argByteCount = argByteCount;
            this.jumpDist = jumpDist;
        }
    }
}
