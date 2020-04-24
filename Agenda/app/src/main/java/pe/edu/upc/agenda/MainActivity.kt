package pe.edu.upc.agenda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE = 1
    val UPDATE_CODE = 2
    var contacts = ArrayList<Contact>()

    // Permite cargar la información de la lista de contactos al RecyclerView
    var contactAdapter = ContactAdapter(contacts, this::modifyContact, this::deleteContact)

    fun deleteContact(contactId: String) {
        val index = contacts.indexOfFirst { x -> x.id == contactId }
        contacts.removeAt(index)
        contactAdapter.notifyItemRemoved(index)
    }

    fun modifyContact(contact: Contact) {
        val intent = Intent(this, ContactActivity::class.java)

        intent.putExtra("keyId", contact.id)
        intent.putExtra("keyName", contact.name)
        intent.putExtra("keyTelephone", contact.telephone)

        startActivityForResult(intent, UPDATE_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadContacts()
        initView()

    }

    fun initView() {
        rvContact.adapter = contactAdapter
        rvContact.layoutManager = LinearLayoutManager(this)
    }

    fun loadContacts() {
        contacts.add(Contact(UUID.randomUUID().toString(), "Jorge", "999999999"))
        contacts.add(Contact(UUID.randomUUID().toString(), "Luis", "777777777"))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, ContactActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val id = UUID.randomUUID().toString()
                    val name = data!!.getStringExtra("keyName")!!
                    val telephone = data.getStringExtra("keyTelephone")!!

                    val contact = Contact(id, name, telephone)

                    contacts.add(contact)
                    contactAdapter.notifyItemChanged(contacts.size - 1)
                }
            }
            UPDATE_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val id = data!!.getStringExtra("keyId")!!
                    val name = data.getStringExtra("keyName")!!
                    val telephone = data.getStringExtra("keyTelephone")!!

                    val index = contacts.indexOfFirst { x -> x.id == id }
                    contacts[index] = Contact(id, name, telephone)
                    contactAdapter.notifyItemChanged(index)
                }
            }
        }
    }
}
