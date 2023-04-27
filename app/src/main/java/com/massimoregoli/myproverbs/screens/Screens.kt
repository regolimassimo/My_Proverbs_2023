package com.massimoregoli.myproverbs.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.massimoregoli.myproverbs.R
import androidx.compose.ui.text.font.Font


@Composable
fun MainView(
    id: Int, text: String, favorite: Int, onlyFavorite: Int,
    onClickOnlyFavorite: (Boolean) -> Unit,
    onClick: () -> Unit,
    onClickFavorite: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Proverb no. $id",
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                color = Color.Red,
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontFamily = fontFamily()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.show_fav),
                    fontFamily = fontFamily()
                )
                Checkbox(checked = onlyFavorite == 1, onCheckedChange = { onClickOnlyFavorite(it) })
            }
        }
        Text(text = if (text == "") stringResource(R.string.click_here) else text,
            modifier = Modifier
                .padding(4.dp)
                .border(2.dp, Color.Blue, RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(8.dp),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontFamily = fontFamily())
        IconButton(onClick = onClickFavorite) {
            Icon(Icons.TwoTone.ThumbUp, null, tint = if (favorite == 1) Color.Red else Color.Blue)
        }
    }
}

@Composable
fun fontFamily(): FontFamily {
    val assets = LocalContext.current.assets
    return FontFamily(
        Font("Caveat.ttf", assets)
    )
}
