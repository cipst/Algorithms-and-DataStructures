#!/bin/bash

RED="\033[1;31m"
GREEN="\033[1;32m"
MAGENTA="\033[1;35m"
BOLD="\033[1m"
NC="\033[0m"

if [ "$1" == "--clear" ] || [ "$1" == "-c" ]
then
    rm -r classes/*
    echo -e "> ${RED}classes/${NC} cleared successfully"
elif [ "$1" == "--help" ] || [ "$1" == "-h" ]
then
    echo -e "\n❔${BOLD}HELP${NC}❔"
    echo -e "Use option -c or --clear to clear the ${RED}classes/${NC} folder"
    echo -e "Use ${BOLD}«./run.sh»${NC} to compile and launch ${BOLD}MinimumHeapTests${NC}\n"
else
    cd src
    echo -e "${MAGENTA}Compiling...${NC}"
    javac -d ../classes -cp ".:../../Resources/Java/JUnit/*" *.java
    echo -e "${GREEN}Done ✔️${NC}\n"

    cd ../classes
    echo -e "${MAGENTA}Running tests...${NC}"
    java -cp ".:../../Resources/Java/JUnit/*" MinimumHeapTestsRunner
    echo -e "${GREEN}Done ✔️${NC}"
fi