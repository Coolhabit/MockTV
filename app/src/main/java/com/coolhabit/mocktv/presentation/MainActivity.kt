package com.coolhabit.mocktv.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.coolhabit.mocktv.R
import com.coolhabit.mocktv.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by lazy {
        viewModelFactory.create(
            MainActivityViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getNavController().navigate(R.id.nav_graph)

//        binding.bottomNavigationView.setupWithNavController(
//            getNavController().apply {
//                addOnDestinationChangedListener(this@MainActivity)
//            }
//        )
//
//        binding.bottomNavigationView.setOnItemReselectedListener {
//            getNavController().apply {
//                popBackStack(it.itemId, true)
//                navigate(it.itemId)
//            }
//        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?,
    ) {}

    private fun getNavController(): NavController {
        return (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
}
