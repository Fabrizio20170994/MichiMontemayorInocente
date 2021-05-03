package pe.edu.ulima.pm.michimontemayorinocente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Definición de cada texto de jugador y el turno (mutables)
        var player1Text = "O"
        var player2Text = "X"
        var playerTurn = true
        var turnText = "Le toca al jugador "

        // Guardamos cada botón buscándolos por ID
        val but1 = findViewById<AppCompatButton>(R.id.button1)
        val but2 = findViewById<AppCompatButton>(R.id.button2)
        val but3 = findViewById<AppCompatButton>(R.id.button3)
        val but4 = findViewById<AppCompatButton>(R.id.button4)
        val but5 = findViewById<AppCompatButton>(R.id.button5)
        val but6 = findViewById<AppCompatButton>(R.id.button6)
        val but7 = findViewById<AppCompatButton>(R.id.button7)
        val but8 = findViewById<AppCompatButton>(R.id.button8)
        val but9 = findViewById<AppCompatButton>(R.id.button9)

        // Guardamos el TextView de turno
        val textView = findViewById<TextView>(R.id.textTurn)

        // Texto Inicial
        textView.setText("Le toca al jugador " + player1Text)

        // Creamos una lista de botones, para luego crear Mapas con los índices y estados de cada botón
        val butList = listOf(but1,but2,but3,but4,but5,but6,but7,but8,but9)

        val but1Map = mutableMapOf("button" to 0, "state" to 0)
        val but2Map = mutableMapOf("button" to 1, "state" to 0)
        val but3Map = mutableMapOf("button" to 2, "state" to 0)
        val but4Map = mutableMapOf("button" to 3, "state" to 0)
        val but5Map = mutableMapOf("button" to 4, "state" to 0)
        val but6Map = mutableMapOf("button" to 5, "state" to 0)
        val but7Map = mutableMapOf("button" to 6, "state" to 0)
        val but8Map = mutableMapOf("button" to 7, "state" to 0)
        val but9Map = mutableMapOf("button" to 8, "state" to 0)

        // Lista de mapas de botonos
        val mapList = listOf(but1Map,but2Map,but3Map,but4Map,but5Map,but6Map,but7Map,but8Map,but9Map)


        for(i in mapList){
            butList[i["button"]!!].setOnClickListener{
                if(i["state"]!! == 0) {
                    if(playerTurn) {
                        butList[i["button"]!!].setText(player1Text)
                        i["state"] = 1
                        textView.setText(turnText + player2Text)
                    }
                    else {
                        butList[i["button"]!!].setText(player2Text)
                        i["state"] = 2
                        textView.setText(turnText + player1Text)
                    }
                    playerTurn = !playerTurn
                }
            }
        }
    }


}