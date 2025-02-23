package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import scisrc.mobiledev.ecommercelayout.ProductModel
import scisrc.mobiledev.ecommercelayout.R

class HomeFragment : Fragment() {
    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var recommendedAdapter: ProductAdapter
    private val recommendedProducts = mutableListOf<ProductModel>()

    private lateinit var promotionsRecyclerView: RecyclerView
    private lateinit var promotionsAdapter: ProductAdapter
    private val promotionsList = mutableListOf<ProductModel>()

    private lateinit var bannerViewPager: ViewPager2
    private lateinit var bannerIndicator: CircleIndicator3
    private val bannerHandler = Handler(Looper.getMainLooper())

    private val bannerRunnable = object : Runnable {
        override fun run() {
            val currentItem = bannerViewPager.currentItem
            val itemCount = bannerViewPager.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                bannerViewPager.currentItem = (currentItem + 1) % itemCount
            }
            bannerHandler.postDelayed(this, 3000) // เลื่อนทุก 3 วินาที
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // เชื่อม RecyclerView สินค้าแนะนำ
        recommendedRecyclerView = view.findViewById(R.id.recommendedProductsRecycler)
        recommendedRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recommendedProducts.add(ProductModel("ขนมปัง", "ขนมปังไส้กรอก", 35.00, R.drawable.ic_Sausage))
        recommendedProducts.add(ProductModel("ขนมปัง", "ขนมปังเนยถั่ว", 40.00, R.drawable.ic_Peanut))
        recommendedProducts.add(ProductModel("ขนมปัง", "ขนมปังหมูหยอง", 25.00, R.drawable.ic_Pork))

        recommendedAdapter = ProductAdapter(recommendedProducts) { updateFavorites() }
        recommendedRecyclerView.adapter = recommendedAdapter

        // เชื่อม RecyclerView โปรโมชั่น
        promotionsRecyclerView = view.findViewById(R.id.promotionsRecycler)
        promotionsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        promotionsList.add(ProductModel("ขนมปัง", "ไส้กรอก,เนยถั่ว,หมูหยอง", 100.00, R.drawable.bundle_a))
        promotionsList.add(ProductModel("ซื้อ 1 แถม 1", "โปรโมชั่นพิเศษ ซื้อ 1 แถม 1", 0.00, R.drawable.bundle_a))
        promotionsList.add(ProductModel("คูปองส่วนลด", "รับคูปองส่วนลด 50 บาท", 0.00, R.drawable.bundle_a))


        promotionsAdapter = ProductAdapter(promotionsList) { updateFavorites() }
        promotionsRecyclerView.adapter = promotionsAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerViewPager = view.findViewById(R.id.bannerViewPager)
        bannerIndicator = view.findViewById(R.id.bannerIndicator)

        val bannerImages = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        )

        val adapter = BannerAdapter(bannerImages)
        bannerViewPager.adapter = adapter

        bannerIndicator.setViewPager(bannerViewPager)
        bannerHandler.postDelayed(bannerRunnable, 3000)

        bannerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerHandler.removeCallbacks(bannerRunnable)
                bannerHandler.postDelayed(bannerRunnable, 3000)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerHandler.removeCallbacks(bannerRunnable)
    }

    private fun updateFavorites() {
        // อัปเดตรายการโปรด
    }
}
