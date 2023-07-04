package com.miempresa.proyectointegrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AboutListAdapter(
    context: Context,
    resource: Int,
    private val teamMembers: List<TeamMember>
) : ArrayAdapter<TeamMember>(context, resource, teamMembers) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.about_list_item, parent, false)
        }

        val member = teamMembers[position]

        val photoImageView = listItemView?.findViewById<ImageView>(R.id.foto)
        photoImageView?.setImageResource(member.photoResId)

        val nameTextView = listItemView?.findViewById<TextView>(R.id.nombre)
        nameTextView?.text = member.name

        val roleTextView = listItemView?.findViewById<TextView>(R.id.rol)
        roleTextView?.text = member.role

        return listItemView!!
    }
}