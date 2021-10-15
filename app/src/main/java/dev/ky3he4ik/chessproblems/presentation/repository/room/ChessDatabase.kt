package dev.ky3he4ik.chessproblems.presentation.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.ky3he4ik.chessproblems.presentation.repository.converters.DataTypeConverter
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.ProblemsDAO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.UsersDAO
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(
    entities = [ProblemInfoDTO::class, UserInfoDTO::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DataTypeConverter::class)
abstract class ChessDatabase : RoomDatabase() {
    abstract fun problemsDAO(): ProblemsDAO
    abstract fun usersDAO(): UsersDAO

    companion object {
        @Volatile
        private var instance: ChessDatabase? = null

        private const val NUMBER_OF_THREADS = 4

        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getInstance(context: Context): ChessDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ChessDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ChessDatabase::class.java,
                "chess_problems_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
