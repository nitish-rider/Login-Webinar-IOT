package `in`.iot.lab.loginwebinar.UI

import `in`.iot.lab.loginwebinar.Models.Login
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPref: SharedPreferences =requireActivity().getSharedPreferences("Details", Context.MODE_PRIVATE)
        if(sharedPref.getString("_id","")!=""){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        lifecycleScope.launch {
            delay(2000)
        }

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login, container, false)


        email=view.findViewById<TextInputLayout>(R.id.signInEmail)
        password=view.findViewById<TextInputLayout>(R.id.signInPassword)
        val editor=sharedPref.edit()

        //ViewModel
        val repository= Repository()
        val viewModelFactory= MainViewModelFactory(repository)
        viewModel= ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

        view.findViewById<Button>(R.id.signInBt).setOnClickListener {
            if(isInputSafe(email.editText?.text.toString(),password.editText?.text.toString())){
                val login= Login(email.editText?.text.toString(),password.editText?.text.toString())

                viewModel.signIn(login)

                viewModel.myLoginResponse.observe(requireActivity(), Observer { response->
                    if(response.isSuccessful){
                        Log.d("Response", response.body().toString())
                        editor.putString("_id",response.body()!!._id)
                        editor.putString("name",response.body()!!.firstname)
                        editor.putString("email",response.body()!!.email)
                        editor.putString("token",response.body()!!.token)
                        editor.apply()
                        editor.commit()

                        if(response.code()==200){
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        else{
                            Toast.makeText(requireContext(),"Unable To Login", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }

        view.findViewById<TextView>(R.id.signInRegister).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view;
    }

    private fun isInputSafe(emailS: String, passwordS: String): Boolean {
        if(emailS == ""){
            email.error="Cannot be Empty"
            return false
        }
        if(passwordS == ""){
            password.error="Cannot be Empty"
            return false
        }
        return true
    }

}