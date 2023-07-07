package com.example.skillbox_hw_quiz


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.databinding.FragmentSecondBinding
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val listQuestions = QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions
    private val listQuestView = mutableListOf<QuestView>()
    private val mapFeedback = mutableMapOf<String, Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        addQuests()
        binding.buttonSecond2.setOnClickListener {
            if (!isCheckAnswer()) {
                val bundle = Bundle().apply {
                    fragmentManager?.isDestroyed
                    putString(
                        "param1", QuizStorage.answer(
                            QuizStorage.getQuiz(QuizStorage.Locale.Ru),
                            mapFeedback.values.toList()
                        )
                    )
                }
                findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
            } else {
                Snackbar.make(
                    binding.root,
                    R.string.no_answer,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addQuests() {
        var questView: QuestView
        for (i in listQuestions) {
            questView = QuestView(context)
            questView.setTextQuest(i.question)
            mapFeedback[i.question] = -1
            questView.addRadioButton(i)
            binding.linerMain.addView(questView)
            listQuestView.add(questView)
        }
    }

    private fun isCheckAnswer(): Boolean {
        for (i in listQuestView) {
            mapFeedback[i.getTextQuest()] = i.getFeedBack()
        }
        return mapFeedback.containsValue(-1)
    }
}