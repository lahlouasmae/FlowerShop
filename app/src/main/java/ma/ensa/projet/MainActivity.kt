package ma.ensa.projet

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView  // gerer les animations lottie
import android.content.Intent
import com.example.lottie.R

// Activité principale de l'application, affichant une animation et un bouton pour naviguer vers une nouvelle activité.
class MainActivity : AppCompatActivity() {

    // Méthode appelée lors de la création de l'activité.
    override fun onCreate(savedInstanceState: Bundle?) { // sauvegarde l'etat de l'app
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Lie l'interface utilisateur à `activity_main.xml`.

        // Référence à la vue d'animation Lottie dans l'interface.
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)

        // Référence au bouton de navigation dans l'interface.
        val recipesButton = findViewById<Button>(R.id.recipesButton)

        // Lance automatiquement l'animation Lottie lors du chargement de l'activité.
        lottieAnimationView.playAnimation()

        // Définit une action pour le clic sur le bouton `recipesButton`.
        recipesButton.setOnClickListener {
            // Crée un `Intent` pour démarrer `FlowerListActivity`.
            val intent = Intent(this, FlowerListActivity::class.java)
            startActivity(intent) // Démarre `FlowerListActivity`.
        }
    }
}
