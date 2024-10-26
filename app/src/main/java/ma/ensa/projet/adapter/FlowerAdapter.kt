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
    private val flowerList: List<Flower>,
    private val onItemClicked: (Flower) -> Unit,
    private val showLottie: Boolean // Ajout du paramètre pour gérer l'affichage de Lottie
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

            holder.lottieAnimationView.visibility = View.VISIBLE
            holder.lottieAnimationView.setAnimation(R.raw.gift)


        holder.itemView.setOnClickListener {
            onItemClicked(currentFlower)
        }
    }

    override fun getItemCount() = flowerList.size
}
