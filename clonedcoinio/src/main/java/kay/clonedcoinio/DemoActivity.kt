package kay.clonedcoinio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kay.clonedcoinio.group.DataProvider
import kay.clonedcoinio.group.TestAdapter
import kotlinx.android.synthetic.main.activity_demo.*


/**
 * Created by Kay Tran on 23/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        val adapter = TestAdapter()
        adapter.addData(DataProvider.getData())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

}