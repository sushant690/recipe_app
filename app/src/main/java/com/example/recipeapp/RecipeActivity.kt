package com.example.recipeapp

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecipeBinding
    var imgCrop=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this).load(intent.getStringExtra("img")).into(binding.imageBg)
        binding.tittle.text=intent.getStringExtra("tittle")
        binding.stepsTxt.text=intent.getStringExtra("des")
        var ing=intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        binding.time.text=ing?.get(0)
        for(i in 1 until ing!!.size){
        binding.textView19.text=
            """${binding.textView19.text} 🟠 ${ing[i]}
           
            """.trimIndent()
        }
        binding.steps.background=null
        binding.steps.setOnClickListener {
            binding.steps.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ing.setTextColor(getColor(R.color.black))
            binding.ing.background=null
            binding.stepsScr.visibility=View.VISIBLE
            binding.ingScr.visibility=View.GONE
        }
        binding.ing.setOnClickListener {
            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.black))
            binding.ing.setTextColor(getColor(R.color.white))
            binding.steps.background=null
            binding.ingScr.visibility=View.VISIBLE
            binding.stepsScr.visibility=View.GONE
        }

        binding.imageBg.setOnClickListener {
            if(imgCrop){
                binding.imageBg.scaleType=ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.imageBg)
                binding.imageView9.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                binding.imageView7.visibility= View.GONE
                imgCrop=!imgCrop

            }
            else{
                binding.imageBg.scaleType=ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.imageBg)
                binding.imageView9.setColorFilter(null)
                binding.imageView7.visibility= View.GONE
                imgCrop=!imgCrop
            }
        }
        binding.backBtnn.setOnClickListener {
            finish()
        }
    }
}