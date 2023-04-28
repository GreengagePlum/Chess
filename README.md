# Chess

Vous trouverez ici le projet de l'UE Programmation Orienté Objet 2. Le sujet du projet, le rapport et les diagrammes UML
se trouvent sous `extras/`.

![Un aperçu de l'interface graphique du jeu](images/example.png)

## Étudiants

**Efe ERKEN**

Année : L2S4 Printemps 2023

Groupes : TD2-TP4

## Problèmes rencontrés

Voici, vous pouvez trouver les problèmes que j'ai eus lors du développement et comment je les ai résolus (ou pas).

[//]: # (### Superposition joueur-objectif)

[//]: # ()

[//]: # (Dans la première version v1.0.0 où on devait juste gérer le mouvement du joueur dans le vide et sur les objectifs, j'étais bloqué car j'arrivais pas à passer le joueur sur les objectifs. Ceci était dû au fait que mon implémentation de départ était problématique. Pour bouger le joueur dans le sens voulu, j'échangeais la case dans le sens voulu et la case où se situait le joueur. Cette méthode ne marchait bien que quand la case dans le sens voulu était vide. Si elle était un objectif, cela revenait à modifier l'emplacement de l'objectif. J'ai dû repenser mon approche.)

[//]: # ()

[//]: # (J'ai passé à une méthode conditionnelle où en fonction des cases concernées, je modifie manuellement ces cases. J'ai dû étendre les représentations des cases dans la structure de jeu pour représenter la superposition du joueur avec un objectif. Grâce à cela, j'ai pu gérer le mouvement sans bouger les emplacements des objectifs et sans utiliser d'autres champs de données dans la structure de jeu pour stocker les positions de chaque objectif.)

## Versions JDK

J'ai utilisé comme JDK Oracle OpenJDK.

J'ai fait attention à tester mon programme sur les machines de l'UFR pour vérifier les erreurs avant de rendre sur
Moodle.

Ci-dessous sont les versions de JDK que j'ai utilisées.

```
openjdk 19.0.2 2023-01-17
OpenJDK Runtime Environment Homebrew (build 19.0.2)
OpenJDK 64-Bit Server VM Homebrew (build 19.0.2, mixed mode, sharing)

openjdk 17.0.6 2023-01-17
OpenJDK Runtime Environment Homebrew (build 17.0.6+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.6+0, mixed mode, sharing)

openjdk 11.0.18 2023-01-17
OpenJDK Runtime Environment Homebrew (build 11.0.18+0)
OpenJDK 64-Bit Server VM Homebrew (build 11.0.18+0, mixed mode)

openjdk 11.0.18 2023-01-17
OpenJDK Runtime Environment (build 11.0.18+10-post-Ubuntu-0ubuntu120.04.1)
OpenJDK 64-Bit Server VM (build 11.0.18+10-post-Ubuntu-0ubuntu120.04.1, mixed mode, sharing)
```

## Commandes d'utilisation

### Comment compiler et exécuter ?

D'abord, installez au minimum Java 11.

Puis téléchargez le projet sur votre machine avec une des commandes qui suivent :

```
git clone https://git.unistra.fr/erken/sokoban.git
```

ou

```
git clone git@git.unistra.fr:erken/sokoban.git
```

Une fois c'est fait, rendez-vous dans le répertoire du projet et compilez. Les fichiers compilés se trouvent
sous `build/` et le fichier unique `.jar` sous `build/libs/`.

```
cd chess/

./gradlew build
```

Désormais, vous pouvez exécuter le jeu tant que vous êtes dans le répertoire de celui-ci. Ou normalement, vous pouvez
double cliquer sur le `.jar` annoté `-all` pour lancer.

```
./gradlew run
```

### Génération de la documentation

D'abord, installez les dépendances avec votre gestionnaire de paquets (apt, dnf, apk, etc.) : **javadoc**.

Pour générer la documentation pour votre copie du programme, utilisez la commande suivante et jeter un œil au
fichier `build/docs/javadoc/index.html` dans votre navigateur de web préféré.

```
./gradlew javadoc
```

### Comment nettoyer ?

Pour nettoyer le répertoire du projet pour repartir à zéro :

Effacer les fichiers de compilation, executable final et la javadoc (build/*).

```
./gradlew clean
```

## Dépôt Git

Le dépôt git de [ce projet](https://git.unistra.fr/erken/sokoban) suit une structure claire et déterminée proposée par
Vincent Driessen à son
poste [A successful Git branching model](https://nvie.com/posts/a-successful-git-branching-model/).

Du coup ne soyez pas surpris par le fait que `branch main` n'a presque pas de commit. Tout le développement se passe sur
le `branch develop`. Avant chaque version, tout est préparé et assuré fonctionnel pour être inauguré au `branch main`
qui n'a que des versions stables et complètes.

### Légende emoji

La signification des emojis utilisé dans les descriptions des commits git.

| Emoji       | Signification                                                         |
|:------------|:----------------------------------------------------------------------|
| ✨ NEW       | Nouveau fichier ou fonctionnalité                                     |
| 🔧 UPDATE   | Mise à jour d'une partie de programme                                 |
| 🔨 CONFIG   | Manipulation des fichiers de configuration comme makefile ou doxyfile |
| ♻️ REFACTOR | Réécriture d'une partie du programme                                  |
| 🐛 BUGFIX   | Une correction de bogue                                               |
| 🔥 DELETION | Suppression d'un fichier ou d'une fonctionnalité                      |
| 📝 DOC      | Manipulation de la documentation                                      |
| 🎉 EPOCH    | Le début du projet                                                    |
| 🚀 RELEASE  | Une nouvelle version du programme                                     |
