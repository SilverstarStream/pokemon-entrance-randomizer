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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptData {
    public int scriptNum; // file number
    public int headerEndOff; // The offset of the headerEnd delimiter, typically 0x13FD
    public int enableOff; // The offset of the instruction that grants the player the badge
    public int nextJumpOff; // The offset of the end of the section of the script that resolves the player defeating the leader
    public int storedByteCount = 0; // holds how many bytes of stored flag and setVar commands there are
    public byte[] script;
    public List<Byte> scriptList = new ArrayList<>(); // The script in list form for easy insertion and deletion of elements

    public List<Integer> setVarOffs = new ArrayList<>(); // List of the original offsets of the setVar commands that are stored
    public List<Integer> flagOffs = new ArrayList<>(); // ^ for flag commands
    public List<Integer> allOffs = new ArrayList<>(); // Contains the contents of both above Lists; should be sorted
    public Map<Integer, Integer> setVarMap = new HashMap<>(); // maps the offset of each setVar command to its argument
    public Map<Integer, Integer> flagMap = new HashMap<>(); // ^ for flag commands

    public ScriptData(int scriptNum, byte[] script) {
        this.scriptNum = scriptNum;
        this.script = script.clone();
        for (byte b : script) {
            this.scriptList.add(b);
        }
    }

    public byte[] updateScriptArray() {
        this.script = new byte[this.scriptList.size()];
        for (int i = 0; i < this.scriptList.size(); i++) {
            this.script[i] = this.scriptList.get(i);
        }
        return this.script;
    }

    public void swapScriptData(ScriptData that) {
        int temp = this.scriptNum;
        this.scriptNum = that.scriptNum;
        that.scriptNum = temp;
        temp = this.headerEndOff;
        this.headerEndOff = that.headerEndOff;
        that.headerEndOff = temp;
        temp = this.enableOff;
        this.enableOff = that.enableOff;
        that.enableOff = temp;
        temp = this.nextJumpOff;
        this.nextJumpOff = that.nextJumpOff;
        that.nextJumpOff = temp;
        temp = this.storedByteCount;
        this.storedByteCount = that.storedByteCount;
        that.storedByteCount = temp;
        byte[] tempArray = this.script;
        this.script = that.script;
        that.script = tempArray;
        List<Byte> tempList = this.scriptList;
        this.scriptList = that.scriptList;
        that.scriptList = tempList;
    }
}
