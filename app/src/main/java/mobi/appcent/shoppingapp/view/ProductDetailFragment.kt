package mobi.appcent.shoppingapp.view

import android.app.Dialog
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_detail_fragment.*
import mobi.appcent.shoppingapp.R
import mobi.appcent.shoppingapp.adapter.DetailViewPagerAdapter
import mobi.appcent.shoppingapp.adapter.OnSizeItemClickListener
import mobi.appcent.shoppingapp.adapter.SizeViewAdapter
import mobi.appcent.shoppingapp.databinding.ProductDetailFragmentBinding
import mobi.appcent.shoppingapp.extension.changeTextColor
import mobi.appcent.shoppingapp.extension.convertHtml
import mobi.appcent.shoppingapp.model.SendDataModelToMap
import mobi.appcent.shoppingapp.model.SizeModel
import mobi.appcent.shoppingapp.utils.SharedPreferenceManager
import mobi.appcent.shoppingapp.viewmodel.ProductDetailViewModel


class ProductDetailFragment : Fragment() {
    private lateinit var binding: ProductDetailFragmentBinding
    private lateinit var viewModel: ProductDetailViewModel
    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var spManager: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_detail_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        spManager = SharedPreferenceManager()

        initObserver()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productid = args.productId
        viewModel.getProductDetails(productid)
        activity?.ivBackIcon?.visibility = View.VISIBLE
        activity?.ivBackIcon?.setOnClickListener {
            val action = ProductDetailFragmentDirections.actionProductDetailFragmentToHomeFragment()
            findNavController().navigate(action)
            Navigation.findNavController(requireView()).popBackStack(
                R.id.productDetailFragment, true)
        }

        btnIncrement.setOnClickListener {
            var value = Integer.parseInt(binding.tvOutput.text.toString())
            if (value in 1..9) {
                value++
                binding.tvOutput.text = value.toString()
            }
        }
        btnDecrement.setOnClickListener {
            var value = Integer.parseInt(binding.tvOutput.text.toString())
            if (value in 2..10) {
                value--
                binding.tvOutput.text = value.toString()
            }
        }
    }
    private fun initObserver() {
        viewModel.productDetailResponseList.observe(viewLifecycleOwner, {
            it.images?.let { images ->
                ViewPager(images)
            }

            binding.tvProductDetailTitle.text = it.productName
            it.priceDiscount?.let { price ->
                binding.tvOldPriceDetail.text = it.price + "₺"
                binding.tvNewPriceDetail.text = price + "₺"
                binding.tvOldPriceDetail.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } ?: run {
                binding.tvOldPriceDetail.text = it.price + "₺"
                binding.tvNewPriceDetail.visibility = View.GONE
                binding.tvOldPriceDetail.changeTextColor(R.color.title_color)
            }
            it.productDetailInfo?.let { str -> binding.tvProductExplanation.convertHtml(str) }
            sizeAdapter(it.sizeModel)

            binding.btnWhichShop.setOnClickListener { btn ->
                val location = arrayOf(it.latitude, it.longitude, args.productId.toString())
                val sendModel = SendDataModelToMap(it.latitude,it.longitude,Integer.parseInt(it.id))
                val action =
                    ProductDetailFragmentDirections.actionProductDetailFragmentToMapFragment(
                        sendModel
                    )
                findNavController().navigate(action)
            }

            binding.btnAddToCart.setOnClickListener { btn ->
                val dialog = MyCustomDialog(object : OnClickInterface {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        context?.let { context -> spManager.createAddSharedPreferences(context,true) }
                        findNavController().navigate(R.id.action_productDetailFragment_to_homeFragment)
                    }
                })
                fragmentManager?.let { it -> dialog.show(it, "TAG") }
            }
        })
    }
    private fun ViewPager(images: List<String>) {
        val vpDetailAdapter = DetailViewPagerAdapter(images)
        binding.vpDetail.adapter = vpDetailAdapter

    }
    private fun sizeAdapter(sizeList: List<SizeModel>) {
        if (sizeList == null) return

        rvSizeBox.layoutManager = GridLayoutManager(activity,3)
        rvSizeBox.adapter = context?.let {
            SizeViewAdapter(it, sizeList, object : OnSizeItemClickListener {
                override fun onClick(position: Int) {
                    sizeList.forEachIndexed { index, sizeModel ->
                        sizeList[index].isSelected =
                            sizeList[position].sizeText == sizeModel.sizeText
                    }
                    (rvSizeBox.adapter as SizeViewAdapter).setSizeList(sizeList)
                    viewModel.checkButtonEnable()
                }
            })
        }
    }
}