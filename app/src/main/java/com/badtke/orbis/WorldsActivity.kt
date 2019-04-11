package com.badtke.orbis


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content_played.*
import java.io.IOException
import java.util.ArrayList

class WorldsActivity : AppCompatActivity() {


    private lateinit var rvHorizontalPicker: RecyclerView
    private lateinit var imageView_back: ImageView
    private lateinit var imageView_last: ImageView
    private lateinit var imageView_next: ImageView
    private lateinit var imageView_coins: ImageView
    private lateinit var textView_score: TextView

    private val data = (1..3).toList().map { it.toString() } as ArrayList<String>


    //---------- Serialisierung --------------

    internal var myData = Datenmodell.getInstance()

    override fun onResume() {
        super.onResume()
        try {
            myData.datenmodellDeserialisieren(applicationContext)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }



        rvHorizontalPicker.scrollToPosition(myData.currentWorld)
        setVisibles()
        textView_score.text = myData.coins.toString()

    }


    public override fun onPause() {
        super.onPause()
        try {
            myData.datenmodellSerialisieren(applicationContext)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    //---------- Starting -------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worlds)

        initializeVariables()
        try {
            initializeOnClickActions()
        } catch (ignored: Exception) {
        }

        setHorizontalPicker()
    }


    private fun initializeVariables() {
        imageView_back = findViewById(R.id.imageView_back)
        imageView_last = findViewById(R.id.imageView_last)
        imageView_next = findViewById(R.id.imageView_next)
        imageView_coins = findViewById(R.id.imageView_coins)
        rvHorizontalPicker = findViewById(R.id.rv_horizontal_picker)
        textView_score = findViewById(R.id.textView_score)


        Glide.with(this).load(R.drawable.comic_coin_edited).into(imageView_coins)
    }

    private fun initializeOnClickActions() {
        imageView_back.setOnClickListener { onBackPressed() }


        imageView_last.setOnClickListener {
            var newPos: Int = myData.currentWorld - 1

            if (data.size > 0) {
                if (newPos < 0) {
                    newPos = 0
                } else if (newPos >= data.size) {
                    newPos = data.size - 1
                }

                rvHorizontalPicker.smoothScrollToPosition(newPos)
            }
        }

        imageView_next.setOnClickListener {
            var newPos: Int = myData.currentWorld + 1

            if (data.size > 0) {
                if (newPos < 0) {
                    newPos = 0
                } else if (newPos >= data.size) {
                    newPos = data.size - 1
                }

                rvHorizontalPicker.smoothScrollToPosition(newPos)
            }
        }



    }

    private fun setHorizontalPicker() {

        // Setting the padding such that the items will appear in the middle of the screen
        //val padding: Int = ScreenUtils.getScreenWidth(this)/2 - ScreenUtils.dpToPx(this, 40)
        //val customPadding: Int =
        //rvHorizontalPicker.setPadding(padding, 0, padding, 0)

        // Setting layout manager
        rvHorizontalPicker.layoutManager = SliderLayoutManager(this).apply {
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {

                    myData.currentWorld = layoutPosition
                    setVisibles()
                }
            }
        }

        // Setting Adapter
        rvHorizontalPicker.adapter = SliderAdapter().apply {
            setData(data)
            callback = object : SliderAdapter.Callback {
                override fun onItemClicked(view: View) {
                    if (rvHorizontalPicker.getChildLayoutPosition(view) != myData.currentWorld) {
                        rvHorizontalPicker.smoothScrollToPosition(rvHorizontalPicker.getChildLayoutPosition(view))
                    } else {
                        myData.currentWorld = rvHorizontalPicker.getChildLayoutPosition(view)
                        val myIntent = Intent(view.context, PlayActivity::class.java)
                        startActivity(myIntent)
                        overridePendingTransition(R.anim.none, R.anim.none)
                    }

                }
            }
        }
    }

    private fun setVisibles() {
        if (myData.currentWorld <= 0){
            imageView_last.visibility = View.INVISIBLE

        } else imageView_last.visibility = View.VISIBLE

        if (myData.currentWorld >= data.size-1){
            imageView_next.visibility = View.INVISIBLE

        } else imageView_next.visibility = View.VISIBLE
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.none, R.anim.none)
    }

}
