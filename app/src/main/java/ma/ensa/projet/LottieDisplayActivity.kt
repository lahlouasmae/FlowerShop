package ma.ensa.projet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.lottie.R

class LottieDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie_display)

        // Récupérer l'animation Lottie
        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieAnimationView)

        lottieAnimationView.setAnimation(R.raw.wait)  // Assurez-vous de choisir le bon fichier JSON
        lottieAnimationView.playAnimation()

        // Ajouter un délai pour arrêter l'animation et naviguer vers l'activité de détail
        Handler(Looper.getMainLooper()).postDelayed({
            lottieAnimationView.cancelAnimation()
            navigateToDetailActivity()
        }, 8000) // Afficher l'animation pendant 8 secondes
    }

    private fun navigateToDetailActivity() {
        val flowerName = intent.getStringExtra("flower_name")
        val flowerPrice = intent.getDoubleExtra("flower_price", 0.0)
        val flowerDescription = intent.getStringExtra("flower_description")
        val animationRes = intent.getIntExtra("animation_res", -1)
        val flowerImage = intent.getIntExtra("flower_image", -1) // Correction : méthode correcte est getIntExtra

        // Lancer l'activité des détails de la fleur
        val intent = Intent(this, FlowerDetailActivity::class.java).apply {
            putExtra("flower_name", flowerName)
            putExtra("flower_price", flowerPrice)
            putExtra("flower_description", flowerDescription)
            putExtra("flower_image", flowerImage)
            putExtra("animation_res", animationRes)
        }
        startActivity(intent)
        finish()
    }
}
