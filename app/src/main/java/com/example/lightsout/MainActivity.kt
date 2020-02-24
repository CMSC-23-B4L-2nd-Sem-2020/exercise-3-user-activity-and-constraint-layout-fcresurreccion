package com.example.lightsout

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    // Declare variables for views
    private lateinit var textNickname: TextView
    private lateinit var editNickname: EditText
    private lateinit var doneButton: Button
    private lateinit var retryButton: Button
    private lateinit var moveCountText: TextView
    private var moveCount: Int = 0

    // Create 2d array for colorCode (0=white, 1=black)
    private var colorCode : Array<IntArray> = arrayOf(
        intArrayOf(0,0,0,0,0),
        intArrayOf(0,0,0,0,0),
        intArrayOf(0,0,0,0,0),
        intArrayOf(0,0,0,0,0),
        intArrayOf(0,0,0,0,0)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()

        // find views of the components
        editNickname = findViewById(R.id.edit_nickname)
        textNickname = findViewById(R.id.text_nickname)
        doneButton = findViewById(R.id.done_button)
        retryButton = findViewById(R.id.retry_button)
        moveCountText = findViewById(R.id.move_count_text)
    }

    // enter nickname when done button is clicked
    fun updateNickname(view : View){
        textNickname.text = editNickname.text

        textNickname.visibility = View.VISIBLE
        editNickname.visibility = View.GONE
        doneButton.visibility = View.GONE

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // change nickname when text is clicked
    fun changeNickname(view : View){
        textNickname.visibility = View.GONE
        editNickname.visibility = View.VISIBLE
        doneButton.visibility = View.VISIBLE

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editNickname, 0)
    }

    // return id of the box given row and col
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
        val grid: List<List<Int>> = listOf (
            row1, row2, row3, row4, row5
        )
        return grid[x][y]
    }

    // set onclick handler for the boxes
    private fun setListeners() {
        for (row in (0..4)) {
            for (col in (0..4)) {
                findViewById<Button>(getId(row,col)).setOnClickListener{
                    closeLights(it)
                }
            }
        }
    }

    // flip the clicked box and the adjacent boxes
    private fun closeLights(view : View) {
        if(!isGameOver()){  //check if all boxes are black
            // find the clicked box
            for (row in (0..4)) {
                for (col in (0..4)) {
                    if (getId(row, col) == view.id) {
                        flip(row, col) // flip the clicked box
                        if (row-1 >= 0) { //up
                            flip(row-1, col)
                        }
                        if (col-1 >=0) { //left
                            flip(row, col-1)
                        }
                        if (row+1 < 5) { //down
                            flip(row+1, col)
                        }
                        if (col+1 < 5) { //right
                            flip(row, col+1)
                        }
                    }
                }
            }

            // update visibility
            retryButton.visibility = View.VISIBLE
            moveCountText.visibility = View.VISIBLE

            // update count
            updateMoveCount()
        }
    }

    // change the color of the box
    private fun flip(row : Int, col : Int){
        val view : View = findViewById(getId(row,col))
        // If white, change to black
        if (getColorCode(row,col) == 0) {
            view.setBackgroundResource(R.color.black)
            this.setColorCode(row,col,1)
        }
        // If black, change to white
        else{
            view.setBackgroundResource(R.color.white)
            this.setColorCode(row,col,0)
        }
    }

    // click handler for Retry button
    fun retry(view : View){
        for (row in (0..4)) {
            for (col in (0..4)) {
                val box : View  = findViewById (getId (row, col))
                box.setBackgroundResource(R.color.white)
                setColorCode(row,col,0)
            }
        }
        // update visibility
        retryButton.visibility = View.GONE
        moveCountText.visibility = View.GONE
        moveCount = 0   // reset move count
    }

    // get color code of box given row and col (0:white, 1:black)
    private fun getColorCode(x:Int, y:Int): Int{
        return colorCode[x][y]
    }

    // set color code of box given row and col (0:white, 1:black)
    private fun setColorCode(x:Int, y: Int, code:Int){
        colorCode[x][y] = code
    }

    @SuppressLint("SetTextI18n")
    fun updateMoveCount(){
        moveCount += 1
        moveCountText.text = "Moves: $moveCount"
    }

    // check if game is over (all boxes are black)
	fun isGameOver(): Boolean{
	    for (row in (0..4)){
	        for (col in (0..4)){
				if (getColorCode(row,col) == 0){
				    return false
				}
		    }
	    }
	    return true
	}


}