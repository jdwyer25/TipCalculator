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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class TabFragment1 extends Fragment implements View.OnClickListener {

    protected View v;
    protected NumberPicker np;
    protected TextView billPay, tipAmount, perPerson;
    protected Button btnCalculate, btnReset;
    protected EditText totalBill, numPeople;

    double tipTotal, totalBillAmount, totalPerPerson;
    int currentPercentage;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        currentPercentage = np.getValue();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        v = inflater.inflate(R.layout.tab_fragment_1, container, false);

        //Get all the ids for the widgets in the view
        getViews(v);

        //Configure the number picker widget
        ConfigureNumberPicker();

        //Set the click event listeners
        SetClickEvents();

        //Reset the widget values - if they existed(for screen rotation)
        resetValues();

        return v;
    }

    private void resetValues() {
        //Check for a value
        if(tipTotal > 0){
            //Set the values again
            SetValues();

            //Reset the value to the current percentage
            np.setValue(currentPercentage);

        }
    }

    private void SetClickEvents() {
        btnReset.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }

    private void ConfigureNumberPicker() {
        np.setMinValue(0);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(false);
        np.setValue(15);
    }

    private void getViews(View v) {
        np = (NumberPicker) v.findViewById(R.id.numPicker);
        totalBill = (EditText) v.findViewById(R.id.edBillAmount);
        numPeople = (EditText) v.findViewById(R.id.edNumPeople);
        btnCalculate = (Button) v.findViewById(R.id.btnTipCalc);
        btnReset = (Button) v.findViewById(R.id.btnReset);
        billPay = (TextView) v.findViewById(R.id.lblBillPay);
        tipAmount = (TextView) v.findViewById(R.id.lblTipAmount);
        perPerson = (TextView) v.findViewById(R.id.lblPerPerson);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnTipCalc:

                hideKeyboard();

                if(ValidateValues()){
                    double total = Double.valueOf(totalBill.getText().toString());
                    int number = Integer.valueOf(numPeople.getText().toString());
                    int percentage = Integer.valueOf(np.getValue());

                    tipTotal = total * (Float.valueOf(percentage) / 100);
                    totalBillAmount = tipTotal + total;
                    totalPerPerson = totalBillAmount / number;

                    SetValues();

                }
                break;
            case R.id.btnReset:
                ClearInputs();
                break;
        }
    }

    private void SetValues() {

        //Set the widget values with formatting
        billPay.setText(String.format("$ %.2f", totalBillAmount));
        tipAmount.setText(String.format("$ %.2f", tipTotal));
        perPerson.setText(String.format("$ %.2f", totalPerPerson));

    }

    private void ClearInputs() {

        //Reset the number picker value to the default value of 15
        np.setValue(15);

        //Clear the values in all the other widgets
        totalBill.setText("");
        numPeople.setText("");
        billPay.setText("");
        tipAmount.setText("");
        perPerson.setText("");

        //Reset the stored values
        totalBillAmount = 0;
        totalPerPerson = 0;
        tipTotal = 0;
    }

    private void hideKeyboard() {
        InputMethodManager inm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private boolean ValidateValues() {

        if(totalBill.getText() == null || totalBill.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.no_bill_amount, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(numPeople.getText() == null || numPeople.getText().toString().equals("")){
            Toast.makeText(getActivity(), R.string.no_number_of_people, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
