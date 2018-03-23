package com.valkyrie.nabeshimamac.leders_jankenapp

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    private lateinit var cpuBar: ProgressBar
    private lateinit var myBar: ProgressBar
    private lateinit var cpuImageView: ImageView
    private lateinit var resultTextView: TextView
    private lateinit var playerImageView: ImageView
    private lateinit var paaImageView: ImageView
    private lateinit var chokiImageView: ImageView
    private lateinit var gooImageView: ImageView
    private var random: Random = Random()
    private var randomNumber: Int by Delegates.notNull()
    private var cpuHp: Int by Delegates.notNull()
    private var userHp: Int by Delegates.notNull()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cpuImageView = findViewById(R.id.cpu)
        resultTextView = findViewById(R.id.result)
        playerImageView = findViewById(R.id.player)
        paaImageView = findViewById(R.id.image_choose_01)
        chokiImageView = findViewById(R.id.image_choose_02)
        gooImageView = findViewById(R.id.image_choose_03)

        cpuHp = 100
        userHp = 100

        cpuBar = findViewById(R.id.progressBar1)
        cpuBar.max = cpuHp
        cpuBar.progress = cpuHp
        myBar = findViewById(R.id.progressBar2)
        myBar.max = userHp
        myBar.progress = userHp

        reloadBar()

    }

    fun paa(view: View) {
        playerImageView.setImageResource(R.drawable.paa)
        cpuTurn()
        when (randomNumber) {
            0 -> win()
            1 -> lose()
            2 -> draw()
        }
        reloadBar()
        checkHp()
    }

    fun choki(view: View) {
        playerImageView.setImageResource(R.drawable.choki)
        cpuTurn()
        when (randomNumber) {
            0 -> lose()
            1 -> draw()
            2 -> win()
        }
        reloadBar()
        checkHp()
    }

    fun goo(view: View) {
        playerImageView.setImageResource(R.drawable.goo)
        cpuTurn()
        when (randomNumber) {
            0 -> draw()
            1 -> win()
            2 -> lose()
        }
        reloadBar()
        checkHp()
    }

    fun cpuTurn() {
        randomNumber = random.nextInt(3)
        when (randomNumber) {
            0 -> cpu.setImageResource(R.drawable.goo)
            1 -> cpu.setImageResource(R.drawable.choki)
            2 -> cpu.setImageResource(R.drawable.paa)
        }
    }

    fun win() {
        resultTextView.setText(R.string.result_win)

        cpuHp = cpuHp - 20

    }

    fun draw() {
        resultTextView.setText(R.string.result_draw)
        cpuHp = cpuHp - 10
        userHp = userHp - 10

    }

    fun lose() {
        resultTextView.setText(R.string.result_lose)
        userHp = userHp - 20
    }

    fun reloadBar() {
        onHogeProgressChanged(cpuHp, userHp)

        Log.d("cpu_life", Integer.toString(cpuHp))
        Log.d("user_life", Integer.toString(userHp))

    }

    private fun checkHp() {
        if (cpuHp <= 0 && userHp <= 0) {

            AlertDialog.Builder(this).setCancelable(false).apply {
                setTitle(R.string.result_draw)
                setMessage(R.string.user_lose_message)
                setPositiveButton("Retry", DialogInterface.OnClickListener { _, _ ->
                    // OKをタップしたときの処理
                    Toast.makeText(context, "リセットしました", Toast.LENGTH_LONG).show()
                    reGame()
                })
                show()
            }

        } else if (cpuHp <= 0) {
            AlertDialog.Builder(this).setCancelable(false).apply {
                setTitle(R.string.user_win_title)
                setMessage(R.string.user_win_message)
                setPositiveButton("Retry", DialogInterface.OnClickListener { _, _ ->
                    // OKをタップしたときの処理
                    Toast.makeText(context, "リセットしました", Toast.LENGTH_LONG).show()
                    reGame()
                })
                show()
            }

        } else if (userHp <= 0) {

            AlertDialog.Builder(this).setCancelable(false).apply {
                setTitle(R.string.user_lose_title)
                setMessage(R.string.user_lose_message)
                setPositiveButton("Retry", DialogInterface.OnClickListener { _, _ ->
                    // OKをタップしたときの処理
                    Toast.makeText(context, "リセットしました", Toast.LENGTH_LONG).show()
                    reGame()
                })
                show()
            }
        }

    }

    private fun onHogeProgressChanged(progressCpuInPercent: Int, progressUserInPercent: Int) {
        val cpuAnimation = ObjectAnimator.ofInt(cpuBar, "progress", progressCpuInPercent)
        cpuAnimation.duration = 500 // 0.5 second
        cpuAnimation.interpolator = DecelerateInterpolator()
        cpuAnimation.start()

        val userAnimation = ObjectAnimator.ofInt(myBar, "progress", progressUserInPercent)
        userAnimation.duration = 500 // 0.5 second
        userAnimation.interpolator = DecelerateInterpolator()
        userAnimation.start()
    }


    private fun reGame() {
        cpuHp = 100
        userHp = 100
        resultTextView.setText(R.string.result_title)

        reloadBar()

    }
}
