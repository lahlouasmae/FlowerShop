package ma.ensa.projet.service

import ma.ensa.projet.beans.Flower

class FlowerService {
    private val flowerList = mutableListOf<Flower>()

    fun create(flower: Flower) {
        flowerList.add(flower)
    }

    fun getAll(): List<Flower> {
        return flowerList
    }
}
