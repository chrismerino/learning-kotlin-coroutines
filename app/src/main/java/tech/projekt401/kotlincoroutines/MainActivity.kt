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

            // IO = Para llamadas a la red (Retrofit o Volley) o para llamadas a una base local (Room)
            // Main = Para hacer cosas en el Hilo principal, como interactuar con el UI
            // Default = Para trabajo computacional que requiera bastantes recursos. Como renderizado.
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }

        }

    }
    
    // Una vez obtenido el valor y ya asegurando que esta disponible en Main Scope, ya es
    // manipulable y el resultado puede mostrarse u ocuparse donde sea necesario
    // Siempre y cuando sea necesario que se ocupe en el Main Scope.
    private fun setNewText(input: String){

        val newText = textView.text.toString() + "\n$input"
        textView.text = newText
    }

    // En este metodo se cambia el Scope, que inicialmente era IO desde donde se inicio la
    // Coroutine y se cambia al Main Scope para que el resultado pueda ser manipulado y
    // mostrado en un TextView que se encuentra en el Main Scope.
    
   // Si no se cambia el Scope antes de tratar de asignar el valor y mostrarlo en el TextView
    // La app se cerrara debido a que el valor no es accesible desde IO.
    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    // En este metodo se declara una variable cuyo valor se obtiene desde una llamada
    // a la API, una vez se obtenga el valor entonces se mandara ese valor desde el Score
    // IO (que es desde donde se inicio la Coroutine y se pasa al contexto Main para que
    // la app pueda mostrar el valor en una pantalla (en el Scope Main)
    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)
    }

    // Este metodo simula una llamada a una API que tarda 2500ms en responder.
    private suspend fun getResult1FromApi(): String{
        val name = "getResult1FromApi"
        logThread(name)
        delay(2500)
        return RESULT_1
    }


    // Este metodo sirve unicamente para propositos de "logear" el Hilo (Thread)
    // Donde se esta ejecutando la Coroutine.
    private fun logThread(methodName: String){
        println("debug ${methodName}: ${Thread.currentThread().name}")
    }

}
