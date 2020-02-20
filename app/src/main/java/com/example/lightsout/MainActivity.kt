package com.example.lightsout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textNickname: TextView
    private lateinit var editNickname: EditText
    private lateinit var doneButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()

        editNickname = findViewById(R.id.edit_nickname)
        textNickname = findViewById(R.id.text_nickname)
        doneButton = findViewById(R.id.done_button)
    }

    fun updateNickname(view : View){
        textNickname.text = editNickname.text

        textNickname.visibility = View.VISIBLE
        editNickname.visibility = View.GONE
        doneButton.visibility = View.GONE

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun changeNickname(view : View){
        textNickname.visibility = View.GONE
        editNickname.visibility = View.VISIBLE
        doneButton.visibility = View.VISIBLE

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editNickname, 0)
    }


    private fun getId(x : Int, y : Int): Int {
        val row1: List<Int> = listOf(
            R.id.box_1_1,
            R.id.box_1_2,
            R.id.box_1_3,
            R.id.box_1_4,
            R.id.box_1_5
        )
        val row2: List<Int> = listOf(
            R.id.box_2_1,
            R.id.box_2_2,
            R.id.box_2_3,
            R.id.box_2_4,
            R.id.box_2_5
        )
        val row3: List<Int> = listOf(
            R.id.box_3_1,
            R.id.box_3_2,
            R.id.box_3_3,
            R.id.box_3_4,
            R.id.box_3_5
        )
        val row4: List<Int> = listOf(
            R.id.box_4_1,
            R.id.box_4_2,
            R.id.box_4_3,
            R.id.box_4_4,
            R.id.box_4_5
        )
        val row5: List<Int> = listOf(
            R.id.box_5_1,
            R.id.box_5_2,
            R.id.box_5_3,
            R.id.box_5_4,
            R.id.box_5_5
        )

        val grid: List<List<Int>> = listOf ( // is this allowed ????
                row1, row2, row3, row4, row5
        )
        return grid[x][y]
    }

    private fun setListeners() {
        for (row in (0..4)) {
            for (col in (0..4)) {
                findViewById<Button>(getId(row,col)).setOnClickListener{
                    closeLights(it)
                }
            }
        }
    }

    private fun closeLights(view : View) {
        for (row in (0..4)) {
            for (col in (0..4)) {
                if (getId(row, col) == view.id) {
                    flip(view)
                    if (row-1 >= 0) { //up
                        flip(view = findViewById (getId(row-1, col)))
                    }
                    if (col-1 >=0) { //left
                        flip(view = findViewById (getId(row, col-1)))
                    }
                    if (row+1 < 5) { //down
                        flip(view = findViewById (getId(row+1, col)))
                    }
                    if (col+1 < 5) { //right
                        flip(view = findViewById (getId(row, col+1)))
                    }
                }
            }
        }
    }

    private fun flip(view : View){
//        val drawableResource : Int = when (view.background){
//            R.drawable.white -> R.drawable.black
//            else -> R.drawable.white
//        }
//        view.setBackgroundResource(drawableResource)

        if (view.getTag() == R.color.white) {
            view.setBackgroundColor(R.color.black)
            view.setTag(R.color.black)
        }else{
            view.setBackgroundColor(R.color.white)
            view.setTag(R.color.white)
        }
    }

    fun retry(view : View){
        for (row in (0..4)) {
            for (col in (0..4)) {
                val box : View  = findViewById (getId (row, col))
                box.setBackgroundColor(R.color.white)
                box.setTag(R.color.white)
//                box.setBackgroundResource(R.drawable.white)
            }
        }
    }
}
