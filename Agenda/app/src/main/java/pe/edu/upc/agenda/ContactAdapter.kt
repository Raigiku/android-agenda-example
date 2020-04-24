package pe.edu.upc.agenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.prototype_contact.view.*

class ContactAdapter(
    var contacts: ArrayList<Contact>,
    val modifyContact: (Contact) -> Unit,
    val deleteContact: (String) -> Unit
) : RecyclerView.Adapter<ContactPrototype>() {
    // Crea el propototipo (ViewHolder) para cada fila
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_contact, parent, false)

        return ContactPrototype(view)
    }

    // Devuelve la cantidad total de elementos
    override fun getItemCount(): Int {
        return contacts.size
    }

    // Carga la información en la vista para cada fila
    override fun onBindViewHolder(contactPrototype: ContactPrototype, position: Int) {
        contactPrototype.bind(contacts.get(position), modifyContact, deleteContact)
    }

}

class ContactPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.tvName
    val tvTelephone = itemView.tvTelephone

    fun bind(
        contact: Contact,
        modifyContact: (Contact) -> Unit,
        deleteContact: (String) -> Unit
    ) {
        tvName.text = contact.name
        tvTelephone.text = contact.telephone

        itemView.imgBtModify.setOnClickListener {
            modifyContact(contact)
        }

        itemView.imgBtDelete.setOnClickListener {
            deleteContact(contact.id)
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(contact: Contact)
}
