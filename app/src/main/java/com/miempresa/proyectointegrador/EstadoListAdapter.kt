package com.miempresa.proyectointegrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.miempresa.proyectointegrador.EstadoItem
import com.miempresa.proyectointegrador.EstacionamientoItem

class EstadoListAdapter (
    context: Context,
    private val resource: Int,
    private val estadoItems: List<EstadoItem>,
    private val estacionamientoItems: List<EstacionamientoItem>
) : ArrayAdapter<EstadoItem>(context, resource, estadoItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent.context).inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val estadoItem = estadoItems[position]
        val estacionamientoItem = estacionamientoItems.find { it.id == estadoItem.idEstacionamiento }

        viewHolder.lugarTextView.text = estacionamientoItem?.lugar ?: ""
        viewHolder.horaIngresoTextView.text = estadoItem.horaIngreso
        viewHolder.horaSalidaTextView.text = estadoItem.horaSalida

        return view
    }

    private class ViewHolder(view: View) {
        val lugarTextView: TextView = view.findViewById(R.id.text_lugar_estacionamiento)
        val horaIngresoTextView: TextView = view.findViewById(R.id.text_horaingre)
        val horaSalidaTextView: TextView = view.findViewById(R.id.text_horasa)
    }
}