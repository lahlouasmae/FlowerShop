package ma.ensa.projet.service

import ma.ensa.projet.beans.Flower
import ma.ensa.projet.dao.IDao

class FlowerService : IDao<Flower> {
    private val flowerList = mutableListOf<Flower>()

    override fun create(item: Flower) {
        flowerList.add(item)
    }

    override fun getAll(): List<Flower> {
        return flowerList
    }
}
