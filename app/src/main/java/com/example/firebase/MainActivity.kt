package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val myquote=MyQuote()
    private lateinit var button:Button
    private lateinit var _quote:EditText
    private lateinit var _author:EditText
    private val quoteCollectionReference=Firebase.firestore.collection("quotes")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _quote=findViewById(R.id.quote)
        _author=findViewById(R.id.author)
        button=findViewById(R.id.button)

        button.setOnClickListener(View.OnClickListener {

            myquote.quote=_quote.text.toString()
            myquote.author=_author.text.toString()
            saveQuotes(myquote)
            clearFields()
        })

    }

    private fun saveQuotes(quote:MyQuote)= CoroutineScope(Dispatchers.IO).launch {
        try {
            quoteCollectionReference.add(quote)

            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext,"saved",Toast.LENGTH_LONG).show();
            }
            //clearFields()


        }catch ( e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show();
            }
        }
    }

    private fun clearFields() {
        _author.text.clear()
        _quote.text.clear()

    }


}