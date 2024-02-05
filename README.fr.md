# Chess

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![fr](https://img.shields.io/badge/lang-fr-yellow.svg)](README.fr.md)

Vous trouverez ici le projet de l'UE Programmation Orienté Objet 2. Le sujet du projet, le rapport et les diagrammes UML
se trouvent sous `extras/`.

Il s'agit d'un simple jeu d'échecs (multijoueur seulement) fait entièrement en Java à partir de zéro avec une interface graphique [JavaFX](https://openjfx.io/).

![Un aperçu de l'interface graphique du jeu](images/example.png "L'interface graphique")

## Étudiants

**Efe ERKEN**

Année : L2S4 Printemps 2023

Groupes : TD2-TP4

## Problèmes rencontrés

Voici, vous pouvez trouver les problèmes que j'ai eus lors du développement et comment je les ai résolus (ou pas).

### Grille de jeu

Pour modéliser le plateau de jeu, j'ai créé un conteneur représentant chaque case qui peut contenir une pièce ou pas
ainsi que d'autres informations comme le danger de la case et le statut de selection (quand le joueur sélectionne une
case pour jouer une pièce et voir les cases auxquelles bouger). J'ai créé un tableau à deux dimensions de ces cases pour
représenter la grille de jeu.

### Les pièces

Pour les pièces, j'ai créé une classe abstraite, mais à un moment, j'ai dû séparer les pièces royales et les pièces
normales, car seule une pièce normale peut mettre une pièce royale en échec. J'ai dû encore étendre cette classe
abstraite avec d'autres classes abstraites pour specifier plus le type de pièce et sa capacité au lieu d'avoir des
surcharges vides de méthodes.

### Le mouvement des pièces

J'ai dû inventer un système de calcul de danger pour chaque case pour éviter des coups illégaux par le roi. Mais aussi
un système de calcul des coordonnées légal pour d'autres pièces en cas d'échec pour ne permettre que des coups légaux
(des coups qui sauvent le roi seulement). Pour cela, j'ai rajouté plein de méthodes et j'ai structuré le calcul des
coups pour chaque pièce à une suite de verifications qui sont utilisé par une méthode qui traverse tous les coups
possibles pour chaque pièce qui sont par après éliminé encore s'il y a un cas d'échec ou de danger. Ils sont finalement
rajouté à la liste des coordonnées possibles de la pièce. Chaque pièce vérifie si les coordonnées de sa destination
sont dans la grille, sont bien dans son modèle de mouvement ainsi que d'autres verifications supplémentaires comme s'il
n'y a bien aucune autre pièce entre la pièce et sa destination pour des pièces qui ne sautent pas et des verifications pour
des coups spéciaux que la pièce peut avoir ("en passant" pour le pion par exemple).

### La boucle de jeu

J'ai fait en sorte que les pièces de la couleur opposée à celle qui va jouer, calculent leurs coups en premier
pour déterminer le danger avant le calcul de l'autre couleur, qui a besoin de cette information pour trouver les bons
coups possibles.

La classe `Game` est le point central de la logique du jeu. Il gère la boucle en assurant le calcul des coups, leur
effectuation, avancement des tours, l'historique des coups et la détermination de la fin du jeu.

### Interface graphique

J'ai utilisé JavaFX et FXML pour l'interface graphique. J'ai utilisé un `GridPane` de 8 colonnes et 8 lignes avec des
`StackPane` dans chaque case qui contiennent elles-mêmes des images et des rectangles pour afficher les pieces et la
couleur des cases en cas de sélection.

Par après, j'ai aussi ajouté des boutons (annuler, refaire, recommencer) et des alerts (qu'est-ce qu'il faut faire à la
fin du jeu) pour plus d'interaction avec l'utilisateur.

## Versions JDK

J'ai utilisé comme binaires de JDK Java les suivants : Oracle OpenJDK, Oracle JDK et Eclipse Temurin OpenJDK by Adoptium.

J'ai fait attention à tester mon programme sur les machines de l'UFR pour vérifier les erreurs avant de rendre sur
Moodle.

