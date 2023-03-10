package com.skrninja.ui.components.selectlanguage

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import java.util.*

class LocaleHelper {

    fun setLocale(context: Context?, language: String?): Context? {
        persist(context, language)

        // updating the language for devices above android nougat
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
        // for devices having lower version of android os
    }

    private fun updateResourcesLegacy(context: Context?, language: String?): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources: Resources = context!!.resources

        val configuration: Configuration = resources.getConfiguration()
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics())

        return context
    }

    private fun updateResources(context: Context?, language: String?): Context? {
        var locale = Locale(language);
        Locale.setDefault(locale);

        var configuration = context?.getResources()?.getConfiguration();
        configuration?.setLocale(locale);
        configuration?.setLayoutDirection(locale);


        return context?.createConfigurationContext(configuration!!)
    }

    private fun persist(context: Context?, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
        editor.commit()
    }

    companion object {
        const val SELECTED_LANGUAGE: String = "Locale.Helper.Selected.Language"
    }
}