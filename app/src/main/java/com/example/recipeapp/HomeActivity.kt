package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
        private lateinit var binding: ActivityHomeBinding
        private lateinit var rvAdaptor:PopularAdapter
        private lateinit var datalist:ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        binding.editTextText.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
        binding.salad.setOnClickListener {
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)
        }
        binding.mainDish.setOnClickListener {var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent) }
        binding.dessert.setOnClickListener { var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)}
        binding.drinks.setOnClickListener {var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent) }
    }

    private fun setUpRecyclerView() {
        datalist= ArrayList()
        binding.rvPopular.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        var db=Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject=db.Dao()
        var recipe=daoObject.getAll()
        for(i in recipe!!.indices){
            if(recipe[i]!!.category.contains("Popular")){
                datalist.add(recipe[i]!!)
            }
            rvAdaptor=PopularAdapter(datalist, context = this)
            binding.rvPopular.adapter=rvAdaptor
        }
    }
}