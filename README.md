# Projet FlowerShop

## Description

L'application **FlowerShop** est une application mobile développée en Kotlin permettant aux utilisateurs d'explorer, d'acheter et de gérer une collection de fleurs en ligne. Elle est conçue pour offrir une expérience utilisateur immersive grâce à l'intégration d'animations Lottie, une interface utilisateur intuitive et des fonctionnalités de gestion de favoris et de recherche avancée. Cette application utilise l'architecture MVC pour organiser le code de manière structurée et modulable.

## Fonctionnalités

- **Parcourir les Fleurs** : Affichage d'une collection variée de fleurs avec images, descriptions détaillées et prix.
- **Favoris** : Ajouter ou retirer des fleurs de la liste des favoris ; lorsqu'une fleur est marquée comme favorite, cela est synchronisé entre la liste principale et la liste de favoris.
- **Recherche Avancée** : Filtrer la liste des fleurs par nom grâce à une barre de recherche pour une navigation facilitée.
- **Animations Fluides** : Lottie animations intégrées pour améliorer l'interactivité et l'esthétique de l'application.
- **Notifications en Temps Réel** : Affichage de messages Snackbar pour confirmer les actions utilisateur comme l'ajout aux favoris.
- **Interface Responsive** : Effets de survol et menu utilisateur pour une navigation fluide.

## Prise en Main

L'application utilise la dernière version d'Android Studio et nécessite un appareil ou un émulateur Android API niveau 24 ou supérieur pour être exécutée de manière optimale.

## Prérequis

- **Android Studio** : Dernière version recommandée.
- **Appareil Android ou Emulateur** : API niveau 24 ou supérieur.
- **Bibliothèques** :  Lottie pour les animations.


# Installation

Clonez le dépôt :

git clone <repository-url>

Ouvrez le projet dans Android Studio.

Compilez et exécutez le projet sur un émulateur ou un appareil Android.

# Utilisation

Navigation : Parcourez la liste des fleurs disponibles dès l'ouverture de l'application.
Affichage des Détails : Appuyez sur une fleur pour voir ses détails, incluant nom, prix et description.
Gestion des Favoris : Utilisez le bouton Favoris pour sauvegarder vos fleurs préférées, visibles dans une section dédiée.
Recherche : Utilisez la barre de recherche pour trouver rapidement une fleur spécifique.
Filtrage et Partage : Filtrez les fleurs par nom et partagez des détails depuis l'interface de détail.

# Architecture et Organisation

**Architecture MVC :**
Model : Gestion des données avec des classes Bean pour les fleurs et Adapter pour les affichages.
Controller : Composants DAO et Service implémentant DAO pour la logique de l'application.
View : Activités avec des fichiers XML pour chaque interface utilisateur, intégrant RecyclerView, Lottie animations, et Snackbar pour l'interactivité.

# Technologies Utilisées

Kotlin pour le code principal.

Lottie Animations pour des effets visuels améliorés.

RecyclerView pour lister les fleurs.

# Vidéo

https://github.com/user-attachments/assets/1a9d72a9-3011-4275-9ce3-f4e8ac9828e7
