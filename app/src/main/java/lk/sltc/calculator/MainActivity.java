package lk.sltc.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText questionText;
    EditText answerText;
    boolean operatorInUse;
    String previousOperator;

    List<Double> values = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.et_question);
        answerText = findViewById(R.id.et_answer);
    }


    public void numberTapped(View view) {
        Button numberButton = (Button)view;
        questionText.append(numberButton.getText());
        operatorInUse = false;
    }

    public void operatorTapped(View view) {
        if (!operatorInUse) {
            Button operatorButton = (Button) view;
            String currentOperator = operatorButton.getText().toString();
            int buttonId = operatorButton.getId();

            switch (buttonId) {
                case R.id.btn_dot:
                    questionText.append(operatorButton.getText());
                    break;
                case R.id.btn_equal:
                    values.add(getLastValue());
                    calculate(currentOperator);
                    questionText.setText("");
                    break;
                default:
                    questionText.append(operatorButton.getText());
                    values.add(getLastValue());
                    calculate(currentOperator);
                    break;
            }

            operatorInUse = true;
        }
    }

    public void clearTapped(View view) {
        operatorInUse = false;
        values.clear();
        questionText.setText("");
        answerText.setText("");
    }

    public void calculate(String currentOperator) {
        double answer = 0;
        if (values.size() == 1) {
            answer = values.get(0);
        } else if (values.size() == 2) {
            double firstValue = values.get(0);
            double secondValue = values.get(1);
            switch (previousOperator) {
                case "+":
                    answer = firstValue + secondValue;
                    break;
                case "-":
                    answer = firstValue - secondValue;
                    break;
                case "*":
                    answer = firstValue * secondValue;
                    break;
                case "/":
                    answer = firstValue / secondValue;
                    break;
            }
        }

        previousOperator = currentOperator;
        values.clear();
        values.add(answer);
        answerText.setText(String.valueOf(answer));
    }

    public double getLastValue() {
        double lastValue = 0.0;
        String question = questionText.getText().toString();
        String[] valueStrings = question.split("[-+/*]");
        if (valueStrings.length > 0) {
            int lastIndex = valueStrings.length - 1;
            String lastValueString = valueStrings[lastIndex];

            lastValue = Double.parseDouble(lastValueString);
        }

        return lastValue;
    }
}