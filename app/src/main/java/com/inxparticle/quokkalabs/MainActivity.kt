package com.inxparticle.quokkalabs

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.inxparticle.quokkalabs.adapter.BrakingBatAdapter
import com.inxparticle.quokkalabs.adapter.ItemClickInterface
import com.inxparticle.quokkalabs.data.model.BrakingBatApiResponse
import com.inxparticle.quokkalabs.databinding.ActivityMainBinding
import com.inxparticle.quokkalabs.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickInterface {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mBrakingBatAdapter:BrakingBatAdapter

    private val mArrayList:ArrayList<BrakingBatApiResponse.BrakingBatApiResponseItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.viewModel =viewModel
        viewModel.fetchBrakingBatAPIData()

        lifecycleScope.launchWhenCreated {

            viewModel._brakingBatList.collect{
                Log.e(TAG, "onCreate: $it" )
                mArrayList.addAll(it)
                mBrakingBatAdapter = BrakingBatAdapter(mArrayList,this@MainActivity,this@MainActivity)
                setRecyclerView()
            }
        }




    }

    private fun setRecyclerView() {
        activityMainBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
            adapter = mBrakingBatAdapter
        }
    }

    override fun itemClicked(postion:Int) {
    }

    override fun hideTheView(pos:Int) {
        activityMainBinding.showDetails.visibility = View.GONE
    }

    override fun showTheView(pos:Int) {
        activityMainBinding.showDetails.visibility = View.VISIBLE
        activityMainBinding.showDetails.text = mArrayList[pos].name

    }


}