package com.skrninja.ui.components.selectlanguage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skrninja.mvvm.R

class SelectLanguageActivity : AppCompatActivity() {

    private lateinit var mAdapter: LanguageAdapter
    var mList: ArrayList<Language>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)

        val recyclerView: RecyclerView = findViewById(R.id.recyv_lang)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mList?.add(Language("English"))
        mList?.add(Language("Hindi"))

mAdapter = LanguageAdapter(mList!!)
        recyclerView.adapter= mAdapter

        mAdapter.setOnItemClickListener(object : LanguageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                if(mList?.get(position)== mList!![0]){
LocaleHelper().setLocale(this@SelectLanguageActivity,"en")
                }
                if (mList?.get(position)== mList!![1]){
                    LocaleHelper().setLocale(this@SelectLanguageActivity,"hi")
                }
            }

        })
    }
}