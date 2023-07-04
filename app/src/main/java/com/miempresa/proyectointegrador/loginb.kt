
package com.miempresa.proyectointegrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_loginb.*
import org.json.JSONArray


class loginb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginb)

        loginButton.setOnClickListener {
            val email = username.text.toString()
            val clave = password.text.toString()
            val queue = Volley.newRequestQueue(this)
            val urlAPI = getString(R.string.urlapi)

            val url = "$urlAPI/empresa"
            val request = object : StringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->
                    val jsonArray = JSONArray(response)

                    // Verificar las credenciales de inicio de sesión
                    var loggedIn = false
                    var id = ""
                    for (i in 0 until jsonArray.length()) {
                        val cuenta = jsonArray.getJSONObject(i)
                        val username = cuenta.getString("correo")
                        val password = cuenta.getString("clave")
                        if (email == username && clave == password) {
                            loggedIn = true
                            id = cuenta.getString("id")
                            break
                        }
                    }

                    // Mostrar el mensaje según el resultado del inicio de sesión
                    if (loggedIn) {
                        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                        // Redirigir a la actividad correspondiente y pasar el ID de la empresa
                        val intent = Intent(this, welcome::class.java)
                        intent.putExtra("empresaId", id)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error en email o contraseña", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error en la conexión: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            ) {}

            // Agregar la solicitud a la cola de solicitudes de Volley
            queue.add(request)
        }
    }
}



