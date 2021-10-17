package dev.ky3he4ik.chessproblems.presentation.repository.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import java.lang.reflect.Type
import java.util.*

object DataTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToProblemMoves(data: String?): List<ProblemMove> {
        data ?: return Collections.emptyList()
        val listType: Type = object : TypeToken<List<ProblemMove?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun problemMovesToString(someObjects: List<ProblemMove>): String = gson.toJson(someObjects)

    @TypeConverter
    fun stringToStringArray(data: String?): List<String> {
        data ?: return Collections.emptyList()
        val listType: Type = object : TypeToken<List<String>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun stringArrayToString(someObjects: List<String>): String = gson.toJson(someObjects)

    @TypeConverter
    fun stringToFigurePositions(data: String?): List<FigurePosition> {
        data ?: return Collections.emptyList()
        val listType: Type = object : TypeToken<List<FigurePosition?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun figurePositionsToString(someObjects: List<FigurePosition>): String =
        gson.toJson(someObjects)

    @TypeConverter
    fun stringToSolvedProblems(data: String?): List<SolvedProblem> {
        data ?: return Collections.emptyList()
        val listType: Type = object : TypeToken<List<SolvedProblem?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun solvedProblemToString(someObjects: List<SolvedProblem>): String = gson.toJson(someObjects)
}
