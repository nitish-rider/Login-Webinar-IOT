package `in`.iot.lab.loginwebinar.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.iot.lab.loginwebinar.R
import android.content.Context
import android.content.SharedPreferences
import android.widget.TextView


class HomeFragment : Fragment() {
    lateinit var id: TextView
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var token: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        val sharedPref: SharedPreferences =requireActivity().getSharedPreferences("Details", Context.MODE_PRIVATE)
        id=view.findViewById(R.id.idText)
        name=view.findViewById(R.id.nameText)
        email=view.findViewById(R.id.emailText)
        token=view.findViewById(R.id.tokenText)
        id.setText("ID: "+sharedPref.getString("_id","-1"))
        name.setText("Name: "+sharedPref.getString("name","-1"))
        email.setText("Email: "+sharedPref.getString("email","-1"))
        token.setText("JWT Token: "+sharedPref.getString("token","-1"))

        return view;
    }

}