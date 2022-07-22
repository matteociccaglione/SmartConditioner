package it.smarting.smartconditioner.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import it.smarting.smartconditioner.databinding.ChangeNameDialogBinding

/**
 * This class is a DialogFragment subclass and it allows the user to specify a
 * directory from a directory listing or create a new one and assign a title
 * @author Matteo Ciccaglione
 */
class RenameRoomDialog: DialogFragment() {
    private lateinit var binding: ChangeNameDialogBinding
    private lateinit var directorySelected : String
    private var directoryList : MutableList<String> = mutableListOf()
    private var directoryListener: (name: String) -> Unit ={  _: String ->

    }
    var isDirectoryShowed : Boolean = true

    private var cancelListener: ()-> Boolean ={
       true
    }
    companion object{
        fun getInstance(): RenameRoomDialog{
            return RenameRoomDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChangeNameDialogBinding.inflate(inflater)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonOk.setOnClickListener{
            if(binding.editTextTextPersonName.text.isEmpty()){
                return@setOnClickListener
            }
            else {
                val text = binding.editTextTextPersonName.text.toString()
                directoryListener(text)
                dismiss()
            }
        }
        binding.buttonCanc.setOnClickListener{
            if(cancelListener()){
                dismiss()
            }
        }
    }

    /**
     * Set up a directory listener to handle on OK button pressed
     */
    fun setOnOkPressed(listener: (name:String) -> Unit){
        directoryListener=listener
    }
    /**
     * Set up a cancel listener to handle on cancel button pressed
     * The listener must return true if the dialog must be closed
     */
    fun setOnCancelListener(listener: ()-> Boolean){
        cancelListener=listener
    }
    fun setDirectoryList(list: List<String>){
       directoryList.clear()
        directoryList.addAll(list)
    }
}