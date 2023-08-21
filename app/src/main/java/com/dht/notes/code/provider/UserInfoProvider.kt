//package com.dht.notes.code.provider
//
//import android.content.*
//import android.database.Cursor
//import android.database.SQLException
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import android.net.Uri
//import android.os.Build
//import com.aiforcetech.common.*
//import com.aiforcetech.ydlog.logd
//
//
///**
// * @author dht
// * @date 2023/8/10 17:43
// **/
//
//class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    companion object {
//        private const val DATABASE_NAME = "shareInfo.db"
//        private const val DATABASE_VERSION = 1
//    }
//
//    override fun onCreate(db: SQLiteDatabase) {
//        try {
//            logd { "onCreate sql $sql" }
//            db.execSQL(sql)
//        } catch (e: SQLException) {
//            e.printStackTrace()
//            logd { "e $e" }
//        }
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//    }
//}
//
//class UserInfoProvider : ContentProvider() {
//
//    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//
//    private var db: SQLiteDatabase? = null
//
//    override fun onCreate(): Boolean {
//        matcher.addURI(AUTOHORITY, TABLE_NAME, ITEM)
//        matcher.addURI(AUTOHORITY, "$TABLE_NAME/#", ITEM_ID)
//        db = context?.let { DbHelper(it).readableDatabase }
//
//        logd { "onCreate(), db?.path ${db?.path}" }
//
//        return true
//    }
//
//    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?,
//        sortOrder: String?): Cursor? {
//
//        when (matcher.match(uri)) {
//            ITEM -> return db?.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)?.apply {
//                setNotificationUri(context?.contentResolver, uri)
//            }
//
//            ITEM_ID -> return db?.query(TABLE_NAME, projection, COLUMN_ID + "=" + uri.getLastPathSegment(),
//                selectionArgs, null, null, sortOrder)?.apply {
//                setNotificationUri(context?.contentResolver, uri)
//            }
//        }
//        return null
//
//    }
//
//    override fun getType(uri: Uri): String? {
//        return null
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        if (matcher.match(uri) == ITEM) {
//            try {
//                db?.insert(TABLE_NAME, null, values)?.let {
//                    if (it > 0) {
//                        val noteUri = ContentUris.withAppendedId(CONTENT_URI, it)
//                        context?.contentResolver?.notifyChange(noteUri, null)
//                        return noteUri
//                    }
//
//                }
//            } catch (e: SQLException) {
//                e.printStackTrace()
//                logd { "insert(), e $e" }
//            }
//        }
//        return null
//    }
//
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
//        if (matcher.match(uri) == ITEM) {
//            try {
//                db?.execSQL("delete from $TABLE_NAME")
//            } catch (e: SQLException) {
//                e.printStackTrace()
//                logd { "delete(), e $e" }
//            }
//        }
//        return 0
//    }
//
//    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
//        return 0
//    }
//}