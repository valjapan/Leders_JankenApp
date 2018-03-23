package com.valkyrie.nabeshimamac.leders_jankenapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 *
 */

public class MainActivity_Java extends AppCompatActivity {
    ImageView cpu;//cpuの画像
    ImageView player;//playerの画像
    TextView result;//結果の文字
    TextView win;//勝利数の文字
    Random r = new Random();//ランダムな数字を発生させる
    int winCount;//勝利数を数える
    TextView cpuhp;//cpuのhpの文字
    TextView playerhp;//playerのhpの文字
    int cpuhpCount;//cpuのhpを数える
    int playerhpCount;//playerのhpを数える


    @Override//アプリ起動時の処理
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //レイアウトでおいた部品をプログラムで変更できるように
        cpu = (ImageView) findViewById(R.id.cpu);
        player = (ImageView) findViewById(R.id.player);
        result = (TextView) findViewById(R.id.result);
//        win = (TextView) findViewById(R.id.win);
        winCount = 0;
//        cpuhp=(TextView)findViewById(R.id.cpuhp);
//        playerhp=(TextView)findViewById(R.id.playerhp);
        cpuhpCount=100;
        playerhpCount=100;

    }

    public void goo(View v) {
        player.setImageResource(R.drawable.goo);//Playerの手をImageViewにセットする
        int random = r.nextInt(3);//0.1.2の3つの数字をランダムに生成

        if (random == 0) {//0の時にcpuがグーということにする
            cpu.setImageResource(R.drawable.goo);
            result.setText("引き分けです！");
        } else if (random == 1) {//1の時にチョキ
            cpu.setImageResource(R.drawable.choki);
            result.setText("あなたの勝ちです！");
            winCount=winCount+1;//勝利数を1増やす
            cpuhpCount=cpuhpCount-10;//cpuのhpを10減らす
        } else if (random == 2) {//2の時にパー
            cpu.setImageResource(R.drawable.paa);
            result.setText("あなたの負けです！");
            playerhpCount=playerhpCount-10;//playerのhpを10減らす
        }
        win.setText(winCount+" 回勝ちました！");
        cpuhp.setText("CPU(HP:"+cpuhpCount+")");//cpuのhpを表示
        playerhp.setText("PLAYER(HP:"+playerhpCount+")");//playerのhpを表示

    }

    public void choki(View v) {
        player.setImageResource(R.drawable.choki);//Playerの手をImageViewにセットする
        int random = r.nextInt(3);//0.1.2の3つの数字をランダムに生成

        if (random == 0) {//0の時にcpuがグーということにする
            cpu.setImageResource(R.drawable.goo);
            result.setText("あなたの負けです！");
            playerhpCount=playerhpCount-10;//playerのhpを10減らす
        } else if (random == 1) {//1の時にチョキ
            cpu.setImageResource(R.drawable.choki);
            result.setText("引き分けです！");
        } else if (random == 2) {//2の時にパー
            cpu.setImageResource(R.drawable.paa);
            result.setText("あなたの勝ちです！");
            winCount=winCount+1;//勝利数を1増やす
            cpuhpCount=cpuhpCount-10;//cpuのhpを10減らす
        }
        win.setText(winCount+" 回勝ちました！");
        cpuhp.setText("CPU(HP:"+cpuhpCount+")");//cpuのhpを表示
        playerhp.setText("PLAYER(HP:"+playerhpCount+")");//playerのhpを表示
    }

    public void paa(View v) {
        player.setImageResource(R.drawable.paa);//Playerの手をImageViewにセットする
        int random = r.nextInt(3);//0.1.2の3つの数字をランダムに生成

        if (random == 0) {//0の時にcpuがグーということにする
            cpu.setImageResource(R.drawable.goo);
            result.setText("あなたの勝ちです！");
            winCount=winCount+1;//勝利数を1増やす
            cpuhpCount=cpuhpCount-10;//cpuのhpを10減らす
        } else if (random == 1) {//1の時にチョキ
            cpu.setImageResource(R.drawable.choki);
            result.setText("あなたの負けです！");
            playerhpCount=playerhpCount-10;//playerのhpを10減らす
        } else if (random == 2) {//2の時にパー
            cpu.setImageResource(R.drawable.paa);
            result.setText("引き分けです！");
        }
        win.setText(winCount+" 回勝ちました！");
        cpuhp.setText("CPU(HP:"+cpuhpCount+")");//cpuのhpを表示
        playerhp.setText("PLAYER(HP:"+playerhpCount+")");//playerのhpを表示
    }


}