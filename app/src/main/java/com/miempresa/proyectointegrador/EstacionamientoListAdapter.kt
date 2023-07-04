package com.miempresa.proyectointegrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class EstacionamientoListAdapter (
    context: Context,
    private val resource: Int,
    private val estacionamientoItems: List<EstacionamientoItem>,
    private val empresaItems: List<EmpresaItem>
) : ArrayAdapter<EstacionamientoItem>(context, resource, estacionamientoItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(resource, parent, false)

            viewHolder = ViewHolder()
            viewHolder.lugarTextView = itemView.findViewById(R.id.text_lugar)
            viewHolder.empresaTextView = itemView.findViewById(R.id.text_empresa)
            viewHolder.estadoTextView = itemView.findViewById(R.id.text_estado)

            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        val estacionamientoItem = estacionamientoItems[position]
        val empresaItem = empresaItems.find { it.id == estacionamientoItem.idEmpresa }

        viewHolder.lugarTextView.text = estacionamientoItem.lugar
        viewHolder.empresaTextView.text = empresaItem?.nombre ?: ""
        viewHolder.estadoTextView.text = if (estacionamientoItem.estado) "Ocupado" else "Desocupado"

        return itemView!!
    }

    private class ViewHolder {
        lateinit var lugarTextView: TextView
        lateinit var empresaTextView: TextView
        lateinit var estadoTextView: TextView
    }
    fun getEstacionamientosPorEmpresa(idEmpresa: Int): List<EstacionamientoItem> {
        return estacionamientoItems.filter { it.idEmpresa == idEmpresa }
    }

}