package com.example.listarolavel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.listarolavel.ui.theme.ListaRolavelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaRolavelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaRolavelApp(modifier = Modifier.padding(innerPadding) )
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ListaRolavelApp(modifier: Modifier = Modifier) {
    ListaRolavelTheme {
        CardAluno(modifier = modifier,
            aluno = Aluno(
                nome = R.string.arthur,
                curso = R.string.mecatronicadev,
                imagen = R.drawable.ic_launcher_foreground
            )
        )
    }
}

@Composable
fun CardAluno(modifier: Modifier = Modifier,
              aluno: Aluno) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(aluno.imagen),
                contentDescription = stringResource(R.string.foto_do_aluno),
            )
            Column (
                modifier = modifier,
            ) {

                Text(
                    modifier = modifier,
                    text = stringResource(aluno.nome),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = modifier,
                    text = stringResource(aluno.curso),
                    fontSize = 11.sp
                )
            }
        }

    }
}