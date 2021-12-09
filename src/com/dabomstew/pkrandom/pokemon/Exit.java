package com.dabomstew.pkrandom.pokemon;

/*----------------------------------------------------------------------------*/
/*--  Exit.java - Represents a group of warp tiles that warp to the same    --*/
/*--              place.                                                    --*/
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

import java.util.Random;

public class Exit {
    public int minWeight; // the lowest badge required to reach this exit from the other exits
    public String destName; // the name of the Location that the Exit warps to in vanilla
    public int event; // the event number all tiles share
    public WarpData[] warps; // All the warp tile data
    public int mapIndex; // the map number all tiles share
    private int targetMap = -1; // the new map that this Exit warps to
    public Location thisLocation; // the Location object that this Exit belongs to
    public int matrixMap = -1; // if this Exit needs something about its map's tiles changed, this is set by the relevant constructor

    public Exit(int weight, String destName, int[] warpIDs, int mapIndex, Location thisLocation) {
        this.minWeight = weight;
        this.destName = destName;
        int warpCount = warpIDs.length;
        this.warps = new WarpData[warpCount];
        for (int i = 0; i < warpCount; i++) {
            this.warps[i] = new WarpData(warpIDs[i]);
        }
        this.mapIndex = mapIndex;
        this.thisLocation = thisLocation;
    }

    public static void connectExits(Exit exitA, Exit exitB, Random rand) {
        exitA.targetMap = exitB.mapIndex;
        exitB.targetMap = exitA.mapIndex;
        WarpData[] exitsA = exitA.warps;
        WarpData[] exitsB = exitB.warps;
        int exitAIndex = 0;
        int exitBIndex = 0;
        if (exitsA.length > 1) {
            exitAIndex = rand.nextInt(exitsA.length);
        }
        if (exitsB.length > 1) {
            exitBIndex = rand.nextInt(exitsB.length);
        }
        int newTargetWarpA = exitsB[exitBIndex].warp;
        int newTargetWarpB = exitsA[exitAIndex].warp;
        for (WarpData warp : exitA.warps) {
            warp.targetWarp = newTargetWarpA;
        }
        for (WarpData warp : exitB.warps) {
            warp.targetWarp = newTargetWarpB;
        }
    }

    public int getTargetMap() {
        return this.targetMap;
    }

    public static class WarpData {
        public int warp, targetWarp;
        // References to targetWarp aren't needed until the warps get rewritten.
        // Since every ExitSet in every Location *should* get touched by connectExits(),
        // all targetMaps and targetWarps should get written to before they're needed
        // Error handling exists if they aren't written (like when something stays in its vanilla location)
        public WarpData(int warp) {
            this.warp = warp;
            this.targetWarp = -1;
        }
    }
}


