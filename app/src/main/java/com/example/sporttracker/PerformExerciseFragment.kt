package com.example.sporttracker.ui.perform_exercise

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sporttracker.R
import com.example.sporttracker.models.exerciseResult.ExerciseResult
import com.example.sporttracker.models.exerciseResult.ExerciseResultViewModel
import com.example.sporttracker.ui.WelcomeFragment
import com.example.sporttracker.utils.SharedPreferencesManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PerformExerciseFragment : Fragment() {

    private val exerciseResultViewModel: ExerciseResultViewModel by viewModels()
    private lateinit var editTextResult: EditText
    private lateinit var textViewSelectedDate: TextView
    private lateinit var buttonSelectDate: Button
    private lateinit var buttonSave: Button
    private var selectedDate: Calendar = Calendar.getInstance()
    private var exerciseId: Int = 0
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_perform_exercise, container, false)

        editTextResult = view.findViewById(R.id.editTextExerciseResult)
        textViewSelectedDate = view.findViewById(R.id.textViewSelectedDate)
        buttonSelectDate = view.findViewById(R.id.buttonSelectDate)
        buttonSave = view.findViewById(R.id.buttonSaveExercise)

        // Получаем userId из SharedPreferences
        val sharedPrefs = SharedPreferencesManager(requireContext())
        userId = WelcomeFragment.GlobalVariables.userId

        // Получаем exerciseId из аргументов
        exerciseId = arguments?.getInt("exerciseId") ?: 0

        // Устанавливаем текущую дату по умолчанию
        updateDateInView()

        // Обработчик выбора даты
        buttonSelectDate.setOnClickListener {
            showDatePickerDialog()
        }
        // Обработчик сохранения результата
        buttonSave.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                saveExerciseResult()
            }
        }

        return view
    }

    private fun showDatePickerDialog() {

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun updateDateInView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        textViewSelectedDate.text = dateFormat.format(selectedDate.time)
    }

    private suspend fun saveExerciseResult() {
        val resultText = editTextResult.text.toString().trim()

        if (resultText.isEmpty()) {
            Toast.makeText(requireContext(), "Введите результат упражнения", Toast.LENGTH_SHORT).show()
            return
        }

        val exerciseResult = ExerciseResult(
            userId = userId,
            exerciseId = exerciseId,
            result = resultText,
            date = selectedDate.time.toString()
        )

        exerciseResultViewModel.saveResult(exerciseResult)

        Toast.makeText(requireContext(), "Результат сохранен!", Toast.LENGTH_SHORT).show()

        findNavController().navigateUp()
    }
}
