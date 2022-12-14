package com.example.threadcoroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.threadcoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis
import android.os.Message as Message

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //고전적인 방법으로 핸들러를 이용해서 개발스레드에서 데이터를 전송한다.
        /* val handler = object:Handler(){
             override fun handleMessage(msg: Message) {
                 super.handleMessage(msg)
                 val data = binding.tvMessage
                 binding.tvMessage.text = "sum = ${msg.arg1}"
             }
         }*/


        // 임폴트 종류 주의
        val channel = Channel<Int>()


        /*
        binding.btnCoroutine.setOnClickListener{
            //10초동안 계산을 처리하는 루틴
            //개발자 스레드
            thread {
                var sum:Long = 0L
                val time = measureTimeMillis {
                    for(i in 1..2_000_000_000){
                        sum += i
                    }
                }
                Log.d("coroutine", "time: ${time}")
                //오류가 화면에 발생하는 경우도 있고 아닌 경우도 있다
                val message = Message() //편지를 만든다
                message.arg1 = sum.toInt()
                handler.sendMessage(message) //우체부를 부른다
            }//end of thread
        }*/

        val backGroundScope = CoroutineScope(Dispatchers.Default + Job())

        binding.btnCoroutine.setOnClickListener{
            backGroundScope.launch {
                var sum: Long = 0L
                val time = measureTimeMillis {
                    for (i in 1..2_000_000_000) {
                        sum += i
                    }
                }
                Log.d("coroutine", "time: ${time}")
                //오류가 화면에 발생하는 경우도 있고 아닌 경우도 있다
                channel.send((sum.toInt()))
            }
        }


        val mainScope = GlobalScope.launch ( Dispatchers.Main){
            channel.consumeEach {
                binding.tvMessage.text = "sum = ${it}"
            }
        }


        binding.btnIntent.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("message", "${binding.edtMessage.text.toString()}")
            startActivity(intent)
        }
    }
}