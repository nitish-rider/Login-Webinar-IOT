package `in`.iot.lab.loginwebinar.UI

import `in`.iot.lab.loginwebinar.Models.Register
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.iot.lab.loginwebinar.R
import `in`.iot.lab.loginwebinar.repository.Repository
import `in`.iot.lab.loginwebinar.viewModel.MainViewModel
import `in`.iot.lab.loginwebinar.viewModel.MainViewModelFactory
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout


class RegisterFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    lateinit var firstName: TextInputLayout
    lateinit var lastName:TextInputLayout
    lateinit var email:TextInputLayout
    lateinit var password:TextInputLayout
    lateinit var confirmPassword:TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_register, container, false)

        //Variables
        firstName=view.findViewById<TextInputLayout>(R.id.registerFirstname)
        lastName=view.findViewById<TextInputLayout>(R.id.registerLastname)
        email=view.findViewById<TextInputLayout>(R.id.registerEmail)
        password=view.findViewById<TextInputLayout>(R.id.registerPassword)
        confirmPassword=view.findViewById<TextInputLayout>(R.id.registerConfPassword)

        val sharedPref: SharedPreferences =requireActivity().getSharedPreferences("Details", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()

        //ViewModel
        val repository= Repository()
        val viewModelFactory= MainViewModelFactory(repository)
        viewModel= ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

        view.findViewById<Button>(R.id.registerBt).setOnClickListener {
            if(isInputvalid(firstName.editText?.text.toString(),lastName.editText?.text.toString(),email.editText?.text.toString(),
                    password.editText?.text.toString(),confirmPassword.editText?.text.toString())){

                val register= Register(firstName.editText?.text.toString(),lastName.editText?.text.toString(),email.editText?.text.toString(),
                    password.editText?.text.toString(),confirmPassword.editText?.text.toString());


                viewModel.register(register)

                viewModel.myRegisterResponse.observe(requireActivity(), Observer {response->
                    if(response.isSuccessful){
                        Log.d("Response", response.body().toString())
                        editor.putString("_id",response.body()!!._id)
                        editor.putString("name",response.body()!!.firstname)
                        editor.putString("email",response.body()!!.email)
                        editor.putString("token",response.body()!!.token)
                        editor.apply()
                        editor.commit()

                        if(response.code()==200){
                            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                        }
                        else{
                            Toast.makeText(requireContext(),"Unable To Login",Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

            view.findViewById<TextView>(R.id.registerLogin).setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }


        }

        return view;
    }

    private fun isInputvalid(fName: String, lName: String, emailS: String, passwordS: String, confPasswordS: String): Boolean {
        if(fName == ""){
            firstName.error="Cannot be Empty"
            return false
        }
        if(lName == ""){
            lastName.error="Cannot be Empty"
            return false
        }
        if(emailS == ""){
            email.error="Cannot be Empty"
            return false
        }
        if(passwordS == ""){
            password.error="Cannot be Empty"
            return false
        }
        if(confPasswordS == ""){
            confirmPassword.error="Cannot be Empty"
            return false
        }

        return true
    }
}