package ma.ensa.projet

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import android.widget.TextView
import com.example.lottie.R
import com.google.android.material.snackbar.Snackbar
import ma.ensa.projet.beans.Flower

class FlowerDetailActivity : AppCompatActivity() {
    private lateinit var favoriteButton: Button
    private var isFavorite = false
    private lateinit var currentFlower: Flower

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_detail)

        val flowerName = intent.getStringExtra("flower_name")
        val flowerPrice = intent.getDoubleExtra("flower_price", 0.0)
        val animationRes = intent.getIntExtra("animation_res", -1)
        val flowerDescription = intent.getStringExtra("flower_description")
        val flowerImageRes = intent.getIntExtra("flower_image", -1)  // Default to -1 if not found


        val flowerNameTextView = findViewById<TextView>(R.id.flowerNameTextView)
        val flowerPriceTextView = findViewById<TextView>(R.id.flowerPriceTextView)
        val flowerDescriptionTextView = findViewById<TextView>(R.id.flowerDescriptionTextView)
        val flowerAnimationView = findViewById<LottieAnimationView>(R.id.flowerAnimationView)
        favoriteButton = findViewById(R.id.favoriteButton)

        // Initialize currentFlower after ensuring flowerName is not null
        if (flowerName != null && flowerDescription != null && flowerImageRes != -1) {
            currentFlower = Flower(flowerName, flowerPrice, animationRes, flowerDescription, flowerImageRes)
        } else {
            return
        }


        flowerNameTextView.text = flowerName
        flowerPriceTextView.text = "Prix: $flowerPrice Dhs"
        flowerDescriptionTextView.text = flowerDescription

        // Check if current flower is already in favorites
        isFavorite = FlowerListActivity.favoriteList.contains(currentFlower)
        updateFavoriteButton()

        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteButton()
            handleFavoriteClick(currentFlower)
        }

        if (animationRes != -1) {
            try {
                flowerAnimationView.setAnimation(animationRes)
                flowerAnimationView.playAnimation()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            favoriteButton.text = "Retirer des favoris"
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0)
        } else {
            favoriteButton.text = "Ajouter aux favoris"
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fav, 0, 0, 0)
        }
    }

    private fun handleFavoriteClick(flower: Flower) {
        if (isFavorite) {
            // Ajouter la fleur aux favoris
            FlowerListActivity.favoriteList.add(flower)
            Snackbar.make(favoriteButton, "${flower.name} ajouté aux favoris", Snackbar.LENGTH_LONG).show()
        } else {
            // Retirer la fleur des favoris
            FlowerListActivity.favoriteList.remove(flower)
            Snackbar.make(favoriteButton, "${flower.name} retiré des favoris", Snackbar.LENGTH_LONG).show()
        }
    }

}
