## Projet initial

Exercice demandé pour un recrutement, je reprends l'énoncé le plus fidèlement possible.
Ou tout au moins de la manière dont je l'avais interprété.

### Enoncé, glossaire

Chasse aux trésors: Des aventuriers explorent une Carte et récoltent les Trésors qui s'y trouvent.
 
- Carte : Carte rectangulaire, elle contient 2 types de terrain (Plaine ou Montagne). Des trésors y ont été également déposés. Plusieurs trésors peuvent être présent au même endroit.
 
- Aventuriers : Ils se déplacent en suivant des actions fixées à l'avance. 
Les aventuriers ne peuvent se déplacer que dans les plaines.
Deux aventuriers ne peuvent se retrouver au même endroit de la carte. Le premier (ordre dans le fichier configuration) a la priorité dans l'execution des mouvements.

- Trésors : Les aventuriers collectent au maximum un Trésor par tour, et seulement après un déplacement sur une nouvelle case (Tourner Gauche ou Droite ne permet pas de récolter un 2nd trésor).

- Actions: A chaque tour, une action est appliquée au joueur concerné. 
	- A : Avancer
    - G : Tourner Gauche
    - D : Tourner Droite

Si un avanturier n'a plus d'actions à effectuer, il restera sur place.
Le jeu/simulation s'arrête lorque plus aucune action n'est disponible.

- La configuration initiale du jeu sera fournie sous forme de fichier texte

```
# Carte (# définit des commentaires)
# Taille de la Carte
C - 3 - 4
# Montagne, par défault plaine
M - 1 - 0
M - 2 - 1
# Tresor, position and nbre de trésors
T - 0 - 3 - 2
T - 1 - 3 - 3
# Aventurier, position et orientation initiale, actions à executer
A - Lara - 1 - 1 - S - AADADAGGA
A - Indiana - 2 - 0 - N - AGAGA
```

- Il est demandé d'afficher en console l'état de la carte sous forme graphique à la fin de la simulation, et les statistiques des trésors récoltés par les aventuriers, (a Frame):

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
