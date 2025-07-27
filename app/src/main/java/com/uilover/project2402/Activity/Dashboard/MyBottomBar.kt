package com.uilover.project2402.Activity.Dashboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uilover.project2402.Activity.Dashboard.BottomMenuItem
import com.uilover.project2402.Activity.Dashboard.prepareBottomMenu
import com.uilover.project2402.R

@Composable
@Preview
fun MyBottomBar() {
    val bottomMenuItemsList = prepareBottomMenu()
    val context= LocalContext.current
    var selectedItem by remember { mutableStateOf("Home") }

    BottomAppBar (
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        backgroundColor = colorResource(R.color.white),
        elevation = 3.dp
    ){
        bottomMenuItemsList.forEach { bottomMenuItem->
            BottomNavigationItem(
                selected = (selectedItem==bottomMenuItem.lable),
                onClick = {

                }, icon = {
                    Icon(
                        painter =bottomMenuItem.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top =8.dp)
                            .size(20.dp)
                    )
                }
            )

        }
    }


}

data class BottomMenuItem(
    val lable: String, val icon: Painter
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()
    bottomMenuItemList.add(BottomMenuItem(lable = "Home", icon = painterResource(R.drawable.btn_1)))
    bottomMenuItemList.add(BottomMenuItem(lable = "Cart", icon = painterResource(R.drawable.btn_2)))
    bottomMenuItemList.add(
        BottomMenuItem(
            lable = "Favorite",
            icon = painterResource(R.drawable.btn_3)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            lable = "Order",
            icon = painterResource(R.drawable.btn_4)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            lable = "Profile",
            icon = painterResource(R.drawable.btn_5)
        )
    )

    return bottomMenuItemList
}