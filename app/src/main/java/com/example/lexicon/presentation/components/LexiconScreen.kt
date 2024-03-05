package com.example.lexicon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lexicon.R
import com.example.lexicon.domain.model.Meaning
import com.example.lexicon.domain.model.WordItem
import com.example.lexicon.presentation.MainState
import com.example.lexicon.presentation.MainUiEvents
import com.example.lexicon.ui.theme.LexiconTheme

@Composable
fun LexiconScreen(
    modifier: Modifier = Modifier,
    state: MainState,
    onEvent: (MainUiEvents) -> Unit
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            OutlinedTextField(
                value = state.searchWord,
                onValueChange = {
                    onEvent(MainUiEvents.OnSearchWordChange(it))
                },
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                maxLines = 1,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search_icon)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                label = {
                    Text(
                        modifier = modifier.alpha(0.7f),
                        text = stringResource(id = R.string.search_for_a_word),
                        fontSize = 15.sp
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 19.5.sp
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 30.dp)
        ) {
            state.wordItem?.let { wordItem ->
                Spacer(modifier = modifier.height(20.dp))

                Text(
                    text = wordItem.word,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = wordItem.word,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = modifier.height(20.dp))
            }

            Box(
                modifier = modifier
                    .padding(top = 110.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 50.dp,
                            topEnd = 50.dp
                        )
                    )
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer.copy(0.7f)
                    )
            ) {
                if(state.isLoading) {
                    CircularProgressIndicator(
                        modifier = modifier
                            .size(80.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    state.wordItem?.let {
                        WordResult(wordItem = it)
                    }
                }
            }
        }
    }
}

@Composable
fun WordResult(
    modifier: Modifier = Modifier,
    wordItem: WordItem
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(wordItem.meanings.size){ index ->
            Meaning(
                meaning = wordItem.meanings[index],
                index = index
            )

            Spacer(modifier = modifier.height(32.dp))
        }

    }
}

@Composable
fun Meaning(
    modifier: Modifier = Modifier,
    meaning: Meaning,
    index: Int
) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Text(
                text = "${index + 1}. ${meaning.partOfSpeech}",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(0.4f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(
                        top = 2.dp, bottom = 4.dp,
                        start = 12.dp, end = 12.dp
                    )
            )

            if(meaning.definition?.definition?.isNotBlank() == true) {
                Spacer(modifier = modifier.height(8.dp))

                Row (
                    modifier = modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.definition),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = modifier.width(12.dp))

                    Text(
                        text = meaning.definition.definition,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            if(meaning.definition?.example?.isNotBlank() == true) {
                Spacer(modifier = modifier.height(8.dp))

                Row (
                    modifier = modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.example),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = modifier.width(12.dp))

                    Text(
                        text = meaning.definition.example,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun LexiconPreview() {
    LexiconTheme {
        LexiconScreen(
            state = MainState(),
            onEvent = {}
        )
    }
}