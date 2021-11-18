Pokemon Entrance Randomizer by SilverstarStream

Based on Universal Pokemon Randomizer ZX by Ajarmar

Based on the Universal Pokemon Randomizer by Dabomstew

# Info

This randomizer fork implements several entrance randomization settings on top of Universal Pokemon Randomizer ZX.

Currently only Pokemon Platinum (U) is supported. Other (U) games are planned to be supported in the future.

Have a look at the [release page](https://github.com/SilverstarStream/pokemon-entrance-randomizer/releases) for changelogs and downloads.

# External Libraries

This project uses the following open source software:
- [jackson](https://github.com/FasterXML/jackson) ([Apache Software License, Version 2.0](https://github.com/FasterXML/jackson-core/blob/2.14/LICENSE); Currently using 2.13.0) includes:
  - [jackson-core](https://github.com/FasterXML/jackson-core) ([download](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-core/2.13.0/bundle))
  - [jackson-databind](https://github.com/FasterXML/jackson-databind) ([download](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-databind/2.13.0/bundle))
  - [jackson-annotations](https://github.com/FasterXML/jackson-annotations) ([download](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-annotations/2.13.0/bundle))

To run this fork from source code, first follow the instructions [here](https://github.com/Ajarmar/universal-pokemon-randomizer-zx/wiki/Building-Universal-Pokemon-Randomizer-ZX).
Once finished, set up an external libraries folder somewhere. Download all three of the above jackson libraries as jar into that folder. Then under File -> Project Structure -> Libraries, use the '+' button to add libraries, select Java, and select the folder containing the jackson jars you downloaded.

# Known Issues
- (Platinum, Gym Shuffler) Hearthome (Fantina’s) Gym has a darkness effect that is removed once Fantina is beaten. If Hearthome Gym is shuffled to be after Hearthome City, the darkness effect will not be present while challenging the gym. I can confirm that the game specifically checks for badge number 4 (0-based) for this effect, and I am almost certain the effect is not controlled by scripts.


- (Platinum, Gym Shuffler) After each gym leader is beaten, they have some dialogue for the player. This dialogue is edited to properly reflect the name of the badge given to the player and the name of the HM that becomes usable. Depending on which dialogue gets which badge and HM, the text can exceed the width of the text box. This shouldn't cause crashes, but the ends of lines can be cut off.

# Bug reports

If you encounter something that seems to be a bug, submit an issue using the `Bug Report` issue template.

# Suggestions

If you have an idea to improve the entrance randomizer, you can suggest it by submitting an issue using the `Suggestion` template. If the suggestion would entail lots of work for me, or if I don't think it makes a meaningful enough addition to the randomizer, it will probably be rejected. Better yet, you can add it to the randomizer yourself with a pull request! Just make sure to propose the idea first with the `Contribution Idea` template.

# Other problems

If you have problems using the randomizer, it could be because of some problem with Java or your operating system. **If you have problems with starting the randomizer specifically, [read this page first before creating an issue.](https://github.com/Ajarmar/universal-pokemon-randomizer-zx/wiki/About-Java)** If that page does not solve your problem, submit an issue using the `Need Help` issue template.

## Special Thanks

Spiky-Eared Pichu for SDSME. The program itself helped me understand how Platinum handles most everything related to maps, warps, and scripts, and its source code contained most everything I needed to know to develop my randomizer settings.

Ajarmar and tom-overton for their initial feedback to these settings and advice.
