package com.osamaaftab.filtering.ui.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.osamaaftab.filtering.contractor.UserListContractor
import com.osamaaftab.filtering.di.component.DaggerMainActivityComponents
import com.osamaaftab.filtering.di.module.ContextModule
import com.osamaaftab.filtering.di.module.presenter.UserListPresenterModule
import com.osamaaftab.filtering.presenter.UserListPresenter
import com.osamaaftab.filtering.ui.model.UserData
import com.osamaaftab.filtering.ui.adapter.UserListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_dialog.*
import javax.inject.Inject
import com.osamaaftab.filtering.R


class MainActivity : AppCompatActivity(),
    UserListContractor.IView,
    View.OnClickListener {


    override fun onCreateDialog() {
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.layout_dialog)
    }

    override fun onHideProgress() {
        progress!!.dismiss()
    }

    override fun onShowProgress() {
        progress!!.show()
    }

    override fun onHideDialog() {
        dialog!!.dismiss()
    }

    @Inject
    @JvmField
    var userListPresenter: UserListPresenter? = null

    @Inject
    @JvmField
    var adapter: UserListAdapter? = null


    @Inject
    @JvmField
    var progress: ProgressDialog? = null

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return userListPresenter
    }

    @Inject
    @JvmField
    var dialog: Dialog? = null

    override fun onShowNoInternetError() {
        Toast.makeText(this, getString(R.string.text_network_error), Toast.LENGTH_LONG).show()
    }

    override fun onsetUserListAdapter(list: ArrayList<UserData>) {
        userList.layoutManager = LinearLayoutManager(this)
        adapter!!.addList(list)
        userList.adapter = adapter
    }

    override fun onUpdateUserListAdapter(list: ArrayList<UserData>) {
        adapter!!.updateList(list)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = getString(R.string.text_toolbar_title)
        DaggerMainActivityComponents.builder()
            .userListPresenterModule(UserListPresenterModule(this))
            .contextModule(ContextModule(this))
            .build()
            .inject(this)
        userListPresenter!!.onFetchUserList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                userListPresenter!!.onAttemptFilterButton()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewDialog() {
        dialog!!.show()
        dialog!!.reset.setOnClickListener(this)
        dialog!!.apply.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.reset -> {
                userListPresenter!!.onResetFilter()
            }
            R.id.apply -> {
                userListPresenter!!.onApplyFilter(
                    dialog!!.has_photo_switch.isChecked,
                    dialog!!.in_contact_switch.isChecked,
                    dialog!!.in_fav_switch.isChecked,
                    dialog!!.score_range_seek.selectedMinValue.toString(),
                    dialog!!.score_range_seek.selectedMaxValue.toString(),
                    dialog!!.age_range_seek.selectedMinValue.toString(),
                    dialog!!.age_range_seek.selectedMaxValue.toString(),
                    dialog!!.height_range_seek.selectedMinValue.toString(),
                    dialog!!.height_range_seek.selectedMaxValue.toString(),
                    dialog!!.distance.progress.toString()
                )
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        userListPresenter!!.onDistroy()
    }
}
