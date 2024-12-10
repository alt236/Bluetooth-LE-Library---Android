package uk.co.alt236.btlescan.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.theme.AppTheme
import uk.co.alt236.btlescan.app.ui.view.details.compose.DeviceDetailsContent
import uk.co.alt236.btlescan.ui.common.Navigation

class DeviceDetailsActivity : AppCompatActivity() {
    private var mapper: DetailsUiMapper? = null
    private var device: BluetoothLeDevice? = null

    @SuppressLint("MissingPermission") // We check before this is called
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val device: BluetoothLeDevice? = intent.getParcelableExtra(EXTRA_DEVICE)
        this.device = device

        mapper = DetailsUiMapper(this.application)

        supportActionBar?.title = device?.name.orEmpty()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = mapper!!.map(device)
        if (USE_COMPOSE) {
            setContent { // In here, we can call composables!
                AppTheme {
                    DeviceDetailsContent(items)
                }
            }
        } else {
            setContentView(LAYOUT_ID)
            val core = RecyclerViewCoreFactory.create(this)
            val recycler = findViewById<RecyclerView>(R.id.recycler)
            recycler.setLayoutManager(LinearLayoutManager(this))
            recycler.setAdapter(DetailsRecyclerAdapter(core, items))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.menu_connect) {
            Navigation(this).startControlActivity(device)
            return true
        } else if (itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val EXTRA_DEVICE = DeviceDetailsActivity::class.java.name + ".EXTRA_DEVICE"
        private val LAYOUT_ID = R.layout.activity_details
        private const val USE_COMPOSE = true

        fun createIntent(context: Context?, device: BluetoothLeDevice?): Intent {
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.putExtra(EXTRA_DEVICE, device)

            return intent
        }
    }
}
