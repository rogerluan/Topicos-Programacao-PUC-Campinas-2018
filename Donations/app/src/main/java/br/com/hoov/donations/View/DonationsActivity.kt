package br.com.hoov.donations.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.hoov.donations.Model.Donation
import br.com.hoov.donations.R
import br.com.hoov.donations.Store.Server
import kotlinx.android.synthetic.main.activity_donations.*

class DonationsActivity : AppCompatActivity() {
    private var donations: List<Donation> = listOf()
        set(value) { field = value; handleDonationsChanged() }
    private var filteredDonations: List<Donation> = listOf()
        set(value) { field = value; handleFilteredDonationsChanged() }

    // Components
    private val donationList by lazy {
        val result = RecyclerView(this, DonationRow::class.java, donations)
        result.itemSelectedHandler = { showDonation(it) }
        result
    }

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donations)
        // Set up action bar
        supportActionBar?.title = "Donations"
        // Set up donation list
        donationListContainer.addView(donationList)
        fetchDonations()
        refreshView.setOnRefreshListener { fetchDonations() }
        searchView.delayInMs = 0
        searchView.didSettleOnTextHandler = { searchDonations(it) }
    }

    // Update
    private fun handleDonationsChanged() {
        searchDonations(searchView.query.toString())
    }

    private fun handleFilteredDonationsChanged() {
        donationList.items = filteredDonations
    }

    // Interactions
    private fun fetchDonations() {
        Server.fetchDonations { donations, error ->
            refreshView.isRefreshing = false
            if (error == null) {
                this.donations = donations ?: listOf()
            } else {
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    @Suppress("NAME_SHADOWING")
    private fun searchDonations(query: String) {
        val query = query.trim()
        if (query.isBlank()) {
            filteredDonations = donations
            return
        }
        filteredDonations = donations.filter {
            it.title.contains(query) ||
            it.description.contains(query) ||
            it.contact.contains(query) ||
            it.city.contains(query) ||
            it.state.contains(query) ||
            it.address.contains(query) ||
            it.address.contains(query)
        }
    }

    private fun showDonation(donation: Donation) {
        DonationActivity.startActivity(this, donation)
    }
}
