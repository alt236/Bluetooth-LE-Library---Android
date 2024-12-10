package uk.co.alt236.btlescan.ui.details

import AppFullScreenProgressIndicator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import kotlinx.coroutines.launch
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.theme.AppTheme
import uk.co.alt236.btlescan.app.ui.view.details.compose.DeviceDetailsContent
import uk.co.alt236.btlescan.ui.common.Navigation
import uk.co.alt236.btlescan.ui.details.recyclerview.DetailsRecyclerAdapter
import uk.co.alt236.btlescan.ui.details.recyclerview.RecyclerViewCoreFactory
import uk.co.alt236.btlescan.ui.details.usecases.IsComposeEnabledForDetailsUseCase
import javax.inject.Inject

@AndroidEntryPoint
class DeviceDetailsActivity : AppCompatActivity() {
    private val viewModel: DeviceDetailsViewModel by viewModels()
    private var device: BluetoothLeDevice? = null

    @Inject
    internal lateinit var isComposeEnabled: IsComposeEnabledForDetailsUseCase

    @SuppressLint("MissingPermission") // If we are here, permission has already been granted
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.device = intent.getParcelableExtra(EXTRA_DEVICE)

        supportActionBar?.title = device?.name.orEmpty()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateViewState(uiState)
                }
            }
        }

        if (savedInstanceState == null) {
            viewModel.perform(Action.ShowDeviceDetails(device))
        }
    }

    private fun updateViewState(uiState: UiState) {
        if (isComposeEnabled()) {
            updateComposeState(uiState)
        } else {
            updateLegacyViewState(uiState)
        }
    }

    private fun updateComposeState(uiState: UiState) {
        setContent {
            AppTheme {
                when (uiState) {
                    UiState.Loading -> AppFullScreenProgressIndicator()
                    is UiState.ShowData -> DeviceDetailsContent(uiState.uiItems)
                }
            }
        }
    }

    private fun updateLegacyViewState(uiState: UiState) {
        if (uiState is UiState.ShowData) {
            setContentView(LAYOUT_ID)
            val core = RecyclerViewCoreFactory.create(this)
            val recycler = findViewById<RecyclerView>(R.id.recycler)
            recycler.setLayoutManager(LinearLayoutManager(this))
            recycler.setAdapter(DetailsRecyclerAdapter(core, uiState.uiItems))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        return when (itemId) {
            R.id.menu_connect -> {
                Navigation(this).startControlActivity(device)
                true
            }

            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val EXTRA_DEVICE = DeviceDetailsActivity::class.java.name + ".EXTRA_DEVICE"
        private val LAYOUT_ID = R.layout.activity_details

        fun createIntent(
            context: Context,
            device: BluetoothLeDevice?,
        ): Intent {
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.putExtra(EXTRA_DEVICE, device)
            return intent
        }
    }
}
