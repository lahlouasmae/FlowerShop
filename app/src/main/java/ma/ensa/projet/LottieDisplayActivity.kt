// Déclaration de la classe LottieDisplayActivity qui hérite de AppCompatActivity
package ma.ensa.projet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.lottie.R

// Classe qui affiche une animation Lottie pendant 8 secondes avant de naviguer vers une autre activité.
class LottieDisplayActivity : AppCompatActivity() {

    // Méthode appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie_display) // Définit le layout de l'activité

        // Initialisation de la vue d'animation Lottie
        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieAnimationView)

        // Chargement de l'animation Lottie à partir d'un fichier JSON (ici "wait")
        lottieAnimationView.setAnimation(R.raw.wait)  // Assurez-vous que "wait" est un fichier d'animation Lottie valide dans "res/raw"

        // Démarre l'animation immédiatement après le chargement
        lottieAnimationView.playAnimation()

        // Planifie une tâche qui s'exécutera après un délai de 8 secondes (8000 millisecondes)
        Handler(Looper.getMainLooper()).postDelayed({   //s'execute sur le thread principal(UIThread)
            lottieAnimationView.cancelAnimation()  // Annule l'animation après 8 secondes
            navigateToDetailActivity()  // Appelle la méthode pour naviguer vers l'activité de détails
        }, 8000) // 8000 millisecondes = 8 secondes d'animation
    }

    // Méthode pour naviguer vers l'activité de détails après avoir récupéré les informations passées via l'Intent
    private fun navigateToDetailActivity() {
        // Récupération des données passées par l'activité précédente via l'Intent
        val flowerName = intent.getStringExtra("flower_name") // Récupère le nom de la fleur
        val flowerPrice = intent.getDoubleExtra("flower_price", 0.0) // Récupère le prix de la fleur
        val flowerDescription = intent.getStringExtra("flower_description") // Récupère la description de la fleur
        val animationRes = intent.getIntExtra("animation_res", -1) // Récupère la ressource d'animation associée
        val flowerImage = intent.getIntExtra("flower_image", -1) // Récupère la ressource de l'image associée à la fleur

        // Création d'un nouvel Intent pour passer à l'activité de détails de la fleur
        val intent = Intent(this, FlowerDetailActivity::class.java).apply {
            // Ajout des données à l'Intent sous forme de paires clé-valeur
            putExtra("flower_name", flowerName)
            putExtra("flower_price", flowerPrice)
            putExtra("flower_description", flowerDescription)
            putExtra("flower_image", flowerImage)
            putExtra("animation_res", animationRes)
        }

        // Démarre l'activité de détails avec les informations de la fleur
        startActivity(intent)

        // Ferme l'activité actuelle (LottieDisplayActivity) pour revenir à la précédente
        finish()
    }
}
