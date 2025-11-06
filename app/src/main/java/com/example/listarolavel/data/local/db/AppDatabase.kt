package com.example.listarolavel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.listarolavel.data.local.dao.AlunoDao
import com.example.listarolavel.data.local.entity.AlunoEntity

@Database(entities = [AlunoEntity::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alunoDao(): AlunoDao
}