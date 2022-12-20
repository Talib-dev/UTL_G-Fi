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
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.dashboard.DashboardViewModel

class MonitoringFragment : BaseFragment<FragmentMonitringBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_monitring
    private lateinit var mBinding: FragmentMonitringBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding=getDataBinding()
    }

//   fun getBattery(Battery? battery, SysInfo? sysInfo) {
//       var data = arrayListOf<LiveModel>()
//        if (sysInfo != null) {
//            data.add(DataKey("ID", widget.serialNo));
//            data.add(DataKey("NAME", sysInfo.name!));
//        }
//        if (battery != null) {
//            data.add(DataKey("TYPE", battery.type!));
//            data.add(DataKey("VOLTAGE", battery.volt!));
//            data.add(DataKey("CURRENT", battery.curr!));
//        }
//        return data;
//    }
//
//    List<DataKey> _getSolar(OutputDc? solar) {
//        List<DataKey> data = <DataKey>[];
//        if (solar != null) {
//            data.add(DataKey("VOLTAGE", solar.volt!));
//            data.add(DataKey("CURRENT", solar.curr!));
//            data.add(DataKey("KHW", solar.power!));
//        }
//        return data;
//    }
//
//    List<DataKey> _getGrid(Bypass? outputAc) {
//        List<DataKey> data = <DataKey>[];
//        if (outputAc != null) {
//            data.add(DataKey("VOLTAGE", outputAc.volt!.the1P!));
//            data.add(DataKey("CURRENT", outputAc.curr!.the1P!));
//            data.add(DataKey("KHW", outputAc.actPower!.the1P!));
//        }
//        return data;
//    }
//
//    List<DataKey> _getByPass(Bypass? bypass) {
//        List<DataKey> data = <DataKey>[];
//        if (bypass != null) {
//            data.add(DataKey("VOLTAGE", bypass.volt!.the1P!));
//            data.add(DataKey("CURRENT", bypass.curr!.the1P!));
//            data.add(DataKey("KHW", bypass.actPower!.the1P!));
//        }
//        return data;
//    }
//
//    List<DataKey> _getLoad(OutputDc? bypass) {
//        List<DataKey> data = <DataKey>[];
//        if (bypass != null) {
//            data.add(DataKey("VOLTAGE", "--"));
//            data.add(DataKey("CURRENT", "--"));
//            data.add(DataKey("KHW", "--"));
//        }
//        return data;
//    }
//
//    List<DataKey> _getStatus(Status? status) {
//        List<DataKey> data = <DataKey>[];
//        if (status != null) {
//            data.add(DataKey("SYSTEM", status.outputAc!));
//            data.add(DataKey("LOAD", status.load!));
//            data.add(DataKey("INVERTER", status.inv!));
//            data.add(DataKey("BYPASS", status.bypass!));
//        }
//        return data;
//    }
}