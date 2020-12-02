# CS348 Group Project
## Members: 
    Steven (Songtao) Leng
    Yuxuan Ji 
    Andy Shengding Zhang
    Craig Eric Lewis

to initialize, run

```bash
source ~db2inst2/sqllib/db2profile
chmod +x init.sh
. ./init.sh
```
loads the sql scripts, creates db and compiles java file

to start the `Main` program, run:
```bash
java project348.Main
```

to start individual modules, run:
Please run the features in the following order
1. Go through the trainer creation flow (press enter to proceed) and note down the trainer id assigned to you
```bash
java CreateTrainer
```
2. Try and capture at least 2 pokemon succesfully
```bash
java EncounterPokemon
```
3. Provides pokedex functionality allows searching by name, id and type
```bash
java SearchPokemon
```
4. Allows you to add, replace, removed pokemon from your party.
```bash
java ManageParty
```
5. Allows you to edit moves, abilities and nicknames of your pokemon
```bash
java EditPokemon
```
