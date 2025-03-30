package com.hust.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val placeholder = StringBuilder()
        val result: TextView = findViewById(R.id.equals)

        //Hàm thêm kí tự mới vào chuỗi phép tính
        fun appendVal (string: String) {
            placeholder.append(string)
            result.text = placeholder.toString()
        }

        //Hàm xóa tất cả
        fun clearAll() {
            placeholder.clear()
            result.text = "0"
        }

        //Hàm xóa giá trị cuối cùng trong biểu thức
        fun clearEntry() {
            if (placeholder.isNotEmpty()) {
                val text = placeholder.toString()
                val lastOperatorIndex = text.indexOfLast { it == '+' || it == '-' || it == '*' || it == '/' }
                if (lastOperatorIndex != -1) {
                    placeholder.delete(lastOperatorIndex + 1, placeholder.length)
                } else {
                    placeholder.clear()
                    result.text= "0"
                }
                result.text = placeholder.toString()
            }
        }

        //Hàm chỉ xóa một kí tự trước đó
        fun backspace() {
            if (placeholder.isNotEmpty()) {
                placeholder.deleteCharAt(placeholder.length - 1)
                result.text = placeholder.toString()
            }
        }

        //Hàm đổi dấu
        fun toggleSign() {
            if (placeholder.isNotEmpty()) {
                val currentText = placeholder.toString()
                if (currentText.startsWith("-")) {
                    placeholder.replace(0, 1, "")
                } else {
                    placeholder.insert(0, "-")
                }
                result.text = placeholder.toString()
            }
        }

        //Các số
        findViewById<MaterialButton>(R.id.num0).setOnClickListener{appendVal("0")}
        findViewById<MaterialButton>(R.id.num1).setOnClickListener{appendVal("1")}
        findViewById<MaterialButton>(R.id.num2).setOnClickListener{appendVal("2")}
        findViewById<MaterialButton>(R.id.num3).setOnClickListener{appendVal("3")}
        findViewById<MaterialButton>(R.id.num4).setOnClickListener{appendVal("4")}
        findViewById<MaterialButton>(R.id.num5).setOnClickListener{appendVal("5")}
        findViewById<MaterialButton>(R.id.num6).setOnClickListener{appendVal("6")}
        findViewById<MaterialButton>(R.id.num7).setOnClickListener{appendVal("7")}
        findViewById<MaterialButton>(R.id.num8).setOnClickListener{appendVal("8")}
        findViewById<MaterialButton>(R.id.num9).setOnClickListener{appendVal("9")}

        //Các phép tính cộng trừ nhân chia và ngăn cách số thập phân
        findViewById<MaterialButton>(R.id.actionPlus).setOnClickListener{appendVal("+")}
        findViewById<MaterialButton>(R.id.actionMinus).setOnClickListener{appendVal("-")}
        findViewById<MaterialButton>(R.id.actionMultiply).setOnClickListener{appendVal("*")}
        findViewById<MaterialButton>(R.id.actionDivide).setOnClickListener{appendVal("/")}
        findViewById<MaterialButton>(R.id.floatNum).setOnClickListener{appendVal(".")}

        //Xóa và đổi dấu
        findViewById<MaterialButton>(R.id.deleteLastNum).setOnClickListener { clearEntry() }
        findViewById<MaterialButton>(R.id.deleteAll).setOnClickListener { clearAll() }
        findViewById<MaterialButton>(R.id.deleteOne).setOnClickListener { backspace() }
        findViewById<MaterialButton>(R.id.actionNegative).setOnClickListener { toggleSign() }

        //Tính kết quả
        findViewById<MaterialButton>(R.id.actionEqual).setOnClickListener{
            val expression = ExpressionBuilder(placeholder.toString()).build()
            val results = expression.evaluate()
            result.text = results.toString()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}