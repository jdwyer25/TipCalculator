package dwyer.com.tipcalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TabFragment2 extends Fragment implements View.OnClickListener{

    protected View v;
    protected TextView lblTipPercentage;
    protected EditText txtBillAmount, txtTipAmount;
    protected Button btnCalculate, btnReset;

    double billAmount, tipAmount;
    int percentage;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        v = inflater.inflate(R.layout.tab_fragment_2, container, false);

        //get the ids for the widgets in the view
        getViews();

        //Set the on click listeners
        SetClickEvents();

        //Reset the widget values - if they existed(for screen rotation)
        resetValues();

        return v;
    }

    private void getViews() {
        txtBillAmount = (EditText) v.findViewById(R.id.edBillAmount);
        txtTipAmount = (EditText) v.findViewById(R.id.edTipAmount);
        lblTipPercentage = (TextView) v.findViewById(R.id.lblTipPercentage);
        btnCalculate = (Button) v.findViewById(R.id.btnTipCalc);
        btnReset = (Button) v.findViewById(R.id.btnReset);
    }

    private void SetClickEvents() {
        btnCalculate.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    private void resetValues() {
        if(billAmount > 0){
            SetValues();
        }
    }

    private void SetValues() {
        lblTipPercentage.setText(String.valueOf(percentage));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnTipCalc:

                hideKeyboard();

                if(ValidateValues()){
                    billAmount = Double.valueOf(txtBillAmount.getText().toString());
                    tipAmount = Double.valueOf(txtTipAmount.getText().toString());

                    percentage = (int) Math.floor((tipAmount / billAmount) * 100);

                    lblTipPercentage.setText(String.valueOf(percentage));

                }

                break;

            case R.id.btnReset:
                ClearInputs();
                break;
        }
    }

    private void ClearInputs() {
        lblTipPercentage.setText("");
        txtBillAmount.setText("");
        txtTipAmount.setText("");

        billAmount = 0;
        tipAmount = 0;
        percentage = 0;
    }

    private boolean ValidateValues() {

        if(txtBillAmount.getText() == null || txtBillAmount.getText().toString().equals("")){
            Toast.makeText(getActivity(), R.string.no_bill_amount, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtTipAmount.getText() == null || txtTipAmount.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.no_tip_amount, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void hideKeyboard() {
        InputMethodManager inm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
