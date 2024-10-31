package ma.ensa.projet.beans

import android.os.Parcel
import android.os.Parcelable

// Classe de données Flower, représentant une fleur avec plusieurs propriétés
data class Flower(
    val name: String,          // Nom de la fleur
    val price: Double,         // Prix de la fleur
    val animationRes: Int,     // Identifiant de ressource pour l'animation
    val description: String,   // Description de la fleur
    val imageRes: Int,         // Identifiant de ressource pour l'image

) : Parcelable { // Implémentation de l'interface Parcelable pour permettre la sérialisation
    // pour passer la classe ente les activités par intents
    // Constructeur secondaire prenant un Parcel pour recréer un objet Flower
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",   // Lecture de name
        parcel.readDouble(),         // Lecture de price
        parcel.readInt(),            // Lecture de animationRes
        parcel.readString() ?: "",   // Lecture de description
        parcel.readInt()             // Lecture de imageRes
    )

    // Sérialisation des données de l'objet Flower dans un Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)      // Écriture de name
        parcel.writeDouble(price)     // Écriture de price
        parcel.writeInt(animationRes) // Écriture de animationRes
        parcel.writeString(description) // Écriture de description
        parcel.writeInt(imageRes)     // Écriture de imageRes
    }

    // Décrit le contenu de l'objet, retourne 0 car aucun type spécial n'est utilisé
    override fun describeContents(): Int = 0   // on doit la redefinir pour que la classe ne soit pas abstract

    // Objet compagnon pour recréer des objets Flower à partir d'un Parcel
    companion object CREATOR : Parcelable.Creator<Flower> {
        // Méthode pour créer une instance de Flower depuis un Parcel
        override fun createFromParcel(parcel: Parcel): Flower {
            return Flower(parcel)
        }

        // Méthode pour créer un tableau d'objets Flower de taille spécifiée
        override fun newArray(size: Int): Array<Flower?> {
            return arrayOfNulls(size)
        }
    }

    // Redéfinition de equals pour comparer deux objets Flower basés sur name et description
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Flower) return false
        return name == other.name && description == other.description
    }

    // Redéfinition de hashCode pour générer un code basé sur name et description pour hashmap
    override fun hashCode(): Int {
        return name.hashCode() * 31 + description.hashCode()
    }
}
