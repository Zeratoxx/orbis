package com.badtke.orbis


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import java.util.ArrayList
import java.util.Arrays

class WorldsActivity : AppCompatActivity() {

    private lateinit var rvHorizontalPicker: RecyclerView
    private lateinit var tvSelectedItem: TextView

    private lateinit var imageView_back: ImageView
    private lateinit var imageView_last: ImageView
    private lateinit var imageView_next: ImageView
    private lateinit var world_eins: Button
    private lateinit var world_zwei: Button
    private lateinit var world_drei: Button

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
        world_eins = findViewById(R.id.button1)
        world_zwei = findViewById(R.id.button2)
        world_drei = findViewById(R.id.button3)
        imageView_last = findViewById(R.id.imageView_last)
        imageView_next = findViewById(R.id.imageView_next)

        tvSelectedItem = findViewById(R.id.tv_selected_item)
        rvHorizontalPicker = findViewById(R.id.rv_horizontal_picker)

    }

    private fun initializeOnClickActions() {
        imageView_back.setOnClickListener { onBackPressed() }
        world_eins.setOnClickListener { view ->
            val myIntent = Intent(view.context, PlayActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.none, R.anim.none)
        }
        world_zwei.setOnClickListener { view ->
            val myIntent = Intent(view.context, PlayActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.none, R.anim.none)
        }
        world_drei.setOnClickListener { view ->
            val myIntent = Intent(view.context, PlayActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.none, R.anim.none)
        }

        //imageView_last.setOnClickListener {rvHorizontalPicker.smoothScrollToPosition(rvHorizontalPicker.getChildLayoutPosition(view) + 1) }

        //imageView_next.setOnClickListener { onBackPressed() }
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
                    tvSelectedItem.text = data[layoutPosition]
                }
            }
        }

        // Setting Adapter
        rvHorizontalPicker.adapter = SliderAdapter().apply {
            setData(data)
            callback = object : SliderAdapter.Callback {
                override fun onItemClicked(view: View) {
                    rvHorizontalPicker.smoothScrollToPosition(rvHorizontalPicker.getChildLayoutPosition(view))
                }
            }
        }
    }



    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.none, R.anim.none)
    }

}
