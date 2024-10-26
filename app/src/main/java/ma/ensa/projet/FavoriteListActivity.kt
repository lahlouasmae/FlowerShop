package ma.ensa.projet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lottie.R

import ma.ensa.projet.beans.Flower

class FavoriteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)

        val favoriteList = intent.getParcelableArrayListExtra<Flower>("favorite_list") ?: arrayListOf()

        // Vérifiez les données pour chaque élément dans la liste des favoris
        for (flower in favoriteList) {
            Log.d("FavoriteListActivity", "Flower in favorites: ${flower.name}, ImageRes: ${flower.imageRes}")

        }

        val favoriteRecyclerView = findViewById<RecyclerView>(R.id.favoriteRecyclerView)

        val numFavoritesTextView = findViewById<TextView>(R.id.numFavoritesTextView)
        numFavoritesTextView.text = "Nombre de fleurs préférées : ${favoriteList.size}"
        // Configuration du RecyclerView avec les favoris
        favoriteRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        favoriteRecyclerView.adapter = FlowerAdapter(favoriteList, { flower ->
            // Action à faire lorsqu'un favori est cliqué (par exemple, afficher les détails)
            val intent = Intent(this, LottieDisplayActivity::class.java).apply {
                putExtra("flower_name", flower.name)
                putExtra("flower_price", flower.price)
                putExtra("flower_description", flower.description)
                putExtra("animation_res", flower.animationRes)
                putExtra("flower_image", flower.imageRes)  // Assurez-vous de transmettre l'image
            }
            Log.d("FavoriteListActivity", "Flower in favorites: ${flower.name}, ImageRes: ${flower.imageRes}")
            startActivity(intent)
        }, showLottie = false) // On ne montre pas les animations Lottie dans la liste des favoris

    }

}
