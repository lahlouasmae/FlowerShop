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

    private lateinit var favoriteAdapter: FlowerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)

        val favoriteRecyclerView = findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        val numFavoritesTextView = findViewById<TextView>(R.id.numFavoritesTextView)

        // Configuration de l'adaptateur avec la liste partagée des favoris
        favoriteAdapter = FlowerAdapter(FlowerListActivity.favoriteList, { flower ->
            // Action lorsqu'un favori est cliqué pour afficher les détails
            val intent = Intent(this, LottieDisplayActivity::class.java).apply {
                putExtra("flower_name", flower.name)
                putExtra("flower_price", flower.price)
                putExtra("flower_description", flower.description)
                putExtra("animation_res", flower.animationRes)
                putExtra("flower_image", flower.imageRes)
            }
            startActivity(intent)
        }, showLottie = false)

        // Configuration du RecyclerView
        favoriteRecyclerView.layoutManager = LinearLayoutManager(this)
        favoriteRecyclerView.adapter = favoriteAdapter

        // Mise à jour du nombre de favoris
        numFavoritesTextView.text = "Nombre de fleurs préférées : ${FlowerListActivity.favoriteList.size}"
    }

    override fun onResume() {
        super.onResume()
        // Mettre à jour la liste des favoris et l'adaptateur lorsqu'on revient à cette activité
        favoriteAdapter.updateFlowerList(FlowerListActivity.favoriteList)
        // Mettre à jour le nombre de favoris
        val numFavoritesTextView = findViewById<TextView>(R.id.numFavoritesTextView)
        numFavoritesTextView.text = "Nombre de fleurs préférées : ${FlowerListActivity.favoriteList.size}"
    }
}
