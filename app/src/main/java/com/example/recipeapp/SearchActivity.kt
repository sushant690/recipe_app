package com.example.recipeapp

import android.annotation.SuppressLint
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var rvAdaptor:SearchAdapter
    private lateinit var datalist:ArrayList<Recipe>
    private lateinit var recipe:List<Recipe?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.search.requestFocus()
        var db= Room.databaseBuilder(this@SearchActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject=db.Dao()
        recipe = daoObject.getAll()!!

        setUpRecyclerView()
        binding.backBtn.setOnClickListener{
            finish()
        }
        binding.search.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString()!=""){
                    filterData(p0.toString() )
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    private fun filterData(filterText: String) {
         var filterData=ArrayList<Recipe>()
        for(i in recipe.indices){
       if(recipe[i]!!.tittle.lowercase().contains(filterText.lowercase())){
           filterData.add(recipe[i]!!)
       }
            rvAdaptor.filterList(filterList = filterData)
        }
    }

    private fun setUpRecyclerView() {

        datalist= ArrayList()
        binding.rvSearch.layoutManager= LinearLayoutManager(this)


        for(i in recipe!!.indices){
            if(recipe[i]!!.category.contains("Popular")){
                datalist.add(recipe[i]!!)
            }
            rvAdaptor= SearchAdapter(datalist, context = this)
            binding.rvSearch.adapter=rvAdaptor
        }
    }
}