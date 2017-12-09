package com.itheima.studentinfo.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.studentinfo.db.StudentDBOpenHelper;
import com.itheima.studentinfo.domain.StudentInfo;

/**
 * 学生信息的dao代码：data access object
 * 提供增删改查
 */
public class StudentDao {

	private StudentDBOpenHelper helper;
	/**
	 * 没有无参的构造方法，只能用下面的构造方法去初始化dao
	 * @param context
	 */
	public StudentDao(Context context) {
		helper = new StudentDBOpenHelper(context);
	}
	/**
	 * 添加一个学生信息
	 * @param name 姓名
	 * @param phone 电话
	 * @return 添加在数据库的第几行
	 */
	public long add(String name,String phone){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("phone", phone);
		long rowid = db.insert("info", null, values);
		db.close();
		return rowid;
	}
	/**
	 * 删除一条学生信息
	 * @param id 学生id
	 * @return 影响了几行，0代表删除失败
	 */
	public int delete(String id){
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("info", "_id=?", new String[]{id});
		db.close();
		return result;
	}

	/**
	 * 获取所有的学生信息
	 * @return
	 */
	public List<StudentInfo> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<StudentInfo> infos = new ArrayList<StudentInfo>();
		Cursor cursor = db.query("info", new String[]{"_id","name","phone"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			StudentInfo info = new StudentInfo();
			String id = cursor.getString(0);
			String name = cursor.getString(1);
			String phone = cursor.getString(2);
			info.setId(id);
			info.setName(name);
			info.setPhone(phone);
			infos.add(info);
		}
		cursor.close();
		db.close();
		return infos;
	}

}