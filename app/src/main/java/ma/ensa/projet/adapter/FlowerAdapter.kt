package ma.ensa.projet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.lottie.R
import ma.ensa.projet.beans.Flower

// Adaptateur pour gérer l'affichage des éléments "Flower" dans un RecyclerView
class FlowerAdapter(
    private var flowerList: List<Flower>, // Liste des fleurs à afficher dans le RecyclerView (modifiable pour mettre à jour la liste)
    private val onItemClicked: (Flower) -> Unit, // Fonction de rappel appelée lors du clic sur un élément
    private val showLottie: Boolean // Indicateur pour décider d'afficher l'animation Lottie ou non
) : RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder>() {

    // Classe interne pour représenter les vues d'un seul élément "Flower"
    class FlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flowerName: TextView = itemView.findViewById(R.id.flowerName) // Affiche le nom de la fleur
        val flowerPrice: TextView = itemView.findViewById(R.id.flowerPrice) // Affiche le prix de la fleur
        val flowerImageView: ImageView = itemView.findViewById(R.id.flowerImageView) // Image de la fleur
        val lottieAnimationView: LottieAnimationView = itemView.findViewById(R.id.flowerAnimationView) // Vue pour l'animation Lottie
    }

    // Crée et "gonfle" le layout XML pour un élément de la liste
    // LayoutInflater prend le fichier item_flower.xml et le transforme en objet View que l'on peut afficher dans le RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_flower, parent, false)
        return FlowerViewHolder(itemView)
    }

    // Remplit les données dans les vues d'un élément de la liste à une position donnée
    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        val currentFlower = flowerList[position] // Obtenir la fleur à la position donnée
        holder.flowerName.text = currentFlower.name // Définir le nom de la fleur
        holder.flowerPrice.text = "Prix pour un bouquet: ${currentFlower.price} Dhs" // Afficher le prix de la fleur
        holder.flowerImageView.setImageResource(currentFlower.imageRes) // Charger l'image de la fleur

        // Vérifier si l'animation Lottie doit être affichée
        if (showLottie) {
            holder.lottieAnimationView.visibility = View.VISIBLE // Rendre l'animation visible
            holder.lottieAnimationView.setAnimation(R.raw.gift) // Définir l'animation à jouer
            holder.lottieAnimationView.playAnimation() // Lancer l'animation
        } else {
            holder.lottieAnimationView.visibility = View.GONE // Masquer l'animation si `showLottie` est false
        }

        // Définir un clic sur l'élément qui déclenche la fonction `onItemClicked` avec l'objet `currentFlower`
        holder.itemView.setOnClickListener {
            onItemClicked(currentFlower)
        }
    }

    // Retourne le nombre total d'éléments dans la liste
    override fun getItemCount() = flowerList.size

    // Méthode pour mettre à jour la liste des fleurs et notifier le RecyclerView de la mise à jour
    fun updateFlowerList(newFlowerList: List<Flower>) {
        flowerList = newFlowerList // Mettre à jour la liste interne
        notifyDataSetChanged() // Notifier l'adaptateur que les données ont changé
    }
}
