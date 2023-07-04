package com.miempresa.proyectointegrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_loginb.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        empresa.setOnClickListener {
            val intent = Intent(this, Empresa::class.java)
            startActivity(intent)
        }

        estacionamiento.setOnClickListener {
            val intent = Intent(this, Estacionamiento::class.java)
            startActivity(intent)
        }

        estado.setOnClickListener {
            val intent = Intent(this, Estado::class.java)
            startActivity(intent)
        }
        myprofile.setOnClickListener {
            val intent = Intent(this, miperfil::class.java)
            startActivity(intent)
        }
        help.setOnClickListener {
            val intent = Intent(this, sobrenosotros1::class.java)
            startActivity(intent)
        }
        sobrenosotros.setOnClickListener {
            val intent = Intent(this, teammanager::class.java)
            startActivity(intent)
        }
    }
}