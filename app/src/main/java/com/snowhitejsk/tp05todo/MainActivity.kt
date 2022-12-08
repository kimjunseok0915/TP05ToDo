package com.snowhitejsk.tp05todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.snowhitejsk.tp05todo.TodoActivity
import android.content.SharedPreferences
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.view.View
import com.bumptech.glide.Glide
import com.snowhitejsk.tp05todo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.includeCategoryAll.root.setOnClickListener { v: View? -> clickedCategory(0) }
        binding.includeCategoryWork.root.setOnClickListener { v: View? -> clickedCategory(1) }
        binding.includeCategoryStudy.root.setOnClickListener { v: View? -> clickedCategory(2) }
        binding.includeCategoryHealth.root.setOnClickListener { v: View? -> clickedCategory(3) }
        binding.includeCategoryHobby.root.setOnClickListener { v: View? -> clickedCategory(4) }
        binding.includeCategoryMeeting.root.setOnClickListener { v: View? -> clickedCategory(5) }
        binding.includeCategoryEtc.root.setOnClickListener { v: View? -> clickedCategory(6) }
        binding.includeCategoryDone.root.setOnClickListener { v: View? -> clickedCategory(7) }
        loadData() // SharedPreferences에 저장된 프로필이미지, 이름을 가져와서 뷰에 보여주는 기능호출
    }

    fun clickedCategory(category: Int) {
        val intent = Intent(this, TodoActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

    fun loadData() {
        val pref = getSharedPreferences("account", MODE_PRIVATE)
        val profile = pref.getString("profile", "")
        val name = pref.getString("name", "")
        binding!!.tvName.text = "안녕하세요. $name 님"
        Glide.with(this).load(profile).error(R.drawable.profle).into(binding!!.civProfile)
    }

    override fun onResume() {
        super.onResume()
        loadDatabaseAndUiUpdate()
    }

    private fun loadDatabaseAndUiUpdate(){
        // Database "Todo.db"파일 안에 있는 todo 테이블의 카테고리별 개수 가져오기

        val db:SQLiteDatabase= openOrCreateDatabase("Todo", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS todo(num INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, category INTEGER, note TEXT, isDone INTEGER)")

        var countAll:Long = DatabaseUtils.longForQuery(db,"SELECT COUNT(*) FROM todo WHERE isDone=0", null)
        var countWork:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0 and category=?", arrayOf("1"))
        var countStudy:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0 and category=?", arrayOf("2"))
        var countHealth:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0 and category=?", arrayOf("3"))
        var countHobby:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0 and category=?", arrayOf("4"))
        var countMeeting:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0 and category=?", arrayOf("5"))
        var countEtc:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=0 and category=?", arrayOf("6"))
        var countDone:Long = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo WHERE isDone=1", null)


        binding.includeCategoryAll.tvCategoryAllNum.text= countAll.toString()
        binding.includeCategoryWork.tvNum.text= countWork.toString()
        binding.includeCategoryStudy.tvNum.text= countStudy.toString()
        binding.includeCategoryHealth.tvNum.text= countHealth.toString()
        binding.includeCategoryHobby.tvNum.text= countHobby.toString()
        binding.includeCategoryMeeting.tvNum.text= countMeeting.toString()
        binding.includeCategoryEtc.tvNum.text= countEtc.toString()
        binding.includeCategoryDone.tvNum.text= countDone.toString()
    }

}