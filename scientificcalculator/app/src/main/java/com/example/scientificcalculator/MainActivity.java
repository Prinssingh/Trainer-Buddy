package com.example.scientificcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button n1,n2,n3,n4,n5,n6,n7,n8,n9,n0,ndot,
            plus,minus,multiple,divide,pi,
            equal,factorial,square,square_root,dividefrom1,sin,cos,tan,log,ln,AC,C,bracket1,bracket2;

    TextView tvmain,tvsec;

    String PI = "3.141592653589793238";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n5 = findViewById(R.id.n5);
        n6 = findViewById(R.id.n6);
        n7 = findViewById(R.id.n7);
        n8 = findViewById(R.id.n8);
        n9 = findViewById(R.id.n9);
        n0 = findViewById(R.id.n0);
        ndot = findViewById(R.id.ndot);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        multiple = findViewById(R.id.multiple);
        divide = findViewById(R.id.divide);
        equal = findViewById(R.id.equal);
        factorial = findViewById(R.id.factorial);
        square = findViewById(R.id.square);
        square_root = findViewById(R.id.square_root);
        dividefrom1 = findViewById(R.id.dividefrom1);
        sin = findViewById(R.id.sin);
        cos = findViewById(R.id.cos);
        tan = findViewById(R.id.tan);
        log = findViewById(R.id.log);
        ln = findViewById(R.id.ln);
        AC = findViewById(R.id.AC);
        C = findViewById(R.id.C);
        pi = findViewById(R.id.pi);
        bracket1 = findViewById(R.id.bracket1);
        bracket2 = findViewById(R.id.bracket2);

        tvmain = findViewById(R.id.tvmain);
        tvsec = findViewById(R.id.tvsec);


        n1.setOnClickListener(this);
        n2.setOnClickListener(this);
        n3.setOnClickListener(this);
        n4.setOnClickListener(this);
        n5.setOnClickListener(this);
        n6.setOnClickListener(this);
        n7.setOnClickListener(this);
        n8.setOnClickListener(this);
        n9.setOnClickListener(this);
        n0.setOnClickListener(this);
        ndot.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiple.setOnClickListener(this);
        divide.setOnClickListener(this);
        equal.setOnClickListener(this);
        factorial.setOnClickListener(this);
        square.setOnClickListener(this);
        square_root.setOnClickListener(this);
        dividefrom1.setOnClickListener(this);
        sin.setOnClickListener(this);
        cos.setOnClickListener(this);
        tan.setOnClickListener(this);
        log.setOnClickListener(this);
        ln.setOnClickListener(this);
        AC.setOnClickListener(this);
        C.setOnClickListener(this);
        pi.setOnClickListener(this);
        bracket1.setOnClickListener(this);
        bracket2.setOnClickListener(this);

        tvmain.setOnClickListener(this);
        tvsec.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.n1:
                tvmain.setText(tvmain.getText()+"1");
                break;
            case R.id.n2:
                tvmain.setText(tvmain.getText()+"2");
                break;
            case R.id.n3:
                tvmain.setText(tvmain.getText()+"3");
                break;
            case R.id.n4:
                tvmain.setText(tvmain.getText()+"4");
                break;
            case R.id.n5:
                tvmain.setText(tvmain.getText()+"5");
                break;
            case R.id.n6:
                tvmain.setText(tvmain.getText()+"6");
                break;
            case R.id.n7:
                tvmain.setText(tvmain.getText()+"7");
                break;
            case R.id.n8:
                tvmain.setText(tvmain.getText()+"8");
                break;
            case R.id.n9:
                tvmain.setText(tvmain.getText()+"9");
                break;
            case R.id.n0:
                tvmain.setText(tvmain.getText()+"0");
                break;
            case R.id.ndot:
                if(tvmain.getText().toString().equals("")); {
                    tvmain.setText("0.");
                    
                }
                tvmain.setText(tvmain.getText()+".");
                break;
            case R.id.plus:
                tvmain.setText(tvmain.getText()+"+");
                break;
            case R.id.minus:
                tvmain.setText(tvmain.getText()+"-");
                break;
            case R.id.multiple:
                tvmain.setText(tvmain.getText()+"×");
                break;
            case R.id.divide:
                tvmain.setText(tvmain.getText()+"÷");
                break;
            case R.id.equal:
                String ans = tvmain.getText().toString();
                String repplacedstr = ans.replace('÷','/').replace('×','*');
                double result = eval(repplacedstr);
                tvmain.setText(String.valueOf(result));
                tvsec.setText(ans);
                break;
            case R.id.factorial:
                int valuee = Integer.parseInt(tvmain.getText().toString());
                int fact = fact(valuee);
                tvmain.setText(String.valueOf(fact));
                tvsec.setText(valuee+"!");
                break;
            case R.id.square:
                double d = Double.parseDouble(tvmain.getText().toString());
                double square = d*d;
                tvmain.setText(String.valueOf(square));
                tvsec.setText(d+"²");
                break;
            case R.id.square_root:
                //tvmain.setText(tvmain.getText()+"√");
                String val = tvmain.getText().toString();
                double r = Math.sqrt(Double.parseDouble(val));
                tvmain.setText(String.valueOf(r));
                break;
            case R.id.dividefrom1:
                tvmain.setText(tvmain.getText()+"1/");
                break;
            case R.id.sin:
                tvsec.setText(pi.getText());
                tvmain.setText(tvmain.getText()+"sin");
                break;
            case R.id.cos:
                tvsec.setText(pi.getText());
                tvmain.setText(tvmain.getText()+"cos");
                break;
            case R.id.tan:
                tvsec.setText(pi.getText());
                tvmain.setText(tvmain.getText()+"tan");
                break;
            case R.id.log:
                tvmain.setText(tvmain.getText()+"log(");
                break;
            case R.id.ln:
                tvmain.setText(tvmain.getText()+"ln(");
                break;
            case R.id.pi:
                tvsec.setText(pi.getText());
                tvmain.setText(tvmain.getText()+PI);
                break;
            case R.id.AC:
                tvmain.setText("");
                break;
            case R.id.C:
                String value = tvmain.getText().toString();
                value = value.substring(0, value.length()-1);
                tvmain.setText(value);
                break;
            case R.id.bracket1:
                tvmain.setText(tvmain.getText()+"(");
                break;
            case R.id.bracket2:
                tvmain.setText(tvmain.getText()+")");
                break;

        }
    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    int fact(int n){
        return (n==1 || n==0) ? 1 : n*fact(n-1);
    }
}