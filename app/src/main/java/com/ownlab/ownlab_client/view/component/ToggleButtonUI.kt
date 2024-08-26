package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FiveToggleButtonUI(
    firstOption: String,
    secondOption: String,
    thirdOption: String,
    fourthOption: String,
    fifthOption: String,
    onOptionSelected: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(firstOption) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OptionItem(firstOption, selectedOption) {
                selectedOption = firstOption
                onOptionSelected(firstOption)
            }
            OptionItem(secondOption, selectedOption) {
                selectedOption = secondOption
                onOptionSelected(secondOption)
            }
            OptionItem(thirdOption, selectedOption) {
                selectedOption = thirdOption
                onOptionSelected(thirdOption)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OptionItem(fourthOption, selectedOption) {
                selectedOption = fourthOption
                onOptionSelected(fourthOption)
            }
            OptionItem(fifthOption, selectedOption) {
                selectedOption = fifthOption
                onOptionSelected(fifthOption)
            }
        }
    }
}

@Composable
fun OptionItem(option: String, selectedOption: String, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            //.weight(1f)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = onSelect,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Blue
                    )
                )
            }
            Text(text = option, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun ToggleButtonUI(
    firstOption: String,
    secondOption: String,
    onOptionSelected: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(firstOption) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(32.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        RadioButton(
                            selected = selectedOption == firstOption,
                            onClick = {
                                selectedOption = firstOption
                                onOptionSelected(firstOption)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Blue
                            )
                        )
                    }
                    Text(text = firstOption, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        RadioButton(
                            selected = selectedOption == secondOption,
                            onClick = {
                                selectedOption = secondOption
                                onOptionSelected(secondOption)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Blue
                            )
                        )
                    }
                    Text(text = secondOption, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}


@Composable
fun ToggleButtonStateUI(
    firstOption: String,
    secondOption: String,
    toggleState: MutableState<Boolean>
) {
    val selectedOption = if (toggleState.value) firstOption else secondOption

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(32.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        RadioButton(
                            selected = selectedOption == firstOption,
                            onClick = { toggleState.value = true },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Blue
                            )
                        )
                    }
                    Text(text = firstOption, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        RadioButton(
                            selected = selectedOption == secondOption,
                            onClick = { toggleState.value = false },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Blue
                            )
                        )
                    }
                    Text(text = secondOption, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}