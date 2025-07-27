// app/src/main/java/com/uilover/project2402/Activity/Dashboard/TopBar.kt
package com.uilover.project2402.Activity.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project2402.R
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

/**
 * Composable real de la TopBar, recibe parámetros para texto y acción de búsqueda.
 */
@Composable
fun TopBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 48.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar / perfil
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .clickable { /* acción perfil */ }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Campo de búsqueda editable
        TextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,

            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search  // ← muestra el botón “Search” en el teclado
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text)                              // dispara tu búsqueda
                    /*keyboarController?.hide()*/                  // oculta el teclado
                }
            ),

            placeholder = {
                Text(
                    text = "What would you like to eat?",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            },
            trailingIcon = {
                IconButton(onClick = { onSearch(text) }) {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Search",
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(R.color.white),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.DarkGray
            ),
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .background(colorResource(R.color.grey), CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Icono de notificaciones
        Image(
            painter = painterResource(R.drawable.bell_icon),
            contentDescription = "Notifications",
            modifier = Modifier
                .size(40.dp)
                .clickable { /* acción notificaciones */ }
        )
    }
}

/**
 * Preview para TopBar en aislamiento.
 */
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    var previewText by rememberSaveable { mutableStateOf("") }
    TopBar(
        text = previewText,
        onTextChange = { previewText = it },
        onSearch = { /* no-op */ }
    )
}
