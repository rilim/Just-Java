/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.toast_more), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.toast_less), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Saves information into variable from EditText view
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //  Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Stores information in a String, whether client wants whipped cream topping or not
        String cream;
        if (hasWhippedCream) {
            // client wants whipped cream topping, then String value is Yes
            cream = getString(R.string.yes);
        }
        else {
            // client doesn't want whipped cream topping, then String value is No
            cream = getString(R.string.no);
        }

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Stores information in a String, whether client wants chocolate topping or not
        String chocolate;
        if (hasChocolate) {
            // client wants chocolate topping, then String value is Yes
            chocolate = getString(R.string.yes);
        }
        else {
            // client doesn't want chocolate topping, then String value is No
            chocolate = getString(R.string.no);
        }

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, cream, chocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add 1Eur if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        //Add 2Eur if the user wants chocolate
    if(addChocolate) {
        basePrice = basePrice + 2;
    }

        //Calculate the total order price by multiplying by quantity
    return quantity * basePrice;

}

    /**
     * This method creates order summary
     *
     * @param name            of the customer
     * @param price           of the order
     * @param cream is whether or not the user wants whipped cream topping
     * @param chocolate    is whether or not the user wants chocolate topping
     * @return order text summary
     */
    private String createOrderSummary(int price, String cream, String chocolate, String name) {
        String orderSummary = getString(R.string.order_summary_name, name);
        orderSummary += "\n" + getString(R.string.order_summary_cream) + cream;
        orderSummary += "\n" + getString(R.string.order_summary_chocolate) + chocolate;
        orderSummary += "\n" + getString(R.string.order_summary_quantity, quantity);
        orderSummary += "\n" + getString(R.string.order_summary_total, NumberFormat.getCurrencyInstance().format(price));
        orderSummary += "\n" + getString(R.string.thank_you);

        return orderSummary;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


}
