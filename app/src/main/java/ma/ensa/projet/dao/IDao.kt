package ma.ensa.projet.dao

import ma.ensa.projet.beans.Flower

interface IDao<T> {
    fun create(item: T)
    fun getAll(): List<T>
}
