package mobi.appcent.shoppingapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.Runnable
import mobi.appcent.shoppingapp.R
import mobi.appcent.shoppingapp.adapter.OnProductClickListener
import mobi.appcent.shoppingapp.adapter.ProductRecyclerAdapter
import mobi.appcent.shoppingapp.adapter.ViewPagerAdapter
import mobi.appcent.shoppingapp.databinding.HomeFragmentBinding
import mobi.appcent.shoppingapp.model.ProductList
import mobi.appcent.shoppingapp.model.Shopping
import mobi.appcent.shoppingapp.utils.SharedPreferenceManager
import mobi.appcent.shoppingapp.viewmodel.HomeViewModel
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var spManager: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getAllProducts()
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        spManager = SharedPreferenceManager()

        initObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.ivBackIcon?.visibility = View.INVISIBLE
        context?.let {context ->
            activity?.let { activity ->
                spManager.readFromSharedPreferences(context, activity) } }
    }

    private fun initObserver() {
        viewModel.productResponseList.observe(viewLifecycleOwner, {
            binding.tvMainTitle.text = it.mainTitle
            timedViewPager(it)
            binding.tvSubTitle.text = it.subTitle
            it.productList?.let { it_ -> setRecyclerViewAdapter(it_.toMutableList()) }
        })
    }

    private fun timedViewPager(item: Shopping) {
        val vpAdapter = item.categoryList?.let { ViewPagerAdapter(it) }
        binding.viewPager.adapter = vpAdapter
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                binding.viewPager.post(Runnable {
                    binding.viewPager.currentItem =
                        (binding.viewPager.currentItem + 1) % item.categoryList.size
                })
            }
        }
        val timer = Timer()
        timer.schedule(timerTask, 5000, 5000)
    }

    private fun setRecyclerViewAdapter(productList: MutableList<ProductList>) {
        rvMainProducts.layoutManager = GridLayoutManager(context, 2)
        rvMainProducts.adapter = productList.let {
            ProductRecyclerAdapter(it, object : OnProductClickListener {
                override fun onClick(position: Int) {
                    val productid = Integer.parseInt(it[position].id)
                    val bundle = Bundle().apply {
                        putInt("productId", productid)
                    }
                    findNavController().navigate(
                        R.id.action_homeFragment_to_productDetailFragment,
                        bundle
                    )
                }
            })
        }
    }
}


