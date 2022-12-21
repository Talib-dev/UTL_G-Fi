package com.kura.utl.ui.productDetails.monitoring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kura.utl.R
import com.kura.utl.databinding.FragmentDeshboredBinding
import com.kura.utl.databinding.FragmentMonitringBinding
import com.kura.utl.datalayer.modal.*
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonitoringFragment : BaseFragment<FragmentMonitringBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_monitring
    private lateinit var mBinding: FragmentMonitringBinding
    private val viewModel: DashboardViewModel by viewModels()
    private val args: MonitoringFragment by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding=getDataBinding()
    }

   fun getBattery(battery: Battery?,  sysInfo: SysInfo?):List<DataKey> {
       var data = arrayListOf<DataKey>()
        if (sysInfo != null) {
            data.add(DataKey("ID", sno));
            data.add(DataKey("NAME", sysInfo.Name));
        }
        if (battery != null) {
            data.add(DataKey("TYPE", battery.Type));
            data.add(DataKey("VOLTAGE", battery.Volt));
            data.add(DataKey("CURRENT", battery.Curr));
        }
        return data
    }

    fun getSolar( solar:Solar?):List<DataKey>  {
        var data = arrayListOf<DataKey>()
        if (solar != null) {
            data.add(DataKey("VOLTAGE", solar.Volt));
            data.add(DataKey("CURRENT", solar.Curr));
            data.add(DataKey("KHW", solar.Power));
        }
        return data;
    }

     fun getGrid( outputAc: OutputAC? ):List<DataKey> {
         var data = arrayListOf<DataKey>()
        if (outputAc != null) {
            data.add(DataKey("VOLTAGE", outputAc.Volt.BR));
            data.add(DataKey("CURRENT", outputAc.Curr.BR));
            data.add(DataKey("KHW", outputAc.ActPower.BR));
        }
        return data;
    }

    fun getByPass( bypass:Bypass?):List<DataKey>  {
        var data = arrayListOf<DataKey>()
        if (bypass != null) {
            data.add(DataKey("VOLTAGE", bypass.Volt.BR));
            data.add(DataKey("CURRENT", bypass.Curr.BR));
            data.add(DataKey("KHW", bypass.ActPower.BR));
        }
        return data;
    }

    fun getLoad(load:LoadPerc?):List<DataKey>  {
        var data = arrayListOf<DataKey>()
        if (load != null) {
            data.add(DataKey("VOLTAGE", "--"));
            data.add(DataKey("CURRENT", "--"));
            data.add(DataKey("KHW", "--"));
        }
        return data;
    }

     fun getStatus( status:Status?):List<DataKey>  {
        var data = arrayListOf<DataKey>()
        if (status != null) {
            data.add(DataKey("SYSTEM", status.OutputAC));
            data.add(DataKey("LOAD", status.Load));
            data.add(DataKey("INVERTER", status.Inv));
            data.add(DataKey("BYPASS", status.Bypass));
        }
        return data;
    }
}