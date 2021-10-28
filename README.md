Pokemon Entrance Randomizer by SilverstarStream

Based on Universal Pokemon Randomizer ZX by Ajarmar

Based on the Universal Pokemon Randomizer by Dabomstew

# Info

This randomizer fork implements several entrance randomization settings for Universal Pokemon Randomizer ZX.

Currently only Pokemon Platinum (U) is supported. Other (U) games are planned to be supported in the future.

# Known Issues
- (Platinum, Gym Shuffler) Hearthome (Fantina’s) Gym has a darkness effect that is removed once Fantina is beaten. If Hearthome Gym is shuffled to be after Hearthome City, the darkness effect will not be present. I can confirm that the game specifically checks for badge number 4 (0-based) for this effect, and that the effect should not be controlled by any of the script files.


- (Platinum, Gym Shuffler) After each gym leader is beaten, they have some dialogue for the player. This dialogue gets edited to properly reflect the name of the badge given to the player and the name of the HM that becomes usable. Depending on which dialogue gets which badge and HM, the text can exceed the width of the text box. This shouldn’t cause crashes, but the ends of lines can be cut off.

# Bug reports

If you encounter something that seems to be a bug, submit an issue using the `Bug Report` issue template.

# Other problems

If you have problems using the randomizer, it could be because of some problem with Java or your operating system. **If you have problems with starting the randomizer specifically, [read this page first before creating an issue.](https://github.com/Ajarmar/universal-pokemon-randomizer-zx/wiki/About-Java)** If that page does not solve your problem, submit an issue using the `Need Help` issue template.


## Special Thanks

Spiky-Eared Pichu for SDSME. The program itself helped me understand how Platinum handles most everything related to maps, warps, and scripts, and its source code contained most everything I needed to know to develop my randomizer settings.