package tech.projekt401.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {



        }

    }


    private suspend fun getResult1FromApi(){
        val name = "getResult1FromApi"
        logThread(name)
        delay(1000)
    }


    private fun logThread(methodName: String){
        println("debug ${methodName}: ${Thread.currentThread().name}")
    }

}
