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

    private lateinit var cpuBar: ProgressBar //cpuの体力ゲージ
    private lateinit var myBar: ProgressBar //自分の体力ゲージ
    private lateinit var cpuImageView: ImageView //cpu側の画像
    private lateinit var resultTextView: TextView //結果の文字
    private lateinit var playerImageView: ImageView //自分の画像
    private lateinit var paaImageView: ImageView //パーの画像
    private lateinit var chokiImageView: ImageView //チョキの画像
    private lateinit var gooImageView: ImageView //グーの画像
    private var random: Random = Random() //ランダムな数字の表示
    private var randomNumber: Int by Delegates.notNull() //ランダムな数字の保存
    private var cpuHp: Int by Delegates.notNull() //cpuのHPを数える
    private var userHp: Int by Delegates.notNull() //自分のHPを数える


    //アプリ起動時の処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //レイアウトでおいた部品をプログラムで変更できるように

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
        //HPバーの表示（初期）

        reloadBar()
        //HPバーの更新

    }

    fun paa(view: View) {
        //パーを押した時の処理
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
        //チョキを押した時の処理
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
        //グーを押した時の処理
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
        //CPUのだす手をランダムするクラス
        randomNumber = random.nextInt(3)
        //適当に３つから１つを抽選する
        when (randomNumber) {
            0 -> cpu.setImageResource(R.drawable.goo)
            1 -> cpu.setImageResource(R.drawable.choki)
            2 -> cpu.setImageResource(R.drawable.paa)
        }
    }

    fun win() {
        //自分が勝ったの処理
        resultTextView.setText(R.string.result_win)

        cpuHp = cpuHp - 20

    }

    fun draw() {
        //引き分けの処理
        resultTextView.setText(R.string.result_draw)
        cpuHp = cpuHp - 10
        userHp = userHp - 10

    }

    fun lose() {
        //自分が負けの処理
        resultTextView.setText(R.string.result_lose)
        userHp = userHp - 20
    }

    fun reloadBar() {
        onHogeProgressChanged(cpuHp, userHp)
        //HPの減り方をじわじわ減らすためにアニメーションを導入()内に渡す値（今回はCPUのHPと自分のHP）を入力

        Log.d("cpu_life", Integer.toString(cpuHp))
        Log.d("user_life", Integer.toString(userHp))

    }

    private fun checkHp() {
        //HPを確認してどちらかのHPが0になってゲームオーバーになってないか確認するクラス
        if (cpuHp <= 0 && userHp <= 0) {
            //どちらも同時にHPが0になった時
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
            //自分が勝った時
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
            //自分が負けた時
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
        //ゲージの減り方のアニメーションのクラス
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
        //ゲームオーバーからもう一度ゲームをリセットする時にやる処理
        cpuHp = 100
        userHp = 100
        resultTextView.setText(R.string.result_title)

        reloadBar()

    }
}
