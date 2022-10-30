package com.arnyminerz.webdavsync.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arnyminerz.webdavsync.R
import kotlinx.coroutines.Job

interface AddServerRequest {
    fun onAddServerRequest(hostname: String, ssl: Boolean, auth: Pair<String, String>?): Job
}

@Composable
@ExperimentalMaterial3Api
fun AddServerScreen(addServerRequest: AddServerRequest, onBackRequested: () -> Unit = {}) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.title_add_server)) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackRequested,
                    ) { Icon(Icons.Rounded.ChevronLeft, stringResource(R.string.image_desc_back)) }
                },
            )
        },
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(8.dp),
        ) {
            var host by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var useSsl by remember { mutableStateOf(false) }
            var useAuth by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = host,
                onValueChange = { host = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                label = { Text(stringResource(R.string.add_server_host_label)) },
                supportingText = { Text(stringResource(R.string.add_server_host_help)) },
                placeholder = { Text(stringResource(R.string.add_server_host_placeholder)) },
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = useSsl, onCheckedChange = { useSsl = it })
                Text(stringResource(R.string.add_server_ssl))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = useAuth, onCheckedChange = { useAuth = it })
                Text(stringResource(R.string.add_server_auth))
            }
            AnimatedVisibility(visible = useAuth, modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        label = { Text(stringResource(R.string.add_server_username)) },
                    )
                    // TODO: Hide/show password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        label = { Text(stringResource(R.string.add_server_password)) },
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
            ) {
                OutlinedButton(
                    onClick = {
                        addServerRequest.onAddServerRequest(host, useSsl, (username to password).takeIf { useAuth })
                    },
                ) {
                    Text(stringResource(R.string.add_server_add_server))
                }
            }
        }
    }
}
