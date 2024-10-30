// Définition du package de l'activité
package ma.ensa.projet

// Importation des classes nécessaires
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import android.widget.TextView
import com.example.lottie.R
import com.google.android.material.snackbar.Snackbar
import ma.ensa.projet.beans.Flower

// Déclaration de la classe `FlowerDetailActivity` qui hérite de `AppCompatActivity`
class FlowerDetailActivity : AppCompatActivity() {
    // Déclaration des propriétés de l'activité
    private lateinit var favoriteButton: Button // Bouton pour ajouter ou retirer des favoris
    private var isFavorite = false // Booléen indiquant l'état de favori
    private lateinit var currentFlower: Flower // Objet représentant la fleur actuelle

    // Méthode appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_detail) // Liaison avec la vue XML associée

        // Récupération des données envoyées par l'intent (nom, prix, ressource d'animation, description, image)
        val flowerName = intent.getStringExtra("flower_name")
        val flowerPrice = intent.getDoubleExtra("flower_price", 0.0)
        val animationRes = intent.getIntExtra("animation_res", -1)
        val flowerDescription = intent.getStringExtra("flower_description")
        val flowerImageRes = intent.getIntExtra("flower_image", -1)  // Valeur par défaut si l'image n'est pas trouvée

        // Référence aux TextView et LottieAnimationView dans la vue
        val flowerNameTextView = findViewById<TextView>(R.id.flowerNameTextView)
        val flowerPriceTextView = findViewById<TextView>(R.id.flowerPriceTextView)
        val flowerDescriptionTextView = findViewById<TextView>(R.id.flowerDescriptionTextView)
        val flowerAnimationView = findViewById<LottieAnimationView>(R.id.flowerAnimationView)
        favoriteButton = findViewById(R.id.favoriteButton)

        // Initialisation de l'objet `currentFlower` si les données nécessaires sont disponibles
        if (flowerName != null && flowerDescription != null && flowerImageRes != -1) {
            currentFlower = Flower(flowerName, flowerPrice, animationRes, flowerDescription, flowerImageRes)
        } else {
            return // Sort de la méthode si les données sont incomplètes
        }

        // Mise à jour des éléments de l'interface avec les informations de la fleur
        flowerNameTextView.text = flowerName
        flowerPriceTextView.text = "Prix: $flowerPrice Dhs"
        flowerDescriptionTextView.text = flowerDescription

        // Vérification de l'état de favori de la fleur actuelle
        isFavorite = FlowerListActivity.favoriteList.contains(currentFlower)
        updateFavoriteButton() // Mise à jour de l'apparence du bouton favoris

        // Ajout d'un listener sur le bouton favoris pour gérer les clics
        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite // Inversion de l'état de favori
            updateFavoriteButton() // Mise à jour de l'apparence du bouton
            handleFavoriteClick(currentFlower) // Appel à la méthode pour gérer l'ajout ou le retrait des favoris
        }

        // Si une ressource d'animation est définie, l'afficher
        if (animationRes != -1) {
            try {
                flowerAnimationView.setAnimation(animationRes) // Définir l'animation
                flowerAnimationView.playAnimation() // Démarrer l'animation
            } catch (e: IllegalStateException) {
                e.printStackTrace() // Gérer l'exception en cas d'erreur
            }
        }
    }

    // Méthode pour mettre à jour le texte et l'icône du bouton favori
    private fun updateFavoriteButton() {
        if (isFavorite) {
            favoriteButton.text = "Retirer des favoris" // Texte du bouton si l'élément est en favori
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0) // Icône de favori actif
        } else {
            favoriteButton.text = "Ajouter aux favoris" // Texte du bouton si l'élément n'est pas en favori
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fav, 0, 0, 0) // Icône de favori inactif
        }
    }

    // Méthode pour gérer l'ajout ou le retrait des favoris
    private fun handleFavoriteClick(flower: Flower) {
        if (isFavorite) {
            // Ajouter la fleur à la liste des favoris
            FlowerListActivity.favoriteList.add(flower)
            Snackbar.make(favoriteButton, "${flower.name} ajouté aux favoris", Snackbar.LENGTH_LONG).show()
        } else {
            // Retirer la fleur de la liste des favoris
            FlowerListActivity.favoriteList.remove(flower)
            Snackbar.make(favoriteButton, "${flower.name} retiré des favoris", Snackbar.LENGTH_LONG).show()
        }
    }
}
