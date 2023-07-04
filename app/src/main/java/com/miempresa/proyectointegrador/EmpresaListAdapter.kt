package com.miempresa.proyectointegrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class EmpresaListAdapter(
    context: Context,
    private val resource: Int,
    private val empresaItems: List<EmpresaItem>
) : ArrayAdapter<EmpresaItem>(context, resource, empresaItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(resource, parent, false)

            viewHolder = ViewHolder()
            viewHolder.nombreTextView = itemView.findViewById(R.id.text_nombre)
            viewHolder.correoTextView = itemView.findViewById(R.id.text_correo)
            viewHolder.capacidadTextView = itemView.findViewById(R.id.text_capacidad)

            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        val empresaItem = empresaItems[position]

        viewHolder.nombreTextView.text = empresaItem.nombre
        viewHolder.correoTextView.text = empresaItem.correo
        viewHolder.capacidadTextView.text = empresaItem.capacidad

        return itemView!!
    }

    private class ViewHolder {
        lateinit var nombreTextView: TextView
        lateinit var correoTextView: TextView
        lateinit var capacidadTextView: TextView
    }
}