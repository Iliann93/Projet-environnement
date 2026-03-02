#!/bin/bash


# verify if bin exist
if [ -d "bin" ]; then
    echo "Nettoyage : suppression de l'ancien dossier bin..."
    rm -rf bin
fi

# Create directory
mkdir -p bin

# Run
javac -d bin ./src/*.java


if [ $? -eq 0 ]; then
    echo "Compilation réussie ! Lancement du programme..."
    java -cp bin MyEcosystem_predprey
else
    echo "Erreur lors de la compilation."
fi

