package pe.edu.ulima.pm.michimontemayorinocente

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton


class MainActivity : AppCompatActivity() {

    var but1:AppCompatButton? = null
    var but2:AppCompatButton? = null
    var but3:AppCompatButton? = null
    var but4:AppCompatButton? = null
    var but5:AppCompatButton? = null
    var but6:AppCompatButton? = null
    var but7:AppCompatButton? = null
    var but8:AppCompatButton? = null
    var but9:AppCompatButton? = null

    var butList : List<AppCompatButton?>? = null

    var but1Map : MutableMap<String, Any?>? = null
    var but2Map : MutableMap<String, Any?>? = null
    var but3Map : MutableMap<String, Any?>? = null
    var but4Map : MutableMap<String, Any?>? = null
    var but5Map : MutableMap<String, Any?>? = null
    var but6Map : MutableMap<String, Any?>? = null
    var but7Map : MutableMap<String, Any?>? = null
    var but8Map : MutableMap<String, Any?>? = null
    var but9Map : MutableMap<String, Any?>? = null

    var mapList : List<MutableMap<String, Any?>?>? = null

    var textView : TextView? = null

    // Definición de cada texto de jugador y el turno
    var player1Text : String? = "O"
    var player2Text : String? = "X"
    var playerTurn : Boolean? = null
    val turnText = "Le toca al jugador "

    var buttonsColor: Int? = null

