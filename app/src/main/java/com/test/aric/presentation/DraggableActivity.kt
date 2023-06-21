package com.test.aric.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.test.aric.presentation.ui.theme.GithubTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

class DraggableActivity : ComponentActivity() {
    val maxHeight = 200f
    val minHeight = 60f

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DraggableBoxExample()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SwipeableSample(

) {

    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) {
        96.dp.roundToPx()
    }

    val anchors = mapOf(
        0f to 0,
        sizePx.toFloat() * 0.5f to 1,
        (sizePx * 1.5f).toFloat() to 2,
        (sizePx * 2).toFloat() to 3,
        (sizePx * 2.5).toFloat() to 4,
        (sizePx * 3).toFloat() to 5
    )

    Box {

        Row() {
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.White)
            )
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.Cyan)
            )
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.White)
            )
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.Cyan)
            )
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.White)
            )
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.Cyan)
            )
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.White)
            )
            Box(
                Modifier
                    .size(squareSize * 2)
                    .background(Color.Cyan)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Box(modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .size(squareSize)
                .background(
                    Color.Red
                )
            )

            Text(text = "${swipeableState.currentValue}")
        }

    }
}


@Preview
@Composable
fun DraggableBoxExample(

) {
    val configuration = LocalConfiguration.current
    val itemWith = if (configuration.screenWidthDp.dp / 4<configuration.screenHeightDp.dp / 6){
        configuration.screenWidthDp.dp / 4
    }else{
        configuration.screenHeightDp.dp / 6
    }


    var text by remember { mutableStateOf("24") }

    var initialValue = remember { mutableStateOf(1) }

    var count = remember { mutableStateOf(0) }


    var showDialog by remember { mutableStateOf(false) }

    var stop = remember { mutableStateOf(false) }



    LaunchedEffect(stop.value) {
        while (!stop.value) {
            delay(1000)
            count.value = count.value + 1
        }
    }

    var range = remember(text) {
        if (text.isNotEmpty()) {
            mutableStateOf((1..text.toInt()).shuffled())
        } else {
            mutableStateOf((1..24).shuffled())
        }
    }

    Column(){

        FlowRow (modifier = Modifier.fillMaxWidth(),crossAxisAlignment= FlowCrossAxisAlignment.Center,mainAxisAlignment =FlowMainAxisAlignment.Center){
            range.value.forEach { it ->
                DraggableBox(
                    { if (it != initialValue.value){
                        showDialog = true
                    }else{
                        initialValue.value = it +1

                        if(it == 24){
                            stop.value =true
                            showDialog = true
                        }
                    } },
                    it,
                    itemWith,
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(color = Color(0xffffb6c1),
                text =
                if (count.value >= 60) {
                    "${count.value.div(60)} 分${count.value.mod(60)} 秒"
                } else {
                    "${count.value} 秒"
                }

            )

            Text(color = Color(0xffffb6c1),
                text = "已经到：${ initialValue.value -1}"
            )
            Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffffb6c1)),onClick = {
                stop.value = false
                range.value = range.value.shuffled()
                count.value = 0
                initialValue.value = 1

            }) {
                Text(color = Color.White, text = "重置")
            }
        }

        if(showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false }
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(10.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(color=Color(0xffffb6c1),text =if ( initialValue.value == 25){
                            val time = count.value
                            "恭喜周念初，你成功了，用时 ${
                                if (time >= 60) {
                                    "${time/ 60} 分${time.mod(60)} 秒"
                                } else {
                                    "${time} 秒"
                                }
                            }，按下重置键可以继续下一轮哦～"
                        }else {
                            "周念初，你点错了,应该点：${initialValue.value}"
                        }, textAlign = TextAlign.Center)
                    }
                }
            }
    }}
}


@Composable
fun DraggableBox(
    onClick: (Int) -> Unit,
    index: Int,
    itemWith: Dp,
) {

    LocalWindowInfo.current

    val d = LocalDensity.current
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var borderColor by remember { mutableStateOf(Color(0XFFb6c1)) }

    Box(modifier = Modifier
        .offset(x = (offsetX / d.density).dp, y = (offsetY / d.density).dp)
        .size(itemWith)
        .clickable {
            onClick(index)
        }
        .padding(1.dp)
        .border(2.dp, borderColor, shape = RoundedCornerShape(4.dp))
        .background(Color(0xffffb6c1))
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$index", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 24.sp)
    }

}
