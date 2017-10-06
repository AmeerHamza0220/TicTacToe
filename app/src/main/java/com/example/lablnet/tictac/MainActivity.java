package com.example.lablnet.tictac;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int i = 0;// number of turns
    final int n = 3; //number of rows and columns
    char[][] board = new char[4][4]; //Its 3X3 I did not use 0 index .
    CustomTableLayout customTableLayout;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        Button btn = (Button) findViewById(R.id.reset);
        txtResult = (TextView) findViewById(R.id.txtViewResult);
        customTableLayout = new CustomTableLayout(this);
        linear.addView(customTableLayout);
        customTableLayout.setOnItemClickListner(new CustomTableLayout.OnTableLayoutItemClick() {
            @Override
            public void onClick(TextView txt, int row, int column) {
                txt.setClickable(false);
                i++;
                setTetView(txt, i, row, column);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    /*
    This method set TextView for X,O .It means that you first time enter O second time X .
    It goes repeating
     */
    public void setTetView(TextView txt, int id, int row, int column) {
        if (id % 2 == 0) {
            txt.setText("X");
            txt.setTextColor(Color.GREEN);
            checkMatch(row, column, 'X');
        } else {
            String checkedMark = "\u2713";
            txt.setText(checkedMark);
            txt.setTextColor(Color.RED);
            checkMatch(row, column, '\u2713');
        }
    }

    /*
    This method check the winner
     */
    public void checkMatch(int row, int column, char character) {
        board[row][column] = character;
        checkRowWise(board, character, row);
        checkColumnWise(board, character, column);
        checkDiagonally(board, character, column, row);
        checkAntiDiagonally(board, character, column, row);
        /*
        * If all 9 turns are completed and no one win that's means "Tie/Draw"
        */
        if (i >= 9) {
            if (txtResult.getText().toString().equals(""))
                txtResult.setText("Draw!");
        }
    }

    /*
      Search row . If all rows has same character then player is winner
     */
    public void checkRowWise(char[][] board, char player, int row) {
        for (int i = 1; i <= n; i++) {
            //check the specific row to optimize algorithm
            if (board[row][i] != player)
                break;
            // if any row nor equal to player (either X or O) then break this method
            if (i == n) {
                /*if i is equal to n amd this method is not break(still running)
                then all row is same
                */
                txtResult.setText(player + " Won!");
                //so one player is won
                endGame();
            }

        }
    }

    /*
  Search column . If all columns has same character then player is winner
 */
    public void checkColumnWise(char[][] board, char player, int column) {
        for (int i = 1; i <= n; i++) {
            if (board[i][column] != player)
                break;
            if (i == n) {
                txtResult.setText(player + " Won!");
                endGame();
            }
        }
    }

    /*
    Search diagonally such as in row 1 and column 1, row 2 column 2 and so on.
     */
    public void checkDiagonally(char[][] board, char player, int column, int row) {
        /*
        * if row is equal to column so we need to check diagonally*/
        if (row == column) {
            for (int i = 1; i <= n; i++) {
                if (board[i][i] != player)
                    break;
                if (i == n) {
                    txtResult.setText(player + " Won!");
                    endGame();
                }
            }
        }
    }

    /*
   Search anti diagonally such as in row 1 and column 3, row 2 column 2 and so on.
   As we can see row number is going to incremented and column number decremented.
    */
    public void checkAntiDiagonally(char[][] board, char player, int column, int row) {
        /*
        * If the sum of row and column is n+1 in our case its 4 then we need to check anti diagonally
        * */
            if (row + column == n + 1) {
            for (int i = 1; i <= n; i++) {
                // i is the number of row i.e incremented by 1, the column number is decremented after each iteration
                if (board[i][(n + 1) - i] != player)
                    break;
                if (i == n) {
                    txtResult.setText(player + " Won!");
                    endGame();
                }
            }
        }
    }
    /*
    * This method freezed up all remaining cells when a player win */

    public void endGame() {
        /*
        * accessing all rows in this table layout
        */
        for (int i = 0; i < customTableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) customTableLayout.getChildAt(i);
            for (int z = 0; z < tableRow.getChildCount(); z++) {
                  /*
        * accessing all TextView in this table row
        */
                TextView txt = (TextView) tableRow.getChildAt(z);
                txt.setClickable(false);
            }
        }
    }
    public void resetGame(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                aboutDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void aboutDialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("About");
        dialog.setMessage("Developed by Ameer Hamza");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
