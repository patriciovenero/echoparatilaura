package com.miempresa.proyectointegrador

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_miperfil.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class miperfil : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var nombreEmpresaTextView: TextView
    private lateinit var correoEmpresaTextView: TextView
    private lateinit var capacidadTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_miperfil)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_ayuda -> {
                    val intent = Intent(this, sobrenosotros1::class.java)
                    startActivity(intent)
                }
                R.id.nav_team -> {
                    val intent = Intent(this, teammanager::class.java)
                    startActivity(intent)
                }
                R.id.nav_empresa -> {
                    val intent = Intent(this, Empresa::class.java)
                    startActivity(intent)
                }
                R.id.nav_estacionamiento -> {
                    val intent = Intent(this, Estacionamiento::class.java)
                    startActivity(intent)
                }
                R.id.nav_estado -> {
                    val intent = Intent(this, Estado::class.java)
                    startActivity(intent)
                }
                R.id.nav_miperfil -> {
                    val intent = Intent(this, miperfil::class.java)
                    startActivity(intent)
                }
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    val intent = Intent(this, loginb::class.java)
                    Toast.makeText(this, "Cierre de secion correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }
            true
        }

        nombreEmpresaTextView = findViewById(R.id.nombreEmpresa)
        correoEmpresaTextView = findViewById(R.id.correoEmpresa)
        capacidadTextView = findViewById(R.id.capacidad)

        // Obtener información de la empresa desde el intent de inicio de sesión
        val empresaId = intent.getStringExtra("empresaId")
        if (empresaId != null) {
            // Realizar la solicitud HTTP para obtener la información de la empresa por su ID
            val urlAPI = getString(R.string.urlapi)
            val url = "$urlAPI/empresa/$empresaId"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        val empresa = response.getJSONObject("empresa")
                        val nombreEmpresa = empresa.getString("nombre")
                        val correoEmpresa = empresa.getString("correo")
                        val capacidad = empresa.getInt("capacidad")

                        // Actualizar los TextView con la información de la empresa
                        nombreEmpresaTextView.text = nombreEmpresa
                        correoEmpresaTextView.text = correoEmpresa
                        capacidadTextView.text = capacidad.toString()
                    } catch (e: JSONException) {
                        Toast.makeText(this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error en la conexión: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )

            // Agregar la solicitud a la cola de solicitudes de Volley
            VolleySingleton.getInstance(this).addToRequestQueue(request)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}