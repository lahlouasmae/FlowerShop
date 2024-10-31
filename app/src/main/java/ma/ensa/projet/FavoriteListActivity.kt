package ma.ensa.projet

import android.content.Intent  // Importation de la classe Intent pour le passage de données entre les activités
import android.os.Bundle     // Importation de la classe Bundle pour le stockage et le passage de données dans l'Intent
import android.util.Log      // Importation pour l'utilisation des logs de débogage
import android.widget.TextView   // Importation pour la manipulation des TextViews dans l'interface utilisateur
import androidx.appcompat.app.AppCompatActivity  // Importation de la classe de base pour les activités
import androidx.recyclerview.widget.LinearLayoutManager   // Importation de la classe LinearLayoutManager pour gérer la disposition des éléments dans RecyclerView
import androidx.recyclerview.widget.RecyclerView   // Importation de la classe RecyclerView pour afficher des listes de données sous forme de défilement
import com.example.lottie.R   // Importation des ressources, notamment les layouts et les identifiants de vues
import ma.ensa.projet.beans.Flower  // Importation de la classe Flower qui représente un objet "fleur" (les données à afficher dans la liste)

class FavoriteListActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: FlowerAdapter  // Déclaration d'un adaptateur pour le RecyclerView, qui gère l'affichage des fleurs

    // Méthode appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  // Appel de la méthode parente pour l'initialisation de l'activité
        setContentView(R.layout.activity_favorite_list)  // Définition de la vue principale de l'activité à partir du fichier XML (layout)

        // Initialisation des vues à partir de l'interface utilisateur
        val favoriteRecyclerView = findViewById<RecyclerView>(R.id.favoriteRecyclerView)  // RecyclerView pour afficher la liste des favoris
        val numFavoritesTextView = findViewById<TextView>(R.id.numFavoritesTextView)  // TextView pour afficher le nombre de favoris

        // Configuration de l'adaptateur qui gère la liste des favoris
        // L'adaptateur est initialisé avec une liste de fleurs favorites et une action à effectuer lors d'un clic sur un élément
        favoriteAdapter = FlowerAdapter(FlowerListActivity.favoriteList, { flower ->
            // Action à effectuer lors du clic sur un élément : afficher les détails de la fleur
            // Création d'un Intent pour passer à l'activité LottieDisplayActivity
            val intent = Intent(this, LottieDisplayActivity::class.java).apply {
                // Utilisation de putExtra pour envoyer les données de la fleur sous forme de paires clé-valeur
                putExtra("flower_name", flower.name)  // Nom de la fleur
                putExtra("flower_price", flower.price)  // Prix de la fleur
                putExtra("flower_description", flower.description)  // Description de la fleur
                putExtra("animation_res", flower.animationRes)  // Ressource d'animation associée à la fleur
                putExtra("flower_image", flower.imageRes)  // Ressource d'image associée à la fleur
            }
            // Démarre l'activité cible (LottieDisplayActivity) avec les données passées dans l'Intent
            startActivity(intent)
        }, showLottie = false)  // L'argument showLottie est fixé à false

        // Configuration du RecyclerView
        favoriteRecyclerView.layoutManager = LinearLayoutManager(this)  // Utilisation d'un LinearLayoutManager pour une disposition verticale de la liste
        favoriteRecyclerView.adapter = favoriteAdapter  // Assignation de l'adaptateur au RecyclerView pour gérer les éléments à afficher

        // Mise à jour du TextView pour afficher le nombre de fleurs favorites dans la liste
        numFavoritesTextView.text = "Nombre de fleurs préférées : ${FlowerListActivity.favoriteList.size}"
    }

    // Méthode appelée lorsque l'activité reprend après avoir été mise en pause ou stoppée
    override fun onResume() {
        super.onResume()  // Appel de la méthode parente pour restaurer l'état de l'activité
        // Lorsque l'activité reprend, on met à jour la liste des favoris dans l'adaptateur
        favoriteAdapter.updateFlowerList(FlowerListActivity.favoriteList)

        // Mise à jour du TextView pour refléter le nombre actuel de favoris
        val numFavoritesTextView = findViewById<TextView>(R.id.numFavoritesTextView)
        numFavoritesTextView.text = "Nombre de fleurs préférées : ${FlowerListActivity.favoriteList.size}"
    }
}
