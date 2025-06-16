package com.example.retrofit2corrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit2corrutinas.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var  binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()
        binding.buttonRandomImage.setOnClickListener {
            val rawInput = binding.randomDog.text.toString()
            // 1. Eliminar espacios
            val sinEspacios = rawInput.replace(" ", "")
            // 2. Convertir: primera letra mayúscula
            val formateado = sinEspacios.lowercase()
            /* 2. Convertir: primera letra mayúscula, resto minúsculas
            val formateado = sinEspacios.lowercase().replaceFirstChar { it.uppercaseChar() }*/
            // 3. Usar el texto formateado
            searchRandomDog(formateado)
        }
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }

    //Instancia del objeto retrofit
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        val cliient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
        return cliient
    }

    //Corrutina
    private fun searchByName(query: String){
        //Crear la cortina
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    //Show recycleview
                    val images = puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                } else {
                    //show error
                    showError()
                }
            }
        }
    }
    private fun showError(){
        Toast.makeText(this, "Ha ocurroido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return  true
    }

    // Instancia del objeto retrofit
    private fun getRetrofitObject():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Linkear el objeto retrofit con la interfaz
    val call = getRetrofitObject().create(APIService::class.java)

    private fun searchRandomDog(query: String){
        // Crear la corrutina
        CoroutineScope(Dispatchers.IO).launch {
            val response = call.getRandomDog(query)
            runOnUiThread{
                if (response.isSuccessful) {
                    if (binding.tvError.visibility == View.VISIBLE) {
                        binding.tvError.visibility = View.GONE
                        binding.ivRandomDog.visibility = View.VISIBLE
                        val imageUrl = response.body()?.randomImage
                        Picasso.get()
                            .load(imageUrl)
                            .into(binding.ivRandomDog)
                    } else {// At the start of the APP
                        binding.ivRandomDog.visibility = View.VISIBLE
                        val imageUrl = response.body()?.randomImage
                        Picasso.get()
                            .load(imageUrl)
                            .into(binding.ivRandomDog)
                    }
                } else {
                    binding.ivRandomDog.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "Error: ${response.code()}"
                }
            }
        }
    }
}