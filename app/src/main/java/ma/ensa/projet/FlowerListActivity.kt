// Déclaration du package principal de l'application
package ma.ensa.projet

// Importation des classes nécessaires
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lottie.R
import ma.ensa.projet.FavoriteListActivity
import ma.ensa.projet.LottieDisplayActivity
import ma.ensa.projet.beans.Flower
import java.util.Locale

// Déclaration de la classe `FlowerListActivity` qui hérite de `AppCompatActivity`
class FlowerListActivity : AppCompatActivity() {

    // Déclaration des propriétés de l'activité
    private lateinit var flowerAdapter: FlowerAdapter // Adaptateur pour gérer la liste des fleurs
    private lateinit var flowerList: ArrayList<Flower> // Liste complète des fleurs
    private lateinit var filteredFlowerList: ArrayList<Flower> // Liste filtrée des fleurs en fonction de la recherche
    private lateinit var itemCountTextView: TextView // Texte indiquant le nombre total de fleurs

    companion object {
        // Liste statique des fleurs favorites, accessible globalement
        val favoriteList: ArrayList<Flower> = ArrayList()
    }

    // Méthode appelée lors de la création de l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_list) // Liaison avec la vue XML

        // Références aux vues de l'interface
        val flowerRecyclerView = findViewById<RecyclerView>(R.id.flowerRecyclerView)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        val favoriteButton: Button = findViewById(R.id.favoriteIcon)
        itemCountTextView = findViewById(R.id.itemCountTextView)

        // Configuration de la barre d'outils sans le titre par défaut
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Initialisation de la liste des fleurs avec des données statiques
        flowerList = arrayListOf(
            // Liste de fleurs avec leurs attributs (nom, prix, animation, description, image)
            Flower("Rose Rouge", 300.0, R.raw.rose_animation, "Le symbole universel de l'amour et de la passion. Elle représente les émotions profondes et les sentiments romantiques.", R.drawable.rose_image),
            Flower("Tulip", 250.0, R.raw.tulip_animation, "une fleur élégante et colorée qui symbolise l'amour et l'affection. Originaire de Turquie et popularisée par les Pays-Bas, elle est souvent associée au renouveau printanier.", R.drawable.tulip_image),
            Flower("Sunflower", 200.0, R.raw.sunflower_animation, "Une fleur remarquable par sa grande taille et ses pétales jaune vif qui ressemblent à des rayons de soleil.", R.drawable.sunflower_image),
            Flower("Abutilon", 220.0, R.raw.abutilon, "Une plante arbustive ornementale appréciée pour ses fleurs délicates en forme de cloche.", R.drawable.abutilon),
            Flower("Acacia", 200.0, R.raw.acacia, "Un arbre qui se distingue par son feuillage fin et ses grappes de fleurs lumineuses.", R.drawable.acacia),
            Flower("Agapanthe", 350.0, R.raw.agapanthe, "Une plante vivace remarquable par ses grandes ombelles de fleurs qui s'épanouissent en été.", R.drawable.agapanthe),
            Flower("Jasmin", 280.0, R.raw.jasmin, "Un arbuste grimpant célèbre pour ses fleurs au parfum envoûtant et sucré.", R.drawable.jasmin),
            Flower("Marguerite", 150.0, R.raw.lamarguerite, "Une fleur très répandue, connue pour ses pétales blancs entourant un cœur jaune vif.", R.drawable.lamarguerite),
            Flower("Lavande", 210.0, R.raw.lavande, "Une fleur emblématique du sud de la France, reconnue pour sa couleur violette et son parfum apaisant.", R.drawable.lavande),
            Flower("Orchide", 600.0, R.raw.orchide, "Une fleur exotique connue pour sa beauté élégante et ses formes délicates", R.drawable.orchide),
            Flower("Rose Noire", 500.0, R.raw.rosenoire, "Une fleur mystérieuse et rare, souvent associée à l'élégance, la passion intense, et le mystère.", R.drawable.rosenoir)
        )

        // Copie de la liste complète pour le filtrage
        filteredFlowerList = ArrayList(flowerList)

        // Mise à jour du TextView avec le nombre total de fleurs
        itemCountTextView.text = "Total des fleurs : ${filteredFlowerList.size}"

        // Configuration de l'adaptateur et du RecyclerView
        flowerAdapter = FlowerAdapter(filteredFlowerList, onItemClicked = { flower ->
            onFlowerClicked(flower) // Gestion du clic sur une fleur
        }, showLottie = true) // Affichage des animations Lottie

        // Configuration du RecyclerView avec un LinearLayoutManager
        flowerRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        flowerRecyclerView.adapter = flowerAdapter

        // Gestion du clic sur le bouton favoris pour afficher la liste des favoris
        favoriteButton.setOnClickListener {
            showFavorites()
        }
    }

    // Méthode pour afficher les fleurs favorites
    private fun showFavorites() {
        val favoritesIntent = Intent(this, FavoriteListActivity::class.java)
        favoritesIntent.putParcelableArrayListExtra("favorite_list", favoriteList)
        startActivity(favoritesIntent)
    }

    // Création du menu d'options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        // Configuration de la barre de recherche
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        // Gestion des actions de recherche
        searchView?.apply {
            queryHint = "Rechercher une fleur..."
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterFlowers(newText) // Filtrage des fleurs en fonction du texte saisi
                    return true
                }
            })

            // Réinitialisation de la liste lorsqu'on ferme la recherche
            setOnCloseListener {
                filterFlowers("")
                false
            }
        }
        return true
    }

    // Méthode pour filtrer les fleurs en fonction de la recherche
    private fun filterFlowers(query: String?) {
        filteredFlowerList.clear()

        // Si la requête est vide, réafficher la liste complète
        if (query.isNullOrEmpty()) {
            filteredFlowerList.addAll(flowerList)
        } else {
            // Filtrer la liste selon le texte saisi, en ignorant la casse
            val lowercaseQuery = query.lowercase(Locale.getDefault())
            filteredFlowerList.addAll(flowerList.filter {
                it.name.lowercase(Locale.getDefault()).contains(lowercaseQuery)
            })
        }

        // Notifier l'adaptateur des changements de données
        flowerAdapter.notifyDataSetChanged()
    }

    // Gestion des éléments du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                handleShareAction() // Gestion de l'action de partage
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Méthode appelée lorsqu'une fleur est cliquée
    private fun onFlowerClicked(flower: Flower) {
        // Création d'un Intent pour démarrer une nouvelle activité affichant les détails de la fleur
        val intent = Intent(this, LottieDisplayActivity::class.java).apply {
            putExtra("flower_name", flower.name)
            putExtra("flower_price", flower.price)
            putExtra("flower_description", flower.description)
            putExtra("animation_res", flower.animationRes)
            putExtra("flower_image", flower.imageRes) // Passer l'ID de l'image
        }
        startActivity(intent)
    }

    // Méthode pour gérer l'action de partage
    private fun handleShareAction() {
        val shareText = "Découvrez notre magnifique collection de fleurs dans l'application My Flower App!"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Partager via"))
    }
}
