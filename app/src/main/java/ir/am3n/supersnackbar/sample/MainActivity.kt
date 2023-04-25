package ir.am3n.supersnackbar.sample

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import ir.am3n.supersnackbar.SuperSnackbar

class MainActivity : ComponentActivity() {

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {
            SuperSnackbar.show(
                view = findViewById(R.id.activityMain),
                text = "Hello ${counter++}",
                duration = Snackbar.LENGTH_LONG,
                backgroundColor = Color.parseColor("#44efaa")
            )
        }

    }

}
