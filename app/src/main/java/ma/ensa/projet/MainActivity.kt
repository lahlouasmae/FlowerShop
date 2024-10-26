package ma.ensa.projet

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import android.content.Intent
import com.example.lottie.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        val recipesButton = findViewById<Button>(R.id.recipesButton)

        // Play animation on load
        lottieAnimationView.playAnimation()

        // Handle button click

        recipesButton.setOnClickListener {
            val intent = Intent(this, FlowerListActivity::class.java)
            startActivity(intent)
        }

    }

}
