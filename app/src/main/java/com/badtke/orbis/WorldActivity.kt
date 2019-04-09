package com.badtke.orbis


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content_played.*
import java.util.ArrayList

class WorldsActivity : AppCompatActivity() {


    private var currentPosition: Int = 0

    private lateinit var rvHorizontalPicker: RecyclerView
    private lateinit var imageView_back: ImageView
    private lateinit var imageView_last: ImageView
    private lateinit var imageView_next: ImageView
    private lateinit var imageView_coins: ImageView

    private val data = (1..3).toList().map { it.toString() } as ArrayList<String>

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


        imageView_last.visibility = View.INVISIBLE

        Glide.with(this).load(R.drawable.comic_coin_edited).into(imageView_coins)
    }

    private fun initializeOnClickActions() {
        imageView_back.setOnClickListener { onBackPressed() }


        imageView_last.setOnClickListener {
            var newPos: Int = currentPosition - 1

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
            var newPos: Int = currentPosition + 1

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

                    currentPosition = layoutPosition

                    if (currentPosition <= 0){
                        imageView_last.visibility = View.INVISIBLE
                    } else imageView_last.visibility = View.VISIBLE
                    if (currentPosition >= data.size-1){
                        imageView_next.visibility = View.INVISIBLE
                    } else imageView_next.visibility = View.VISIBLE
                }
            }
        }

        // Setting Adapter
        rvHorizontalPicker.adapter = SliderAdapter().apply {
            setData(data)
            callback = object : SliderAdapter.Callback {
                override fun onItemClicked(view: View) {
                    if (rvHorizontalPicker.getChildLayoutPosition(view) != currentPosition) {
                        rvHorizontalPicker.smoothScrollToPosition(rvHorizontalPicker.getChildLayoutPosition(view))
                    } else {
                        currentPosition = rvHorizontalPicker.getChildLayoutPosition(view)
                        val myIntent = Intent(view.context, PlayActivity::class.java)
                        startActivity(myIntent)
                        overridePendingTransition(R.anim.none, R.anim.none)
                    }

                }
            }
        }
    }



    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.none, R.anim.none)
    }

}
