package com.example.paypaycurrencyconverter.view


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paypaycurrencyconverter.CurrencyConverterApplication
import com.google.android.material.snackbar.Snackbar
import com.example.paypaycurrencyconverter.R
import com.example.paypaycurrencyconverter.databinding.ActivityCurrencyConverterBinding

import com.example.paypaycurrencyconverter.di.AppModule
import com.example.paypaycurrencyconverter.helper.ApiEndpoints
import com.example.paypaycurrencyconverter.helper.Helper
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import com.example.paypaycurrencyconverter.viewmodel.CurrencyConverterViewModelFactory
import java.text.DecimalFormat

import java.util.*
import javax.inject.Inject


class CurrencyConverterActivity : AppCompatActivity() {

    //Declare all variables

    //I am using viewBinding to get the reference of the views
    private var _binding: ActivityCurrencyConverterBinding? = null
    private val binding get() = _binding!!

    //Selected country string, default is Afghanistan, since its the first country listed in the spinner
    private var selectedItem1: String? = "AFN"
    private var selectedItem2: String? = "AFN"
    @Inject
    lateinit var viewModelFactory: CurrencyConverterViewModelFactory
    //ViewModel
    private lateinit var  currencyViewModel :CurrencyConverterViewModel

    private lateinit var adapter: CurrencyConvertAdapter
    private val currencies = arrayListOf<CurrencyEntity>()
    private var selectedPos = 0;

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState)
        //Make status bar transparent
        Helper.makeStatusBarTransparent(this)
        _binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupInject()
        setupViewModel()
        setupList()
        setupTextWatcher()

        loadData()

    }


    private fun setupInject() {
        (application as CurrencyConverterApplication).applicationComponent.inject(this)
    }
     fun setupViewModel(){
        currencyViewModel = ViewModelProvider(this,viewModelFactory) // Create reference wrt current fragment
             .get(CurrencyConverterViewModel::class.java)

    }

    private fun setupList() {
        adapter = CurrencyConvertAdapter()
        binding.currenciesList.layoutManager = GridLayoutManager(this, 3)
        binding.currenciesList.adapter = adapter
    }

    private fun setupTextWatcher() {
        binding.txtAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val amount = p0.toString()
                if (currencies.isNotEmpty() && amount.isNotEmpty()) {
                    val rates = amount.toDouble()

                    val newData = currencies.map {
                        CurrencyEntity(it.id, it.baseRate * rates) }

                    doConversion(binding.txtAmount.text.toString(),selectedPos)
                    refreshData(newData)
                }
            }
        })
    }
     fun doConversion(amount:String ,pos:Int){
         val rates = binding.txtAmount.text.toString().toDouble()
         val eRate = currencies[pos].baseRate * rates
         val res = DecimalFormat("#.###").format(eRate)
         val convertedRateText = binding.txtAmount.text.toString() + " USD" + " = "+ res + " " + currencies[pos].id
         binding.convertTo.text = convertedRateText
     }
    private fun refreshData(newData: List<CurrencyEntity>) {
        binding.currenciesList.removeAllViews()
        adapter.setCurrencies(newData)
        adapter.notifyDataSetChanged()
    }

    private fun loadData() {
        if (!Helper.isNetworkAvailable(this)){
            binding.prgLoading.visibility = View.GONE
            currencyViewModel.getAllData()
            currencyViewModel.currencyData.observe(this){ data ->
                if(data.isEmpty()){
                    Snackbar.make(binding.mainLayout,"You are not connected to the internet", Snackbar.LENGTH_LONG)
                        .withColor(ContextCompat.getColor(this, R.color.dark_red))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .show()
                }else {
                    setData(data)
                }
            }
            Snackbar.make(binding.mainLayout,"You are not connected to the internet", Snackbar.LENGTH_LONG)
                .withColor(ContextCompat.getColor(this, R.color.dark_red))
                .setTextColor(ContextCompat.getColor(this, R.color.white))
                .show()

        }else {
            binding.prgLoading.visibility = View.VISIBLE
            currencyViewModel.getAllData()
            currencyViewModel.currencyData.observe(this) { data ->
                setData(data)
        }

        }
    }

    fun setData(data:List<CurrencyEntity>){
        currencies.addAll(data)
        refreshData(currencies)

        // Setup Spinner
        val codes = currencies.map { it.id }
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            codes
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnCountry.adapter = spinnerAdapter
        binding.spnCountry.setSelection(codes.indexOf("USD"))
        binding.prgLoading.visibility = View.GONE
        binding.spnCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if(!binding.txtAmount.text.toString().equals("")){
                    selectedPos = position
                    doConversion(binding.txtAmount.text.toString(),selectedPos)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

    }



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



    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

}