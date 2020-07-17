package com.company.museumquests

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
class QuestListAdapter(private val context: Activity, private val title: Array<String>, private val description: Array<String>)
    : ArrayAdapter<String>(context, R.layout.quest_list_element, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.quest_list_element, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView

        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]

        subtitleText.text = description[position]

        return rowView
    }
}