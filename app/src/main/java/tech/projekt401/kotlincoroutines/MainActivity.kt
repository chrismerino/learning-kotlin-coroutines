package tech.projekt401.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "Result #1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {

            // IO = Local DB or Network interaction
            // Main = For doing stuff on the main thread, interacting with the UI
            // Default = Heavy computational work
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }

        }

    }

    private fun setNewText(input: String){

        val newText = textView.text.toString() + "\n$input"
        textView.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }


    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)
    }

    private suspend fun getResult1FromApi(): String{
        val name = "getResult1FromApi"
        logThread(name)
        delay(2500)
        return RESULT_1
    }


    private fun logThread(methodName: String){
        println("debug ${methodName}: ${Thread.currentThread().name}")
    }

}
