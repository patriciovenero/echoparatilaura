package com.miempresa.proyectointegrador

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONException

class Estacionamiento : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estacionamiento)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        listView = findViewById(R.id.list_view)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
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

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Realizar solicitud a la API utilizando Volley
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlapi)
        val urlEstacionamiento = "$urlAPI/estacionamiento"
        val urlEmpresa = "$urlAPI/empresa"

        val jsonArrayRequestEstacionamiento = JsonArrayRequest(
            Request.Method.GET, urlEstacionamiento, null,
            Response.Listener { response ->
                // Procesar la respuesta JSON de estacionamientos
                val estacionamientoItems = parseEstacionamientoItems(response)

                // Obtener la lista de empresas
                val jsonArrayRequestEmpresa = JsonArrayRequest(
                    Request.Method.GET, urlEmpresa, null,
                    Response.Listener { response ->
                        // Procesar la respuesta JSON de empresas
                        val empresaItems = parseEmpresaItems(response)

                        // Crear un adaptador personalizado y asignar la lista de elementos
                        val adapter = EstacionamientoListAdapter(this, R.layout.esta_list_item, estacionamientoItems, empresaItems)

                        // Asignar el adaptador al ListView
                        listView.adapter = adapter
                    },
                    Response.ErrorListener { error ->
                        // Manejar errores de la solicitud de empresas
                    }
                )

                // Agregar la solicitud de empresas a la cola de solicitudes
                requestQueue.add(jsonArrayRequestEmpresa)
            },
            Response.ErrorListener { error ->
                // Manejar errores de la solicitud de estacionamientos
            }
        )

        // Agregar la solicitud de estacionamientos a la cola de solicitudes
        requestQueue.add(jsonArrayRequestEstacionamiento)
    }

    private fun parseEstacionamientoItems(response: JSONArray): List<EstacionamientoItem> {
        val estacionamientoItems = mutableListOf<EstacionamientoItem>()

        try {
            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val lugar = jsonObject.getString("lugar")
                val estado = jsonObject.getBoolean("estado")
                val idEmpresa = jsonObject.getInt("id_empresa")

                val estacionamientoItem = EstacionamientoItem(id, lugar, estado, idEmpresa)
                estacionamientoItems.add(estacionamientoItem)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return estacionamientoItems
    }

    private fun parseEmpresaItems(response: JSONArray): List<EmpresaItem> {
        val empresaItems = mutableListOf<EmpresaItem>()

        try {
            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val nombre = jsonObject.getString("nombre")
                val correo = jsonObject.getString("correo")
                val capacidad = jsonObject.getString("capacidad")

                val empresaItem = EmpresaItem(id, nombre, correo, capacidad)
                empresaItems.add(empresaItem)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return empresaItems
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}