
package com.stalkergram.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val green = Color.rgb(32, 170, 85)
    private val background = Color.WHITE
    private val dark = Color.rgb(35, 40, 45)
    private val gray = Color.rgb(130, 135, 140)

    private lateinit var content: LinearLayout
    private lateinit var title: TextView

    private var currentTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showApp()
    }

    private fun showApp() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(background)
        }

        title = TextView(this).apply {
            text = "StalkerGram"
            textSize = 27f
            setTextColor(green)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(20, 25, 20, 18)
        }

        root.addView(title)

        val search = EditText(this).apply {
            hint = "Поиск"
            textSize = 16f
            setSingleLine()
            setPadding(18, 12, 18, 12)
            background = rounded(Color.rgb(242, 244, 246), 18)
        }

        val searchParams = LinearLayout.LayoutParams(
            -1, -2
        ).apply {
            setMargins(16, 0, 16, 15)
        }

        root.addView(search, searchParams)

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 8, 16, 8)
        }

        root.addView(
            content,
            LinearLayout.LayoutParams(0, 0, 1f)
        )

        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setBackgroundColor(Color.WHITE)
            elevation = 12f
        }

        val names = listOf("Чаты", "Контакты", "Настройки", "Профиль")
        val icons = listOf("💬", "👥", "⚙️", "👤")

        names.forEachIndexed { index, name ->
            val button = TextView(this).apply {
                text = "${icons[index]}\n$name"
                textSize = 12f
                gravity = Gravity.CENTER
                setPadding(2, 12, 2, 12)
                setTextColor(if (index == 0) green else gray)
                setOnClickListener {
                    currentTab = index
                    updateScreen()
                    for (i in 0 until bottom.childCount) {
                        (bottom.getChildAt(i) as TextView)
                            .setTextColor(if (i == index) green else gray)
                    }
                }
            }

            bottom.addView(
                button,
                LinearLayout.LayoutParams(0, -2, 1f)
            )
        }

        root.addView(bottom)
        setContentView(root)

        updateScreen()
    }

    private fun updateScreen() {
        content.removeAllViews()

        title.text = when (currentTab) {
            0 -> "StalkerGram"
            1 -> "Контакты"
            2 -> "Настройки"
            else -> "Профиль"
        }

        when (currentTab) {
            0 -> showChats()
            1 -> showContacts()
            2 -> showSettings()
            3 -> showProfile()
        }
    }

    private fun addItem(icon: String, name: String, description: String = "") {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(12, 15, 12, 15)
            background = rounded(Color.rgb(247, 248, 249), 16)
        }

        val avatar = TextView(this).apply {
            text = icon
            textSize = 25f
            gravity = Gravity.CENTER
        }

        row.addView(avatar, LinearLayout.LayoutParams(55, 55))

        val texts = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(12, 0, 0, 0)
        }

        val nameView = TextView(this).apply {
            text = name
            textSize = 17f
            setTextColor(dark)
            typeface = Typeface.DEFAULT_BOLD
        }

        texts.addView(nameView)

        if (description.isNotEmpty()) {
            val desc = TextView(this).apply {
                text = description
                textSize = 13f
                setTextColor(gray)
            }
            texts.addView(desc)
        }

        row.addView(texts)

        val params = LinearLayout.LayoutParams(-1, -2).apply {
            bottomMargin = 9
        }

        content.addView(row, params)
    }

    private fun showChats() {
        addItem("👤", "Добро пожаловать!", "Твой новый мессенджер")
        addItem("💬", "Мои чаты", "Здесь будут твои переписки")
    }

    private fun showContacts() {
        addItem("👥", "Контакты", "Твои друзья в StalkerGram")
        addItem("🔍", "Найти пользователя", "Поиск по имени")
    }

    private fun showSettings() {
        addItem("👤", "Аккаунт", "Настройки аккаунта")
        addItem("💬", "Настройки чатов")
        addItem("🔒", "Конфиденциальность")
        addItem("🔔", "Уведомления")
        addItem("💾", "Данные и память")
        addItem("📱", "Устройства")
        addItem("🎨", "Оформление")
    }

    private fun showProfile() {
        addItem("🟢", "Мой профиль", "StalkerGram")
        addItem("📷", "Фотография профиля")
        addItem("✏️", "Изменить имя")
        addItem("ℹ️", "О себе")
    }

    private fun rounded(color: Int, radius: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = radius.toFloat()
        }
    }
}
