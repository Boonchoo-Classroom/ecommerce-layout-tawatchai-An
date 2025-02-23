package scisrc.mobiledev.ecommercelayout

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import scisrc.mobiledev.ecommercelayout.databinding.ActivityMainBinding
import scisrc.mobiledev.ecommercelayout.ui.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize navigation drawer
        drawerLayout = binding.drawerLayout

        // Add hamburger icon for drawer
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE)

        // Handle navigation item clicks
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            val selectedFragment = when (menuItem.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_cart -> CartFragment()
                R.id.nav_favorites -> FavoritesFragment()
                R.id.nav_products -> ProductListFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Load default fragment (Home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            binding.navView.setCheckedItem(R.id.nav_home)
        }
    }

    // ✅ ฟังก์ชันบันทึกสินค้าที่ถูกใจลง SharedPreferences
    fun updateFavorites(favorites: Set<String>) {
        sharedPreferences.edit().putStringSet("favorite_items", favorites).apply()
    }

    // ✅ ฟังก์ชันดึงรายการสินค้าที่ถูกใจจาก SharedPreferences
    fun getFavorites(): Set<String> {
        return sharedPreferences.getStringSet("favorite_items", emptySet()) ?: emptySet()
    }
}