Ci-dessous sont les versions de JDK que j'ai le plus utilisées lors du développement sur ma machine personnelle et à
l'UFR.

```text
openjdk 17.0.7 2023-04-18
OpenJDK Runtime Environment Homebrew (build 17.0.7+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.7+0, mixed mode, sharing)

openjdk 11.0.19 2023-04-18
OpenJDK Runtime Environment Homebrew (build 11.0.19+0)
OpenJDK 64-Bit Server VM Homebrew (build 11.0.19+0, mixed mode)

openjdk 11.0.18 2023-01-17
OpenJDK Runtime Environment (build 11.0.18+10-post-Ubuntu-0ubuntu120.04.1)
OpenJDK 64-Bit Server VM (build 11.0.18+10-post-Ubuntu-0ubuntu120.04.1, mixed mode, sharing)
```

Le fonctionnement du jeu est assuré avec **Java 17** et je vous le conseille fortement (j'ai testé de manière approfondie avec
cette version).

## Commandes d'utilisation

### Comment compiler et exécuter ?

D'abord, pour compiler, installez au minimum un JDK Java **version 17**.

Puis, téléchargez le projet sur votre machine avec `git clone` ou en téléchargeant l'archive du projet.

Une fois c'est fait, rendez-vous dans le répertoire du projet et compilez. Les fichiers compilés se trouvent
sous `build/` et le fichier unique `.jar` sous `build/libs/`. Si c'est votre première fois à compiler, cela peut prendre
un peu de temps. **Attention** si vous êtes sous Windows, utilisez `gradlew.bat` au lieu de `./gradlew`.

```sh
cd chess/

./gradlew build
```

Désormais, vous pouvez exécuter le jeu tant que vous êtes dans le répertoire de celui-ci. Ou normalement, vous pouvez
double cliquer sur le `.jar` suffixé `-all` (ou la commande `java -jar <*.jar>`) à condition que votre
installation Java soit bien fait (environnement de variable pour la bonne version de Java bien configuré (`JAVA_HOME`)
ainsi que l'application par défaut pour lancer les fichiers `.jar`).

```sh
./gradlew run
```

### Génération de la documentation

Pour générer la documentation pour votre copie du programme, utilisez la commande suivante et jeter un œil au
fichier `build/docs/javadoc/index.html` dans votre navigateur de web préféré.

```sh
./gradlew javadoc
```

### Lancer les tests

Pour lancer les tests unitaires tapez cette commande :

```sh
./gradlew test
```

### Comment nettoyer ?

Pour nettoyer le répertoire du projet pour repartir à zéro :

Effacer les fichiers de compilation, executable final et la documentation (`build/*`).

```sh
./gradlew clean
```

## Dépôt Git

Le dépôt git de [ce projet](https://git.unistra.fr/erken/chess) suit une structure claire et déterminée proposée par
Vincent Driessen à son
poste [A successful Git branching model](https://nvie.com/posts/a-successful-git-branching-model/).

Du coup ne soyez pas surpris par le fait que `branch main` n'a presque pas de commit. Tout le développement se passe sur
le `branch develop`. Avant chaque version, tout est préparé et assuré fonctionnel pour être inauguré au `branch main`
qui n'a que des versions stables et complètes.

### Légende emoji

La signification des emojis utilisé dans les descriptions des commits git.

| Emoji       | Signification                                                         |
|:------------|:----------------------------------------------------------------------|
| ✨ NEW      | Nouveau fichier ou fonctionnalité                                     |
| 🔧 UPDATE   | Mise à jour d'une partie de programme                                 |
| 🔨 CONFIG   | Manipulation des fichiers de configuration comme makefile ou doxyfile |
| ♻️  REFACTOR | Réécriture d'une partie du programme                                  |
| 🐛 BUGFIX   | Une correction de bogue                                               |
| 🔥 DELETION | Suppression d'un fichier ou d'une fonctionnalité                      |
| 📝 DOC      | Manipulation de la documentation                                      |
| 🎉 EPOCH    | Le début du projet                                                    |
| 🚀 RELEASE  | Une nouvelle version du programme                                     |
