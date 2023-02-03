package com.example.paypaycurrencyconverter.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.example.paypaycurrencyconverter.R
import com.example.paypaycurrencyconverter.databinding.ActivityCurrencyConverterBinding
import com.example.paypaycurrencyconverter.helper.ApiEndpoints
import com.example.paypaycurrencyconverter.helper.Resource
import com.example.paypaycurrencyconverter.model.Rates
import com.example.paypaycurrencyconverter.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CurrencyConverterActivity : AppCompatActivity() {

    //Declare all variables

    //I am using viewBinding to get the reference of the views
    private var _binding: ActivityCurrencyConverterBinding? = null
    private val binding get() = _binding!!

    //Selected country string, default is Afghanistan, since its the first country listed in the spinner
    private var selectedItem1: String? = "AFN"
    private var selectedItem2: String? = "AFN"

    //ViewModel
    private val currencyViewModel: CurrencyConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState)

        //Make status bar transparent
        Helper.makeStatusBarTransparent(this)

        _binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Initialize both Spinner
        initSpinner()

        //Listen to click events
        setUpClickListener()
    }


    /**
     * This method does everything required for handling spinner (Dropdown list) - showing list of countries, handling click events on items selected.*
     */

    private fun initSpinner(){

        //get first spinner country reference in view
//        val spinner1 = binding.spnFirstCountry
//
//        //set items in the spinner i.e a list of all countries
//        spinner1.setItems( getAllCountries() )
//
//        //hide key board when spinner shows (For some weird reasons, this isn't so effective as I am using a custom Material Spinner)
//        spinner1.setOnClickListener {
//            Helper.hideKeyboard(this)
//        }
//
//        //Handle selected item, by getting the item and storing the value in a  variable - selectedItem1
//        spinner1.setOnItemSelectedListener { view, position, id, item ->
//            //Set the currency code for each country as hint
//            val countryCode = getCountryCode(item.toString())
//            val currencySymbol = getSymbol(countryCode)
//            selectedItem1 = currencySymbol
//            binding.txtFirstCurrencyName.setText(selectedItem1)
//        }


        //get second spinner country reference in view
        val spinner2 = binding.spnSecondCountry

        //hide key board when spinner shows
//        spinner1.setOnClickListener {
//            Helper.hideKeyboard(this)
//        }

        //set items on second spinner i.e - a list of all countries
        spinner2.setItems( getAllCountries() )


        //Handle selected item, by getting the item and storing the value in a  variable - selectedItem2,
        spinner2.setOnItemSelectedListener { view, position, id, item ->
            //Set the currency code for each country as hint
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            selectedItem2 = currencySymbol
            binding.txtSecondCurrencyName.setText(selectedItem2)
        }

    }


    /**
     * A method for getting a country's currency symbol from the country's code
     * e.g USA - USD
     */

    private fun getSymbol(countryCode: String?): String? {
        val availableLocales = Locale.getAvailableLocales()
        for (i in availableLocales.indices) {
            if (availableLocales[i].country == countryCode
            ) return Currency.getInstance(availableLocales[i]).currencyCode
        }
        return ""
    }


    /**
     * A method for getting a country's code from the country name
     * e.g Nigeria - NG
     */

    private fun getCountryCode(countryName: String) = Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }


    /**
     * A method for getting all countries in the world - about 256 or so
     */

    private fun getAllCountries(): ArrayList<String> {

        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()

        return countries
    }

    /**
     * A method for handling click events in the UI
     */

    private fun setUpClickListener(){

        //Convert button clicked - check for empty string and internet then do the conersion
        binding.btnConvert.setOnClickListener {

            //check if the input is empty
            val numberToConvert = binding.etFirstCurrency.text.toString()

            if(numberToConvert.isEmpty() || numberToConvert == "0"){
                Snackbar.make(binding.mainLayout,"Input a value in the first text field, result will be shown in the second text field", Snackbar.LENGTH_LONG)
                    .withColor(ContextCompat.getColor(this, R.color.dark_red))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }

            //check if internet is available
            else if (!Helper.isNetworkAvailable(this)){
                Snackbar.make(binding.mainLayout,"You are not connected to the internet", Snackbar.LENGTH_LONG)
                    .withColor(ContextCompat.getColor(this, R.color.dark_red))
                    .setTextColor(ContextCompat.getColor(this, R.color.white))
                    .show()
            }

            //carry on and convert the value
            else{
                doConversion()
            }
        }


        //handle clicks of other views
     /*   binding.txtContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val data: Uri = Uri.parse("mailto:ibrajix@gmail.com?subject=Hello")
            intent.data = data
            startActivity(intent)
        }*/

    }

    /**
     * A method that does the conversion by communicating with the API - fixer.io based on the data inputed
     * Uses viewModel and flows
     */

    private fun doConversion(){

        //hide keyboard
        Helper.hideKeyboard(this)

        //make progress bar visible
        binding.prgLoading.visibility = View.VISIBLE

        //make button invisible
        binding.btnConvert.visibility = View.GONE

        //Get the data inputed
        val apiKey = ApiEndpoints.API_KEY
        val from = selectedItem1.toString()
        val to = selectedItem2.toString()
        val amount = binding.etFirstCurrency.text.toString().toDouble()

        //do the conversion
        //currencyViewModel.getConvertedData(apiKey, from, to, amount)

        currencyViewModel.getExchangeData(apiKey, amount)

        //observe for changes in UI
        observeUi(to)

    }

    /**
     * Using coroutines flow, changes are observed and responses gotten from the API
     *
     */

    @SuppressLint("SetTextI18n")
    private fun observeUi(to:String) {

        currencyViewModel.exchangeData.observe(this, androidx.lifecycle.Observer {result ->

            when(result.status){
                Resource.Status.SUCCESS -> {
                   // if (result.data?.status == "success"){

                    val map: Map<String, Double>

                    Log.e("to","it");

                    map = result.data!!.rates
                    map.keys.forEach {
                       // Log.e("toaa",it);

                        if(it == to){
                                Log.e("touu",it)
                                val rateForAmount = map[it]

                                currencyViewModel.convertedRate.value = rateForAmount

                                //format the result obtained e.g 1000 = 1,000
                                val formattedString = String.format("%,.2f", currencyViewModel.convertedRate.value)
                            Log.e("touu",formattedString);
                                //set the value in the second edit text field
                                binding.etSecondCurrency.setText(formattedString)
                            }


                        }


                        //stop progress bar
                        binding.prgLoading.visibility = View.GONE
                        //show button
                        binding.btnConvert.visibility = View.VISIBLE
                   // }
//                     if(result.data?.status == "fail"){
//                        val layout = binding.mainLayout
//                        Snackbar.make(layout,"Ooops! something went wrong, Try again", Snackbar.LENGTH_LONG)
//                            .withColor(ContextCompat.getColor(this, R.color.dark_red))
//                            .setTextColor(ContextCompat.getColor(this, R.color.white))
//                            .show()
//
//                        //stop progress bar
//                        binding.prgLoading.visibility = View.GONE
//                        //show button
//                        binding.btnConvert.visibility = View.VISIBLE
//                    }
                }
                Resource.Status.ERROR -> {

                    val layout = binding.mainLayout
                    Snackbar.make(layout,  "Oopps! Something went wrong, Try again", Snackbar.LENGTH_LONG)
                        .withColor(ContextCompat.getColor(this, R.color.dark_red))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()
                    //stop progress bar
                    binding.prgLoading.visibility = View.GONE
                    //show button
                    binding.btnConvert.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    //stop progress bar
                    binding.prgLoading.visibility = View.VISIBLE
                    //show button
                    binding.btnConvert.visibility = View.GONE
                }
            }
        })
    }

    /**
     * Method for changing the background color of snackBars
     */

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

}