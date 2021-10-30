package dev.ky3he4ik.chessproblems.presentation.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.ky3he4ik.chessproblems.presentation.repository.converters.DataTypeConverter
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.FigurePositionDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemMoveDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.SolvedProblemDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserTokensDTO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.ProblemsDAO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.UsersDAO
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(
    entities = [ProblemDTO::class, FigurePositionDTO::class, ProblemMoveDTO::class,
        UserDTO::class, SolvedProblemDTO::class, UserTokensDTO::class],
    version = 5,
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
//        val migration_2_3 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//                val cursor = database.query("SELECT problem_id, moves FROM problem_info")
//                val hasData = cursor.moveToFirst()
//                if (!hasData)
//                    return
//                val movesColumn = cursor.getColumnIndex("moves")
//                val idColumn = cursor.getColumnIndex("problem_id")
//                do {
//                    val moves = DataTypeConverter.stringToProblemMoves(cursor.getString(movesColumn))
//                    val problem_id = cursor.getInt(idColumn)
//                    val moves_arr = moves.map { "${it.posStart}-${it.posEnd}"}
//                    val moves_str = DataTypeConverter.stringArrayToString(moves_arr)
//                    database.execSQL("UPDATE problem_info SET moves='${moves_str}' WHERE problem_id=${problem_id}")
//                } while (cursor.moveToNext())
//            }
//        }

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
//                .addMigrations(migration_2_3)
                .build()
        }
    }
}
