package com.technoshaft.mycryptoapp


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.chip.Chip
import com.technoshaft.mycryptoapp.data.api.model.Crypto
import com.technoshaft.mycryptoapp.data.api.model.Timespan
import com.technoshaft.mycryptoapp.data.database.model.model.WatchlistItem
import com.technoshaft.mycryptoapp.databinding.ActivityChartBinding
import com.technoshaft.mycryptoapp.domain.viewmodel.ChartViewModel
import com.technoshaft.mycryptoapp.presentation.ui_state.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChartActivity : AppCompatActivity() {

    companion object {
        val extra_id = "extra_id"
        val extra_name = "extra_name"
        fun startActivity(context: Context, watchlistItem: Crypto) {
            val intent = Intent(context, ChartActivity::class.java)
            intent.putExtra(extra_name, watchlistItem.name)
            intent.putExtra(extra_id, watchlistItem.id)
            context.startActivity(intent)
        }

        fun startActivity(context: Context, watchlistItem: WatchlistItem) {
            val intent = Intent(context, ChartActivity::class.java)
            intent.putExtra(extra_name, watchlistItem.name)
            intent.putExtra(extra_id, watchlistItem.id)
            context.startActivity(intent)
        }
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityChartBinding

    private fun initChart(chart: LineChart, viewModel: ChartViewModel) {
        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(false)

        chart.setDrawGridBackground(false)

        val x = chart.xAxis
        x.isEnabled = false

        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false

        chart.invalidate()

        var selectedChip = R.id.chip_timespan_1d
        binding.chipsTimespan.setOnCheckedChangeListener { _, resID ->

            // check if currently checked chip has been unchecked
            val validatedResId = if (binding.chipsTimespan.checkedChipId != -1) {
                resID
            } else {
                binding.chipsTimespan.findViewById<Chip>(selectedChip)?.isChecked = true
                selectedChip
            }
            if (validatedResId == selectedChip) return@setOnCheckedChangeListener // no change
            selectedChip = validatedResId

            val timespan = when (selectedChip) {
                R.id.chip_timespan_1d -> Timespan.DAYS1
                R.id.chip_timespan_1w -> Timespan.DAYS7
                R.id.chip_timespan_1m -> Timespan.DAYS30
                R.id.chip_timespan_1y -> Timespan.YEAR1
                R.id.chip_timespan_2y -> Timespan.YEAR2
                R.id.chip_timespan_all -> Timespan.ALL_TIME
                else -> {
                    return@setOnCheckedChangeListener
                }
            }
            viewModel.loadHistoricalData(id, "usd", timespan.key.toInt())
        }

        binding.chipsTimespan.check(selectedChip)


    }

    lateinit var id: String
    lateinit var name: String
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.extras!!.getString(extra_id)!!
        name = intent.extras!!.getString(extra_id)!!

        val viewModel: ChartViewModel by viewModels()

        viewModel.loadHistoricalData(id, "usd", Timespan.DAYS1.key.toInt())



        binding = ActivityChartBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDefaultDisplayHomeAsUpEnabled(true);

        setTitle(name.uppercase(Locale.US))
        initChart(binding.chart, viewModel)
        setContentView(binding.root)




        viewModel.historicalData.observe(this@ChartActivity) { data ->

            when (data) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val entries = data.data?.prices?.mapIndexed { index, it ->
                        Entry(index.toFloat(), it[1])
                    }
                    val set1 = LineDataSet(entries, "Closing Price")
                    set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set1.setDrawIcons(true);
                    set1.setDrawValues(true);
                    set1.color = Color.BLUE
                    //set1.lineWidth = 2f

                    set1.setDrawCircles(false)

                    val lineData = LineData(set1)

                    binding.chart.data = lineData
                    binding.chart.invalidate()
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_chart)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}