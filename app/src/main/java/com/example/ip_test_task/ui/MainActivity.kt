package com.example.ip_test_task.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ip_test_task.R
import com.example.ip_test_task.model.ContentEntityUI
import com.example.ip_test_task.ui.theme.IptesttaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IptesttaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        mainViewModel = mainViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val searchedText = textState.value.text
    mainViewModel.run {
        val (uiList, redactItemIsShow) = stateUi.collectAsStateWithLifecycle().value

        redactItemIsShow?.let {
            RefactoringItem(
                item = it,
                confirm = { contentEntityUI -> mainViewModel.redactingItem(contentEntityUI) }
            ) {
                mainViewModel.closeRedactItem()
            }
        }

        Column(
            modifier = modifier
                .safeDrawingPadding()
        ) {
            SearchBar(textState)

            LazyColumn {
                items(uiList.filter { it.name.contains(searchedText, ignoreCase = true) }) {

                    CardItem(
                        it,
                        { contentEntityUI -> callRedactItem(contentEntityUI) }
                    ) { id: Long -> removeItem(id) }
                }
            }
        }
    }
}

@Composable
fun RefactoringItem(
    item: ContentEntityUI,
    confirm: (ContentEntityUI) -> Unit,
    onDismissAlert: () -> Unit
) {
    item.run {
        val count = remember {
            mutableLongStateOf(amount)
        }
        Dialog(onDismissRequest = { onDismissAlert() }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(25.dp, 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    painterResource(
                        R.drawable.baseline_build_24
                    ),
                    tint = Color.DarkGray,
                    contentDescription = name,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )

                Text(stringResource(R.string.count_items), fontSize = 24.sp, fontFamily = FontFamily.Serif)

                Row(
                    modifier = Modifier
                        .padding(0.dp, 15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    count.run {
                        IconButton(
                            onClick = {
                                if (longValue != (0).toLong())
                                    longValue--
                            }
                        ) {
                            Icon(
                                painterResource(
                                    R.drawable.baseline_arrow_back_ios_24
                                ),
                                tint = Color.Blue,
                                contentDescription = name
                            )
                        }

                        Text(
                            modifier = Modifier.padding(5.dp, 0.dp),
                            text = longValue.toString(),
                            fontSize = 22.sp
                        )

                        IconButton(
                            onClick = {
                                longValue++
                            }
                        ) {
                            Icon(
                                painterResource(
                                    R.drawable.baseline_arrow_forward_ios_24
                                ),
                                tint = Color.Blue,
                                contentDescription = name
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { onDismissAlert() }
                    ) {
                        Text(stringResource(R.string.cancel_text))
                    }

                    TextButton(
                        onClick = {
                            confirm(
                                copy(
                                    amount = count.longValue
                                )
                            )
                        }
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                }

            }
        }
    }
}

@Composable
fun SearchBar(textFiledList: MutableState<TextFieldValue>) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = textFiledList.value,
        onValueChange = { value ->
            textFiledList.value = value
        },
        label = {
            Text(stringResource(R.string.seek_item))
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardItem(
    item: ContentEntityUI,
    redactorItem: (ContentEntityUI) -> Unit,
    deleteItem: (Long) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
    val spaceBetween = Arrangement.SpaceBetween
    item.run {
        val lightGray = Color.LightGray
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            colors = CardColors(Color.White, Color.Black, lightGray, lightGray)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp, 5.dp)
            ) {
                Row(
                    modifier = modifier,
                    horizontalArrangement = spaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        fontSize = 18.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(
                            onClick = {
                                redactorItem(this@run)
                            },
                        ) {
                            Icon(
                                painterResource(R.drawable.baseline_create_24),
                                tint = Color.Blue,
                                contentDescription = stringResource(R.string.redactring_count_item)
                            )
                        }

                        IconButton(
                            onClick = {
                                deleteItem(id)
                            },
                        ) {
                            Icon(
                                painterResource(R.drawable.baseline_backspace_24),
                                tint = Color.Red,
                                contentDescription = stringResource(R.string.redactring_count_item)
                            )
                        }
                    }
                }

                tags.apply {
                    if (isNotEmpty())
                        FlowRow(
                            modifier = Modifier.wrapContentSize(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            forEach {
                                FilterChip(
                                    onClick = {},
                                    label = { Text(it) },
                                    selected = false
                                )
                            }
                        }
                }

                Row(
                    modifier = modifier,
                    horizontalArrangement = spaceBetween
                ) {
                    Text(stringResource(R.string.on_a_warehous))
                    Text(stringResource(R.string.date_add))
                }

                Row(
                    modifier = modifier,
                    horizontalArrangement = spaceBetween
                ) {
                    Text(amount.toString())
                    Text(time)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    /*IptesttaskTheme {
        Greeting(mainViewModel = viewModel())
    }*/
}
