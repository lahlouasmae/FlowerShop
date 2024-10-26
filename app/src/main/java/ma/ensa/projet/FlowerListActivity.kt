package ma.ensa.projet

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

class FlowerListActivity : AppCompatActivity() {

    private lateinit var flowerAdapter: FlowerAdapter
    private lateinit var flowerList: ArrayList<Flower>
    private lateinit var filteredFlowerList: ArrayList<Flower>
    private lateinit var itemCountTextView: TextView

    companion object {
        val favoriteList: ArrayList<Flower> = ArrayList() // Rend la liste favorite accessible
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_list)

        val flowerRecyclerView = findViewById<RecyclerView>(R.id.flowerRecyclerView)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        val favoriteButton: Button = findViewById(R.id.favoriteIcon) // Utiliser le bon ID
        itemCountTextView = findViewById(R.id.itemCountTextView)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Liste des fleurs
        flowerList = arrayListOf(
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

        // Copier la liste originale pour le filtrage
        filteredFlowerList = ArrayList(flowerList)

        // Mise à jour du TextView avec le nombre total de fleurs
        itemCountTextView.text = "Total des fleurs : ${filteredFlowerList.size}"

        // Configuration de l'adaptateur et du RecyclerView
        flowerAdapter = FlowerAdapter(filteredFlowerList, onItemClicked = { flower ->
            onFlowerClicked(flower)
        }, showLottie = true)

        flowerRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        flowerRecyclerView.adapter = flowerAdapter

        // Gérer le clic sur le bouton pour voir les favoris
        favoriteButton.setOnClickListener {
            showFavorites()
        }
    }

    private fun showFavorites() {
        val favoritesIntent = Intent(this, FavoriteListActivity::class.java)
        favoritesIntent.putParcelableArrayListExtra("favorite_list", favoriteList)
        startActivity(favoritesIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.apply {
            queryHint = "Rechercher une fleur..."
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterFlowers(newText)
                    return true
                }
            })

            setOnCloseListener {
                // Réinitialiser la liste lorsqu'on ferme le SearchView
                filterFlowers("")
                false
            }
        }
        return true
    }

    private fun filterFlowers(query: String?) {
        filteredFlowerList.clear()

        if (query.isNullOrEmpty()) {
            filteredFlowerList.addAll(flowerList)
        } else {
            val lowercaseQuery = query.lowercase(Locale.getDefault())
            filteredFlowerList.addAll(flowerList.filter {
                it.name.lowercase(Locale.getDefault()).contains(lowercaseQuery)
            })
        }

        // Notifier l'adaptateur des changements
        flowerAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                handleShareAction()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Fonction qui est appelée quand une fleur est cliquée
    private fun onFlowerClicked(flower: Flower) {
        val intent = Intent(this, LottieDisplayActivity::class.java).apply {
            putExtra("flower_name", flower.name)
            putExtra("flower_price", flower.price)
            putExtra("flower_description", flower.description)
            putExtra("animation_res", flower.animationRes)
        }
        startActivity(intent)
    }

    // Gestion de l'action de partage
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
