package com.example.anders.tip_calculator;

import android.app.Activity;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends Activity {
    private static final NumberFormat currencyFormat=
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat=
            NumberFormat.getPercentInstance();
    private double billAmount = 0.0;
    private double percent = 0.15;
    private TextView AmountTextView; //min computer blev ved med at ændre til stort start bogstav
    private TextView PercentTextView;//samme
    private TextView tipTextView;
    private TextView totalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AmountTextView = (TextView) findViewById(R.id.AmountTextView);
        PercentTextView= (TextView) findViewById(R.id.PercentTextView);
        tipTextView =(TextView) findViewById(R.id.tipTextView);
        totalTextView= (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));
        EditText amountEditText=
                (EditText)findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar percentSeekBar=
                (SeekBar)findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(SeekbarListener);
    }
    private void calculate(){
        PercentTextView.setText(percentFormat.format(percent));
        double tip = billAmount*percent;
        double total = billAmount+tip;
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));

    }
  private final OnSeekBarChangeListener SeekbarListener =
          new OnSeekBarChangeListener() {
              @Override
              public void onProgressChanged(SeekBar seekBar, int progress,
                                           boolean fromUser){
                  percent = progress / 100.0;
                  calculate();

              }
              @Override
              public void onStartTrackingTouch(SeekBar seekBar) { }

              @Override
              public void onStopTrackingTouch(SeekBar seekBar) { }



          };
  private final TextWatcher amountEditTextWatcher= new TextWatcher() {
     @Override
      public void onTextChanged(CharSequence s,int start,
                                int before, int count){
         try{
             billAmount=Double.parseDouble(s.toString())/100.0;
             AmountTextView.setText(currencyFormat.format(billAmount));

         }
         catch (NumberFormatException e){
             AmountTextView.setText("");
             billAmount=0.0;
         }
         calculate();
     }
     @Override
      public void afterTextChanged(Editable s){}
      @Override
      public void beforeTextChanged(
              CharSequence s, int start, int count, int after){}
  };



}
