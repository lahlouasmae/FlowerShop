// Définition du package de l'activité
package ma.ensa.projet

// Importation des classes nécessaires
import android.os.Bundle // Pour gérer l'état de l'activité
import android.widget.Button // Pour utiliser le bouton d'interaction
import androidx.appcompat.app.AppCompatActivity // Pour créer une activité qui hérite d'AppCompatActivity
import com.airbnb.lottie.LottieAnimationView // Pour utiliser les animations Lottie
import android.widget.TextView // Pour afficher du texte dans l'interface
import com.example.lottie.R // Pour référencer les ressources du projet
import com.google.android.material.snackbar.Snackbar // Pour afficher un message temporaire en bas de l'écran
import ma.ensa.projet.beans.Flower // Pour utiliser l'objet Flower qui représente une fleur

// Déclaration de la classe `FlowerDetailActivity` qui hérite de `AppCompatActivity`
class FlowerDetailActivity : AppCompatActivity() {

    // Déclaration des propriétés de l'activité
    private lateinit var favoriteButton: Button // Bouton pour ajouter ou retirer des favoris
    private var isFavorite = false // Booléen indiquant l'état de favori (ajouté ou non aux favoris)
    private lateinit var currentFlower: Flower // Objet représentant la fleur actuellement affichée

    // Méthode appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Liaison avec la vue XML associée à l'activité
        setContentView(R.layout.activity_flower_detail)

        // Récupération des données envoyées par l'intent (nom, prix, ressource d'animation, description, image)
        val flowerName = intent.getStringExtra("flower_name") // Nom de la fleur
        val flowerPrice = intent.getDoubleExtra("flower_price", 0.0) // Prix de la fleur
        val animationRes = intent.getIntExtra("animation_res", -1) // Ressource d'animation Lottie
        val flowerDescription = intent.getStringExtra("flower_description") // Description de la fleur
        val flowerImageRes = intent.getIntExtra("flower_image", -1) // Ressource d'image de la fleur

        // Référence aux TextView et LottieAnimationView dans la vue
        val flowerNameTextView = findViewById<TextView>(R.id.flowerNameTextView) // TextView pour afficher le nom
        val flowerPriceTextView = findViewById<TextView>(R.id.flowerPriceTextView) // TextView pour afficher le prix
        val flowerDescriptionTextView = findViewById<TextView>(R.id.flowerDescriptionTextView) // TextView pour afficher la description
        val flowerAnimationView = findViewById<LottieAnimationView>(R.id.flowerAnimationView) // LottieAnimationView pour l'animation
        favoriteButton = findViewById(R.id.favoriteButton) // Référence au bouton de favoris

        // Vérification que toutes les données nécessaires sont disponibles
        if (flowerName != null && flowerDescription != null && flowerImageRes != -1) {
            // Si les données sont complètes, créer un objet `currentFlower`
            currentFlower = Flower(flowerName, flowerPrice, animationRes, flowerDescription, flowerImageRes)
        } else {
            return // Si les données sont incomplètes, retourner et ne pas continuer
        }

        // Mise à jour des éléments de l'interface avec les informations de la fleur
        flowerNameTextView.text = flowerName // Affichage du nom de la fleur
        flowerPriceTextView.text = "Prix: $flowerPrice Dhs" // Affichage du prix de la fleur
        flowerDescriptionTextView.text = flowerDescription // Affichage de la description de la fleur

        // Vérification de l'état de favori de la fleur actuelle dans la liste des favoris
        isFavorite = FlowerListActivity.favoriteList.contains(currentFlower)
        updateFavoriteButton() // Mise à jour de l'apparence du bouton favoris en fonction de l'état

        // Ajout d'un listener sur le bouton favoris pour gérer les clics
        favoriteButton.setOnClickListener {
            // Inversion de l'état de favori au clic
            isFavorite = !isFavorite
            updateFavoriteButton() // Mise à jour de l'apparence du bouton favoris
            handleFavoriteClick(currentFlower) // Gérer l'ajout ou le retrait des favoris
        }

        // Si une ressource d'animation est définie, l'afficher
        if (animationRes != -1) {
            try {
                flowerAnimationView.setAnimation(animationRes) // Définir l'animation à afficher
                flowerAnimationView.playAnimation() // Lancer l'animation
            } catch (e: IllegalStateException) {
                e.printStackTrace() // Gérer l'exception en cas d'erreur
            }
        }
    }

    // Méthode pour mettre à jour le texte et l'icône du bouton favori
    private fun updateFavoriteButton() {
        if (isFavorite) {
            // Si la fleur est en favori, afficher le texte et l'icône correspondants
            favoriteButton.text = "Retirer des favoris" // Texte du bouton si l'élément est en favori
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0) // Icône de favori actif
        } else {
            // Si la fleur n'est pas en favori, afficher le texte et l'icône correspondants
            favoriteButton.text = "Ajouter aux favoris" // Texte du bouton si l'élément n'est pas en favori
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fav, 0, 0, 0) // Icône de favori inactif
        }
    }

    // Méthode pour gérer l'ajout ou le retrait des favoris
    private fun handleFavoriteClick(flower: Flower) {
        if (isFavorite) {
            // Si la fleur est ajoutée aux favoris, l'ajouter à la liste des favoris
            FlowerListActivity.favoriteList.add(flower)
            // Affichage d'un message avec Snackbar pour indiquer l'ajout aux favoris
            Snackbar.make(favoriteButton, "${flower.name} ajouté aux favoris", Snackbar.LENGTH_LONG).show()
        } else {
            // Si la fleur est retirée des favoris, la retirer de la liste des favoris
            FlowerListActivity.favoriteList.remove(flower)
            // Affichage d'un message avec Snackbar pour indiquer le retrait des favoris
            Snackbar.make(favoriteButton, "${flower.name} retiré des favoris", Snackbar.LENGTH_LONG).show()
        }
    }
}
