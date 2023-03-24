package mx.itson.edu.practica9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        var tvNombre = findViewById(R.id.tvNombre) as TextView
        var tvEmail = findViewById(R.id.tvEmail) as TextView
        val btnLogout = findViewById(R.id.btnCerrar) as Button

        val bundle = intent.extras

        if(bundle != null){
            val name = bundle.getString("name")
            val email = bundle.getString("email")

            tvNombre.setText(name)
            tvEmail.setText(email)
        }

        btnLogout.setOnClickListener{
            finish()
        }
    }
}