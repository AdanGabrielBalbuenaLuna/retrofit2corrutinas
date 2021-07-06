package com.example.retrofit2corrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofit2corrutinas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.temporal.TemporalQuery

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        T
    }

    //Instancia del objeto retrofit
    private fun getRetrofit():Retrofit{

        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breeds/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Corrutina
    private fun searchByName(query: String){
        //Crear la cortina
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            if(call.isSuccessful){
                //Show recycleview
            } else {
                //show error
            }
        }

    }
}