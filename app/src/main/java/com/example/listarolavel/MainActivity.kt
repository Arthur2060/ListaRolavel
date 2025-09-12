package com.example.listarolavel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listarolavel.data.Aluno
import com.example.listarolavel.data.DataResource
import com.example.listarolavel.ui.theme.ListaRolavelTheme
import com.example.listarolavel.ui.theme.backgroundLight
import com.example.woof.ui.theme.Shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaRolavelTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = { TopBar() }
                ) { innerPadding ->
                    ListaRolavelApp(modifier = Modifier.padding(innerPadding) )
                }
            }
        }
    }
}
@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ListaRolavelPreviewDarkTheme() {
    ListaRolavelTheme(darkTheme = true) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = { TopBar() }
            ) { innerPadding ->
            ListaRolavelApp(
                modifier = Modifier
                    .padding(innerPadding)

            )

        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ListaRolavelPreview() {
    ListaRolavelTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = { TopBar() }
            ) { innerPadding ->
            ListaRolavelApp(
                modifier = Modifier
                    .padding(innerPadding)

            )

        }
    }
}
@Composable
fun ListaRolavelApp(modifier: Modifier = Modifier) {
    ListaDeAlunos(
        modifier = modifier,
        listaDeAlunos = DataResource().loadAlunos()
    )
}
@Composable
fun CardAluno(modifier: Modifier = Modifier,
              aluno: Aluno) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = Shapes.small,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.foto_do_aluno),
            )
            Column (
                modifier = modifier,
            ) {

                Text(
                    modifier = modifier,
                    text = aluno.nome,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = modifier,
                    text = aluno.curso,
                    fontSize = 11.sp
                )
            }
        }

    }
}
@Composable
fun ListaDeAlunos(modifier: Modifier = Modifier, listaDeAlunos: List<Aluno>) {
    LazyColumn(
        modifier = modifier,
    ) {
        for (aluno in listaDeAlunos) {
            item {
                CardAluno(aluno = aluno)
            }
        }
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundLight),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
    }
}