package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mente_libre_app.R
import com.example.mente_libre_app.data.remote.dto.chat.ChatMessageDto
import com.example.mente_libre_app.ui.viewmodel.ChatViewModel
import com.example.mente_libre_app.ui.components.PetDrawable
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    navController: NavHostController,
    chatViewModel: ChatViewModel,
    userId: Long,
    petId: Long?,
    petAvatarKey: String
) {
    val rawMessages by chatViewModel.messages.collectAsState()

    // Expandimos cada ChatMessageDto en 2 mensajes separados (user + pet)
    val messages = rawMessages.flatMap { msg ->
        listOfNotNull(
            msg.userMessage?.let {
                ChatMessageDto(msg.id, it, null, msg.timestamp)
            },
            msg.petResponse?.let {
                ChatMessageDto(msg.id, null, it, msg.timestamp)
            }
        )
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var text by remember { mutableStateOf("") }
    val avatarRes = PetDrawable(petAvatarKey)

    LaunchedEffect(Unit) {
        chatViewModel.loadHistory(userId)
    }

    LaunchedEffect(messages.size) {
        scope.launch {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF1F3))
    ) {
        ChatHeader(navController)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            state = listState
        ) {
            items(messages) { msg ->
                ChatBubble(
                    msg = msg,
                    isUser = msg.petResponse == null,
                    avatarRes = avatarRes
                )
            }
        }

        ChatInputField(
            text = text,
            onTextChange = { text = it },
            onSend = {
                if (text.isNotBlank()) {
                    chatViewModel.sendMessage(userId, petId, text)
                    text = ""
                }
            }
        )
    }
}

@Composable
fun ChatHeader(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFC7DD))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Volver",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = "Tu compañera 💗",
            color = Color.White,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.source_serif_pro_bold))
        )
    }
}

@Composable
fun ChatBubble(msg: ChatMessageDto, isUser: Boolean, avatarRes: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Image(
                painter = painterResource(avatarRes),
                contentDescription = "Avatar mascota",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .background(
                    color = if (isUser) Color(0xFFFFD4EB) else Color.White,
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(12.dp)
                .widthIn(max = 260.dp)
        ) {
            Text(
                text = msg.userMessage ?: msg.petResponse.orEmpty(),
                color = Color(0xFF7A2C54),
                fontSize = 16.sp
            )
        }

        if (isUser) Spacer(Modifier.width(8.dp))
    }

    Spacer(Modifier.height(10.dp))
}

@Composable
fun ChatInputField(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text("Escribe un mensaje...") },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(Modifier.width(10.dp))

        IconButton(
            onClick = onSend,
            modifier = Modifier
                .size(52.dp)
                .background(Color(0xFFFF8AC0), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Enviar",
                tint = Color.White
            )
        }
    }
}
