#!/bin/sh

CLASSDIR="$(pwd)"

### Prepareation (same as Assignment 2 Part II)
# You may comment them out if you don't need to use them
MTEST1_DATDIR="$(pwd)/queries/realdb/"
cd $MTEST1_DATDIR
db2 -stvf connectCS348.sql
db2 -stvf droptables.sql
db2 -stvf createtables.sql
db2 -stvf populatetables.sql

### Testing script for CS348 W18 A2
cd $CLASSDIR
## Compile source code
CLASSFILE1="project348/EditPokemon.class"
CLASSFILE2="project348/ManageParty.class"
CLASSFILE3="project348/SearchPokemon.class"
CLASSFILE4="project348/CreateTrainer.class"
CLASSFILE5="project348/EncounterPokemon.class"
CLASSFILE6="project348/Main.class"


if [ \( -f $CLASSFILE1 \) -a \( -f $CLASSFILE2 \) -a \( -f $CLASSFILE3 \) -a \( -f $CLASSFILE4 \) -a \( -f $CLASSFILE5 \) -a \( -f $CLASSFILE6 \) ] 
then
   rm $CLASSFILE1
   rm $CLASSFILE2
   rm $CLASSFILE3
   rm $CLASSFILE4
   rm $CLASSFILE5
   rm $CLASSFILE6
   echo "Clean class files"
fi

chmod +x compile
./compile 
echo "Compiling"

## Check for *.class output
if ! [ \( -f $CLASSFILE1 \) -a \( -f $CLASSFILE2 \) -a \( -f $CLASSFILE3 \) -a \( -f $CLASSFILE4 \) -a \( -f $CLASSFILE5 \) -a \( -f $CLASSFILE6 \) ] 
then 
    echo "CLASS FILE NOT Found. ABORT"
    exit 1 
else 
    echo "Sample initiated..."
fi

