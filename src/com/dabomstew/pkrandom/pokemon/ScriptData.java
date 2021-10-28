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
    public int scriptNum;
    public int headerEndOff;
    public int enableOff;
    public int nextJumpOff;
    public int instByteCount = 0;
    public byte[] script;
    public List<Byte> scriptList = new ArrayList<Byte>();

    public List<Integer> setVarOffs = new ArrayList<Integer>();
    public List<Integer> flagOffs = new ArrayList<Integer>();
    public List<Integer> allOffs = new ArrayList<Integer>();
    public Map<Integer, Integer> setVarMap = new HashMap<Integer, Integer>();
    public Map<Integer, Integer> flagMap = new HashMap<Integer, Integer>();

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
        temp = this.instByteCount;
        this.instByteCount = that.instByteCount;
        that.instByteCount = temp;
        byte[] tempArray = this.script;
        this.script = that.script;
        that.script = tempArray;
        List<Byte> tempList = this.scriptList;
        this.scriptList = that.scriptList;
        that.scriptList = tempList;
    }
}
