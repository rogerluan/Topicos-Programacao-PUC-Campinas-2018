package br.com.hoov.donations.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.hoov.donations.Model.Comment
import br.com.hoov.donations.Model.Donation
import br.com.hoov.donations.R
import br.com.hoov.donations.Store.Server
import br.com.hoov.donations.Utilities.isGone
import kotlinx.android.synthetic.main.activity_donation.*

class DonationActivity : AppCompatActivity() {
    private lateinit var donation: Donation
    private var comments: MutableList<Comment> = mutableListOf()
        set(value) { field = value; handleCommentsChanged() }

    companion object {
        private const val DONATION = "DONATION"

        @JvmStatic fun startActivity(context: Context, donation: Donation) {
            context.startActivity(Intent(context, DonationActivity::class.java).apply {
                putExtra(DONATION, donation)
            })
        }
    }

    // Components
    private val commentsList by lazy {
        val result = RecyclerView(this, CommentRow::class.java, comments)
        result.isNestedScrollingEnabled = false
        result
    }

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)
        // Unwrap parcelable
        donation = intent!!.getParcelableExtra(DONATION)!!
        // Set up action bar
        supportActionBar?.title = donation.title
        supportActionBar?.subtitle = "${donation.city} ${donation.state}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Set up content
        titleTextView.text = donation.title
        descriptionTextView.text = donation.description
        cityTextView.text = donation.city
        stateTextView.text = donation.state
        addressTextView.text = donation.address
        contactTextView.text = donation.contact
        addCommentButton.setOnClickListener { promptAddComment() }
        // Hide views if needed
        if (descriptionTextView.text.isBlank()) descriptionTextView.isGone = true
        if (contactTextView.text.isBlank()) contactTextView.isGone = true
        if (addressTextView.text.isBlank()) addressTextView.isGone = true
        // Populate comments list
        commentsContainerView.addView(commentsList)
        fetchComments(donation.id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    // Updating
    private fun handleCommentsChanged() {
        commentsList.items = comments
    }

    // Interaction
    private fun fetchComments(donationID: Int) {
        Server.fetchDonationComments(donationID) { comments, error ->
            if (error == null && comments != null) {
                this.comments = comments.toMutableList()
            } else {
                Toast.makeText(this, error?.message ?: "An error occurred.", Toast.LENGTH_LONG).show()
            }
        }
    }

    @Suppress("NAME_SHADOWING")
    private fun postComment(comment: String) {
        Server.postCommentForDonation(donation.id, comment) { comment, error ->
            if (comment != null && error == null) {
                comments.add(comment)
                handleCommentsChanged()
            } else {
                Toast.makeText(this, error?.message ?: "An error occurred.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun promptAddComment() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Enter your comment below:")
        val editText = EditText(this)
        alert.setView(editText)
        alert.setPositiveButton("Post") { _, _ -> postComment(editText.text.toString()) }
        alert.create().show()
    }
}
