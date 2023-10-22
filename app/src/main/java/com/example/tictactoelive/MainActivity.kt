package com.example.tictactoelive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout
    private lateinit var resetButton: Button
    private val board = Array(3){Array(3){0} }
    private var currentPlayer = 1
    private var gameOver = false

    // 0 0 0
    // 0 0 0
    // 0 0 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        gridLayout = findViewById(R.id.gridLayout)
        resetButton = findViewById(R.id.resetButton)

        initializeBoard()

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        for(i in 0 until 3){
            for(j in 0 until 3){
                val cell = gridLayout.getChildAt(i*3+j)as Button
                cell.text = ""
            }
        }
        board.map { it.fill(0) }
        currentPlayer = 1
        gameOver = false
        resetButton.visibility = View.INVISIBLE
    }

    inner class CellClickListener(private val row: Int, private val col: Int) : View.OnClickListener {
//        showToast("Cell Clicked")
//        return null

        override fun onClick(v: View) {
            if(board[row][col] == 0 && !gameOver){
                val symbol = if ( currentPlayer == 1) "X" else "O"
                (v as Button).text = symbol
                board[row][col]=currentPlayer

                if(checkForWin(row, col)){
                    gameOver = true
                    resetButton.visibility = View.VISIBLE
                    showToast("${symbol} won the game")
                }
                else if (!board.flatten().contains(0) && !gameOver){
                    gameOver = true
                    resetButton.visibility = View.VISIBLE
                    showToast("It's a draw!")
                }


                else {
                    currentPlayer = if (currentPlayer == 1) 2 else 1
                }
            }
        }
    }
    private fun checkForWin(row: Int, col: Int): Boolean {
        // Check for a row win
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer)
            return true

        // Check for a column win
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer)
            return true

        // Check for diagonal wins
        if (row == col && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            return true
        if (row + col == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
            return true



        return false
    }

    private fun initializeBoard() {
        for(i in 0 until 3){
            for(j in 0 until 3){
                val cell = Button(this)
                cell.layoutParams = GridLayout.LayoutParams()
                cell.textSize = 40f
                cell.setOnClickListener(CellClickListener(i,j))
                cell.text = ""
                gridLayout.addView(cell)
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
    }
}