    var gameCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGame(savedInstanceState)
        checkWinner()
    }

    fun initializeGame(savedInstanceState: Bundle?) {
        // Se inicializa cada texto de jugador y el turno
        if (player1Text == null || player2Text == null) {
            player1Text = ('A'..'Z').random().toString()
            do {
                player2Text = ('A'..'Z').random().toString()
            } while (player2Text == player1Text)
        }
        playerTurn = true

        // Guardamos cada botón buscándolos por ID
        but1 = findViewById<AppCompatButton>(R.id.button1)
        but2 = findViewById<AppCompatButton>(R.id.button2)
        but3 = findViewById<AppCompatButton>(R.id.button3)
        but4 = findViewById<AppCompatButton>(R.id.button4)
        but5 = findViewById<AppCompatButton>(R.id.button5)
        but6 = findViewById<AppCompatButton>(R.id.button6)
        but7 = findViewById<AppCompatButton>(R.id.button7)
        but8 = findViewById<AppCompatButton>(R.id.button8)
        but9 = findViewById<AppCompatButton>(R.id.button9)

        // Guardamos el TextView de turno
        textView = findViewById<TextView>(R.id.textTurn)


        // Creamos una lista de botones
        butList = listOf(but1, but2, but3, but4, but5, but6, but7, but8, but9)

        // Texto Inicial
        textView!!.setText("Le toca al jugador " + player1Text)

        // Creamos Mapas con los índices y estados de cada botón (0 = Ningún jugador lo ha apretado, 1 = Jugador 1 lo apretó, 2 = Jugador 2 lo apretó)
        but1Map = mutableMapOf<String, Any?>("button" to 0, "state" to 0)
        but2Map = mutableMapOf<String, Any?>("button" to 1, "state" to 0)
        but3Map = mutableMapOf<String, Any?>("button" to 2, "state" to 0)
        but4Map = mutableMapOf<String, Any?>("button" to 3, "state" to 0)
        but5Map = mutableMapOf<String, Any?>("button" to 4, "state" to 0)
        but6Map = mutableMapOf<String, Any?>("button" to 5, "state" to 0)
        but7Map = mutableMapOf<String, Any?>("button" to 6, "state" to 0)
        but8Map = mutableMapOf<String, Any?>("button" to 7, "state" to 0)
        but9Map = mutableMapOf<String, Any?>("button" to 8, "state" to 0)

        // Lista de mapas para poder iterar en cada uno
        mapList = listOf(
                but1Map,
                but2Map,
                but3Map,
                but4Map,
                but5Map,
                but6Map,
                but7Map,
                but8Map,
                but9Map
        )

        buttonsColor = Color.rgb((0..255).random(), (0..255).random(), (0..255).random())

        // En este punto hacemos la comprobación sobre si la instancia a sido reiniciada y se necesita recrear
        if (savedInstanceState != null){
            gameCount = savedInstanceState.getInt("count")
            // Asignamos los estados y variables guardados
            var instanceCount = 0
            for (i in mapList!!) {
                i!!["state"] = savedInstanceState.getStringArrayList("buttonState")!![instanceCount]
                instanceCount++
            }
            player1Text = savedInstanceState.getString("player1Text")!!
            player2Text = savedInstanceState.getString("player2Text")!!
            playerTurn = savedInstanceState.getBoolean("playerTurn")!!

            // Recreamos la pantalla según los parámetros guardados (turno y estado de los botones)
            if (playerTurn!!) textView!!.setText(turnText + player1Text) else textView!!.setText(turnText + player2Text)
            for (i in mapList!!) {
                if(i!!["state"] == 1) {
                    butList!![i!!["button"] as Int]!!.setText(player1Text)
                }
                else if (i!!["state"] == 2) {
                    butList!![i!!["button"] as Int]!!.setText(player2Text)
                }

            }
            buttonsColor = savedInstanceState.getInt("buttonColor")
        }

        if (gameCount > 1) {
            //Les damos colorsito
            for (button in butList!!) {
                val background: Drawable = button!!.background
                if (background is ShapeDrawable) {
                    background.paint.color = buttonsColor!!
                } else if (background is GradientDrawable) {
                    background.setColor(buttonsColor!!)
                } else if (background is ColorDrawable) {
                    background.color = buttonsColor!!
                }
            }
        }

        // Iteramos en cada MapList
        for(i in mapList!!){
            // Asignamos un Listener a cada botón que escriba la letra de cada jugador según su turno (y cambie el turno)
            butList!![i!!["button"] as Int]!!.setOnClickListener{
                if(i["state"] == 0) {
                    if(playerTurn!!) {
                        // Asignamos el texto de jugador 1 y cambiamos el estado
                        butList!![i["button"] as Int]!!.setText(player1Text)
                        i["state"] = 1
                        // Cambiamos el texto de Turno
                        textView!!.setText(turnText + player2Text)
                    }
                    else {
                        // Asignamos el texto de jugador 2 y cambiamos el estado
                        butList!![i!!["button"] as Int]!!.setText(player2Text)
                        i["state"] = 2
                        // Cambiamos el texto de Turno
                        textView!!.setText(turnText + player1Text)
                    }
                    // Cambiamos la variable de Turno
                    playerTurn = !playerTurn!!

                    checkWinner()
                }
            }
        }
    }

    // Función que guardará los valores de la Actividad en caso se rote la pantalla
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        // Se guardan los estados de los botones
        savedInstanceState.putIntegerArrayList("buttonState", arrayListOf(
                but1Map!!["state"] as Int,
                but2Map!!["state"] as Int,
                but3Map!!["state"] as Int,
                but4Map!!["state"] as Int,
                but5Map!!["state"] as Int,
                but6Map!!["state"] as Int,
                but7Map!!["state"] as Int,
                but8Map!!["state"] as Int,
                but9Map!!["state"] as Int
        ))

        // Se guarda el color de los botones
        savedInstanceState.putInt("buttonColor", buttonsColor!!)

        //Guardado de texto y turno
        savedInstanceState.putString("player1Text", player1Text)
        savedInstanceState.putString("player2Text", player2Text)
        savedInstanceState.putBoolean("playerTurn", playerTurn!!)

        savedInstanceState.putInt("count", gameCount)
    }

    @SuppressLint("SetTextI18n")
    fun checkWinner() {
        var winner = 0
        //check horizontal
        for (i in 0..6 step 3) {
            if (mapList!![i]!!["state"] == mapList!![i + 1]!!["state"] && mapList!![i]!!["state"] == mapList!![i + 2]!!["state"]) {
                winner = mapList!![i]!!["state"] as Int
                if (winner != 0) break
            }
        }

        if (winner == 0) {
            //check vertical
            for (i in 0..2) {
                if (mapList!![i]!!["state"] == mapList!![i + 3]!!["state"] && mapList!![i]!!["state"] == mapList!![i + 6]!!["state"]) {
                    winner = mapList!![i]!!["state"] as Int
                    if (winner != 0) break
                }
            }
        }

        if (winner == 0) {
            //check diagonal
            if (mapList!![0]!!["state"] == mapList!![4]!!["state"] && mapList!![0]!!["state"] == mapList!![8]!!["state"]) {
                winner = mapList!![0]!!["state"] as Int
            }
            if (mapList!![2]!!["state"] == mapList!![4]!!["state"] && mapList!![2]!!["state"] == mapList!![6]!!["state"]) {
                winner = mapList!![2]!!["state"] as Int
            }
        }

        var gameEnded = false
        if (winner != 0) {
            findViewById<TextView>(R.id.textTurn).text = "Gana el jugador ${if (winner == 1) player1Text else player2Text}!"
            gameEnded = true
        } else {
            if (!mapList!!.any { it!!["state"] == 0 }) {
                textView!!.text = "Empate!"
                gameEnded = true
            }
        }

        if (gameEnded) {
            for (button in butList!!) {
                button!!.setOnClickListener{restartGame()}
            }
            textView!!.setOnClickListener{restartGame()}
        }
    }

    private fun restartGame() {
        gameCount += 1
        for (button in butList!!) {
            button!!.text = null
        }
        player1Text = null
        player2Text = null
        initializeGame(null)
    }
}