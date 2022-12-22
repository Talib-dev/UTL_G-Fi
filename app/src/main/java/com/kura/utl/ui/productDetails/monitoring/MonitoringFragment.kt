package com.kura.utl.ui.productDetails.monitoring

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.kura.utl.R
import com.kura.utl.databinding.FragmentDeshboredBinding
import com.kura.utl.databinding.FragmentMonitringBinding
import com.kura.utl.datalayer.modal.*
import com.kura.utl.ui.MainViewModel
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.dashboard.DashboardAdapter
import com.kura.utl.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonitoringFragment : BaseFragment<FragmentMonitringBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_monitring
    private lateinit var mBinding: FragmentMonitringBinding
    private lateinit var sharedViewModel: MainViewModel
    private val viewModel: MonitoringViewModel by viewModels()
    private lateinit var batteryAdapter: MonitoringAdapter
    private lateinit var solarAdapter: MonitoringAdapter
    private lateinit var gridAdapter: MonitoringAdapter
    private lateinit var bypassAdapter: MonitoringAdapter
    private lateinit var loadAdapter: MonitoringAdapter
    private lateinit var statusAdapter: MonitoringAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()

        batteryAdapter = MonitoringAdapter(arrayListOf())
        getDataBinding().rvBattery.adapter = batteryAdapter

        solarAdapter = MonitoringAdapter(arrayListOf())
        getDataBinding().rvSolar.adapter = solarAdapter

        gridAdapter = MonitoringAdapter(arrayListOf())
        getDataBinding().rvGrid.adapter = gridAdapter

        bypassAdapter = MonitoringAdapter(arrayListOf())
        getDataBinding().rvBypass.adapter = bypassAdapter

        loadAdapter = MonitoringAdapter(arrayListOf())
        getDataBinding().rvLoad.adapter = loadAdapter

        statusAdapter = MonitoringAdapter(arrayListOf())
        getDataBinding().rvStatus.adapter = statusAdapter

        sharedViewModel.serialNo?.let {
            viewModel.getSystem(it)
            viewModel.systemInfo.observe(viewLifecycleOwner) { data ->
                Log.d("monitoringFragment", "name: ${data.SysInfo?.Name}")
                if (getBattery(data.Battery, data.SysInfo).isNotEmpty()) {
                    mBinding.llBattery.visibility = View.VISIBLE
                    batteryAdapter.update(getBattery(data.Battery, data.SysInfo))
                } else {
                    mBinding.llBattery.visibility = View.GONE
                }
                if (getSolar(data.Solar).isNotEmpty()) {
                    mBinding.llSolar.visibility = View.VISIBLE
                    solarAdapter.update(getSolar(data.Solar))
                } else {
                    mBinding.llSolar.visibility = View.GONE
                }
                if (getGrid(data.InputAC).isNotEmpty()) {
                    mBinding.llGrid.visibility = View.VISIBLE
                    gridAdapter.update(getGrid(data.InputAC))
                } else {
                    mBinding.llGrid.visibility = View.GONE
                }
                if (getByPass(data.Bypass).isNotEmpty()) {
                    mBinding.llBypass.visibility = View.VISIBLE
                    bypassAdapter.update(getByPass(data.Bypass))
                } else {
                    mBinding.llBypass.visibility = View.GONE
                }
//                loadAdapter.update(getLoad(data.LoadPerc))
                if (getStatus(data.Status).isNotEmpty()) {
                    mBinding.llStatus.visibility = View.VISIBLE
                    statusAdapter.update(getStatus(data.Status))
                } else {
                    mBinding.llStatus.visibility = View.GONE
                }
            }
        } ?: run {
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
        }

    }

    fun getBattery(battery: Battery?, sysInfo: SysInfo?): List<DataKey> {
        var data = arrayListOf<DataKey>()
        if (sysInfo != null) {
            data.add(DataKey("ID", sharedViewModel.serialNo ?: ""));
            data.add(DataKey("NAME", sysInfo.Name ?: ""));
        }
        if (battery != null) {
            data.add(DataKey("TYPE", battery.Type ?: ""));
            data.add(DataKey("VOLTAGE", battery.Volt ?: ""));
            data.add(DataKey("CURRENT", battery.Curr ?: ""));
        }
        return data
    }

    fun getSolar(solar: Solar?): List<DataKey> {
        var data = arrayListOf<DataKey>()
        if (solar != null) {
            data.add(DataKey("VOLTAGE", solar.Volt ?: ""));
            data.add(DataKey("CURRENT", solar.Curr ?: ""));
            data.add(DataKey("KHW", solar.Power ?: ""));
        }
        return data;
    }

    fun getGrid(inputAC: InputAC?): List<DataKey> {
        var data = arrayListOf<DataKey>()
        if (inputAC != null) {
            data.add(DataKey("VOLTAGE", inputAC.Volt?.BR ?: ""));
            data.add(DataKey("CURRENT", inputAC.Curr?.BR ?: ""));
            data.add(DataKey("KHW", inputAC.ActPower?.BR ?: ""));
        }
        return data;
    }

    fun getByPass(bypass: Bypass?): List<DataKey> {
        val data = arrayListOf<DataKey>()
        if (bypass != null) {
            data.add(DataKey("VOLTAGE", bypass.Volt?.BR ?: ""));
            data.add(DataKey("CURRENT", bypass.Curr?.BR ?: ""));
            data.add(DataKey("KHW", bypass.ActPower?.BR ?: ""));
        }
        return data;
    }

    fun getLoad(load: LoadPerc?): List<DataKey> {
        var data = arrayListOf<DataKey>()
        if (load != null) {
            data.add(DataKey("VOLTAGE", "--"));
            data.add(DataKey("CURRENT", "--"));
            data.add(DataKey("KHW", "--"));
        }
        return data;
    }

    fun getStatus(status: Status?): List<DataKey> {
        var data = arrayListOf<DataKey>()
        if (status != null) {
            data.add(DataKey("SYSTEM", status.OutputAC ?: ""));
            data.add(DataKey("LOAD", status.Load ?: ""));
            data.add(DataKey("INVERTER", status.Inv ?: ""));
            data.add(DataKey("BYPASS", status.Bypass ?: ""));
        }
        return data;
    }
}