package se.christoferbodin.veganresan

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.appbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true
    }
}
