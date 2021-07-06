package com.example.retrofit2corrutinas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class DogAdapter(val images:List<String>):RecyclerView.Adapter<DogViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout., parent, false,))
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }
}