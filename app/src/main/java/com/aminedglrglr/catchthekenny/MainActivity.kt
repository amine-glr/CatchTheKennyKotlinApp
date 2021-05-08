package com.aminedglrglr.catchthekenny

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.gridlayout.widget.GridLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var score=0

    var handler= Handler()
    var runnable= Runnable {  }

    lateinit var sharedPreferences: SharedPreferences
    var highScorePreferences: Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences= this.getSharedPreferences("com.aminedglrglr.layoutboun", MODE_PRIVATE)

        highScorePreferences= sharedPreferences.getInt("highScore", -1)

        if(highScorePreferences==-1){
            highScoreText.text="High Score: "
        }
        else{
            highScoreText.text= "High Score: ${highScorePreferences}"
        }


        hideImages()

        object: CountDownTimer(15000, 1000){
            override fun onTick(millisUntilFinished: Long) {
               timeText.text="Time: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
               timeText.text= "Time: 0"
                handler.removeCallbacks(runnable)
                for(image in gridLayout){
                    image.visibility=View.INVISIBLE
                }

                var alert =AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Do you want to play again?")
                alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                })
                alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_LONG).show()
                })

                alert.show()


            }

        }.start()

    }

    fun increaseScore(view: View){
        score=score+1
        scoreText.text="Score: ${score}"
        sharedPreferences.edit().putInt("highScore", score).apply()

    }





    fun hideImages(){

        runnable= object: Runnable{
            override fun run() {
                for(image in gridLayout){

                   image.visibility=View.INVISIBLE
                }
                val random= Random()
                val randomNumber= random.nextInt(3)
                val randomNumber2=random.nextInt(3)



                val view: View

                view= imageView1


                var params = GridLayout.LayoutParams(view.layoutParams)

                params.rowSpec = GridLayout.spec(randomNumber,3)

                params.columnSpec = GridLayout.spec(randomNumber2,3)


                view.layoutParams = params

                view.visibility=View.VISIBLE


                handler.postDelayed(runnable, 500)
            }

        }
        handler.post(runnable)



    }
}