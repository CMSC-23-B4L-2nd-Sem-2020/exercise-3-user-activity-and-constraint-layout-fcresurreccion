package com.example.lightsout

import android.content.Context
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
//    private lateinit var retryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

//    private fun retry(){}

//    private fun closeLights(){}

}
