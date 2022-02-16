# CarteExplorer

Run tests and install:
```
mvn install
```

mvn test
'''''

Run :
``` 
cd Executable
exec.sh [ demo1.in ]
```

## Enoncé, glossaire

Chasse aux trésors: Des aventuriers explorent une Carte et récoltent les Trésors qui s'y trouvent.
 
- Carte : Carte rectangulaire, elle contient 2 types de terrain (Plaine ou Montagne). Des trésors y ont été également déposés. Plusieurs trésors peuvent être présent au même endroit.
 
- Aventuriers : Ils se déplacent en suivant des mouvements fixés à l'avance (Avancer, tourner Gauche, tourner Droite).
Les aventuriers ne peuvent se déplacer que dans les plaines. Ils collectent au maximum un Trésor par tour, et seulement après un déplacement sur une nouvelle case (tourner Gauche ou Droite ne permet pas de récolter un 2nd trésor).

2 aventuriers ne peuvent se retrouver au même endroit de la carte. Le premier (ordre dans le fichier configuration) a la priorité dans l'execution des mouvements.

- La configuration initiale du jeu est fournie sous forme de fichier texte
```
# Carte (# définit des commentaires)
C - 3 - 4
# Montagne
M - 1 - 0
M - 2 - 1
# Tresor
T - 0 - 3 - 2
T - 1 - 3 - 3
A - Lara - 1 - 1 - S - AADADAGGA
A - Indiana - 2 - 0 - N - AGAGA
```

- Il est demandé d'afficher l'état de la carte sous forme graphique à la fin de la simulation, et les statistiques des trésors récoltés par les aventuriers, (a Frame):
```
.           M           A(Indiana)  
.           A(Lara)     M           
.           .           .           
T(2)        T(3)        .           
.           M           A(Indiana)  
.           .           M           
.           .           .           
A(Lara)     T(2)        .           
Il y a eu 9 tours 
Lara a collecté 3 trésors et a fini en 0 - 3 - SUD
Indiana a collecté 0 trésors et a fini en 2 - 0 - SUD
```

## Hexagonal architecture,
séparation renforcée par sous modules maven => obligation d'utiliser un port & adapter pattern (ou simialre)

- Domain Model (Metier) aucune dépendance aux autres couches

Configuration et Frame très similaire dans le contenu, mais format et utilisation différente.

Frame : snapshot à un temps T de la simulation. Objectif de rendering
Configuration: pour initialiser une simulation, contient EN PLUS les mouvements des Aventuriers

- AppConsole : application console pour réaliser l'exercice demandé.

- InputProvider : service pour lire les configurations initiales.

- Executable. Pour injecter une implémentation de InputProvider au métier, cette couche doit avoir accès aux autres couches.

