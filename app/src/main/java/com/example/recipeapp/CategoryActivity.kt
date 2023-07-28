package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivityCategoryBinding
import com.example.recipeapp.databinding.ActivityHomeBinding

class CategoryActivity : AppCompatActivity() {
   private val binding by lazy {
       ActivityCategoryBinding.inflate(layoutInflater)
   }
    private lateinit var rvAdaptor:CategoryAdapter
    private lateinit var datalist:ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title=intent.getStringExtra("TITTLE")
        setUpRecyclerView()
        binding.goBack.setOnClickListener {
            finish()
        }
    }
    private fun setUpRecyclerView() {
        datalist= ArrayList()
        binding.rvCategory.layoutManager=
            LinearLayoutManager(this)
        var db= Room.databaseBuilder(this@CategoryActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject=db.Dao()
        var recipe=daoObject.getAll()
        for(i in recipe!!.indices){
            if(recipe[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)){
                datalist.add(recipe[i]!!)
            }
            rvAdaptor=CategoryAdapter(datalist, context = this)
            binding.rvCategory.adapter=rvAdaptor
        }
    }
}