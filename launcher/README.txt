1. IF YOU FIND A BUG

First, make sure the bug you're reporting relates to the Entrance Randomizer. Submit a Bug Report issue here:
https://github.com/SilverstarStream/pokemon-entrance-randomizer/issues/new/choose

2. HOW TO USE

Just double click the launcher script that matches your operating system and it will start the randomizer program.

Windows: Use launcher_WINDOWS.bat
Mac: Use launcher_MAC.command
Other Unix-based systems: Use launcher_UNIX.sh

DO NOT change the name of the randomizer program as this will cause the launcher to fail.
The launcher program and the randomizer program must be in the same folder.
The launcher is necessary for being able to randomize 3DS games.


3. TROUBLESHOOTING

Please see here if you run into problems with the launcher and need help:
https://github.com/Ajarmar/universal-pokemon-randomizer-zx/issues/221

If this does not solve your launcher issue, submit a Need Help issue here:
https://github.com/SilverstarStream/pokemon-entrance-randomizer/issues/new/choose


Pokemon Entrance Randomizer Information

--- Gym Shuffler Information

Gym shuffling connects each of the gyms to a random gym city. In other words, entering a gym will lead to random gym. The gym trainers and gym leader of each gym are scaled to match the levels and evolutions of the original gym. The badge received is always tied to the city, so for example the gym in Oreburgh City will always give the Coal Badge. Any events that occur outside the gym after completing it are also kept to the city.


--- Map Randomizer Information

The map randomizer expects the player to complete the gym cities in order. So for example, in Platinum, the order is Oreburgh City first, then Eterna City, and so on until Sunyshore City. It will also be this city order even if Gym Shuffling is also turned on.

However, from a small amount of testing, Platinum seems to allow for out-of-order gym completion if the player wishes to take on a gym they are underleveled for. This out-of-order completion does not seem to have any effect on game completability.

The Map Randomizer does not stop a player from entering a Location that has been scaled for late-game before they are "supposed" to access it.
There are two consequences of this:
1. A player can catch a Pokemon that is overleveled and steamroll at least a portion of the game.
2. A player can enter a Trainer battle without knowing how strong they are and wipe.

The first scenario should be self-correcting by the player. If a player wishes to use something overleveled and finds that experience enjoyable, there’s no reason not to do so. If a player would find that experience unenjoyable, they can simply use a self-imposed level cap.

However, the more concerning problem is losing to an unbeatable Trainer. For Nuzlockers and other challenge players where fainting Pokemon has permanent consequences, they should use the spoiler log to determine which routes are safe to access at which badges. If a player without those restrictions does not wish to use the spoiler log, there should be no issue stumbling into a Trainer battle, wiping, and trying a different route to progress.


--- How does the Map Randomizer make a map?

Imagine all of the areas in the game, like cities and routes, then group areas together if there’s no loading zone between them. So in Platinum, Twinleaf Town, Sandgem Town, and Jubilife City are all one area, since there are no loading zones separating them. Each area in the game that is completely separated from the others by loading zones is called a Location. All the loading zones to other Locations are called Exits (or Entrances, it doesn’t matter). So, each Location will have at least one Exit to other Locations.

Starting with the starting Location, the randomizer connects Locations one at a time to one of the Exits on the existing map. Normally it first connects a route, then a gym city to that route. It continues to do this until there are no more cities to place. However, since there are more routes than there are cities, there are leftover routes to place. So, periodically during the route + gym placement cycle, it will randomly place a route between two Locations so that once randomization finishes the map has the following properties:
- the map is closed (every Exit is connected)
- each pair of Exits that have been connected only connect to each other
- there are no small loops (a Location cannot connect to two Exits of the same Location)
- the map cannot cause a softlock (the game can be beaten)
- Trainers and wild Pokemon levels and evolutions are scaled to the badge the player can be expected to have at that point.

A few more notes: areas that are completely contained by an overworld Location are not randomized. For example, in Platinum, this includes all three lakes and Wayward Cave. Also, building entrances are not randomized by any setting, except for gyms.

List of randomized Locations in Platinum:
Jubilife City (starting Location)
Oreburgh Gate
Oreburgh City
Ravaged Path
Eterna City & Floaroma Town
Eterna Forest
Cycling Road
S. Coronet Cave
Route 208
Hearthome City
Solaceon & Celestic Towns and Route 215
Veilstone City
Valor Lakefront and Routes 213 & 214
Pastoria City
Route 218
Canalave City
N. Coronet Cave [lower] (Strength boulders)
N. Coronet Cave [upper] (Foggy lake)
Snowpoint City and Routes 216 & 217