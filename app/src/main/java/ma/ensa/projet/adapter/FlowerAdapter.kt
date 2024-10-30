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

class FlowerAdapter(
    private var flowerList: List<Flower>, // Changement de `val` à `var` pour permettre la mise à jour de la liste
    private val onItemClicked: (Flower) -> Unit,
    private val showLottie: Boolean // Paramètre pour gérer l'affichage de Lottie
) : RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder>() {

    class FlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flowerName: TextView = itemView.findViewById(R.id.flowerName)
        val flowerPrice: TextView = itemView.findViewById(R.id.flowerPrice)
        val flowerImageView: ImageView = itemView.findViewById(R.id.flowerImageView)
        val lottieAnimationView: LottieAnimationView = itemView.findViewById(R.id.flowerAnimationView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_flower, parent, false)
        return FlowerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        val currentFlower = flowerList[position]
        holder.flowerName.text = currentFlower.name
        holder.flowerPrice.text = "Prix pour un bouquet: ${currentFlower.price} Dhs"

        holder.flowerImageView.setImageResource(currentFlower.imageRes)
        Log.d("Adapter", "Setting image resource ID: ${currentFlower.imageRes} for ${currentFlower.name}")

        // Affichez ou masquez l'animation Lottie en fonction de la variable `showLottie`
        if (showLottie) {
            holder.lottieAnimationView.visibility = View.VISIBLE
            holder.lottieAnimationView.setAnimation(R.raw.gift)
            holder.lottieAnimationView.playAnimation()
        } else {
            holder.lottieAnimationView.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onItemClicked(currentFlower)
        }
    }

    override fun getItemCount() = flowerList.size

    // Méthode pour mettre à jour la liste de fleurs et notifier l'adaptateur
    fun updateFlowerList(newFlowerList: List<Flower>) {
        flowerList = newFlowerList
        notifyDataSetChanged()
    }
}
