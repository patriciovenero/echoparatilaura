package com.miempresa.proyectointegrador

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class teammanager : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teammanager)

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

        val listView = findViewById<ListView>(R.id.list_view)
        val names = listOf(
            getString(R.string.aaronname),
            getString(R.string.dantename),
            getString(R.string.andrename),
            getString(R.string.jaimename),
            getString(R.string.patricioname)
        )
        val roles = listOf(
            getString(R.string.aaronrol),
            getString(R.string.danterol),
            getString(R.string.andrerol),
            getString(R.string.jaimerol),
            getString(R.string.patriciorol)
        )

        val photos = listOf(
            R.drawable.photo_aaron,
            R.drawable.photo_dante,
            R.drawable.photo_andre,
            R.drawable.photo_jaime,
            R.drawable.photo_patricio
        )
        val correo = listOf(
            getString(R.string.aaroncorreo),
            getString(R.string.dantecorreo),
            getString(R.string.andrecorreo),
            getString(R.string.jaimecorreo),
            getString(R.string.patriciocorreo)
        )

        val github = listOf(
            getString(R.string.aarongithub),
            getString(R.string.dantegithub),
            getString(R.string.aarongithub),
            getString(R.string.jaimegithub),
            getString(R.string.patriciogithub)
        )

        val details = listOf(
            getString(R.string.aarondes),
            getString(R.string.dantedes),
            getString(R.string.andredes),
            getString(R.string.jaimedes),
            getString(R.string.patriciodes)
        )


        val teamMembers = mutableListOf<TeamMember>()
        for (i in names.indices) {
            val member = TeamMember(names[i], roles[i], photos[i], correo[i], github[i], details[i])
            teamMembers.add(member)
        }

        val adapter = AboutListAdapter(this, R.layout.about_list_item, teamMembers)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedMember = teamMembers[position]
            // Aquí puedes pasar el miembro del equipo seleccionado (selectedMember) a la actividad de detalles
            val intent = Intent(this, detalles_aboutus::class.java)
            intent.putExtra("memberName", selectedMember.name)
            intent.putExtra("memberRole", selectedMember.role)
            intent.putExtra("memberPhoto", selectedMember.photoResId)
            intent.putExtra("memberEmail", selectedMember.correo)
            intent.putExtra("memberGitHub", selectedMember.github)
            intent.putExtra("memberDetails", selectedMember.detalles)
            startActivity(intent)
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