package contacts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import contacts.domain.Contact
import contacts.presentation.ContactListEvent
import contacts.presentation.ContactListState
import core.presentation.BottomSheetFromWish

@Composable
fun AddContactSheet(
    state: ContactListState,
    newContact: Contact?,
    isOpen: Boolean,
    onEvent: (ContactListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetFromWish(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.height(64.dp).padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onEvent(ContactListEvent.DismissContact)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (newContact?.photoBytes == null) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(40))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable {
                            onEvent(ContactListEvent.OnAddPhotoClicked)
                        }
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            shape = RoundedCornerShape(40)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add photo",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(40.dp)
                    )
                }
            } else {
                ContactPhoto(
                    contact = newContact,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            onEvent(ContactListEvent.OnAddPhotoClicked)
                        }
                )
            }
            Spacer(Modifier.height(24.dp))
            ContactTextField(
                value = newContact?.firstName ?: "",
                placeholder = "First name",
                error = state.firstNameError
            ) {
                onEvent(ContactListEvent.OnFirstNameChanged(it))
            }
            Spacer(Modifier.height(16.dp))
            ContactTextField(
                value = newContact?.lastName ?: "",
                placeholder = "Last name",
                error = state.lastNameError
            ) {
                onEvent(ContactListEvent.OnLastNameChanged(it))
            }
            Spacer(Modifier.height(16.dp))
            ContactTextField(
                value = newContact?.email ?: "",
                placeholder = "Email",
                error = state.emailError
            ) {
                onEvent(ContactListEvent.OnEmailChanged(it))
            }
            Spacer(Modifier.height(16.dp))
            ContactTextField(
                value = newContact?.phoneNumber ?: "",
                placeholder = "Phone number",
                error = state.phoneNumberError
            ) {
                onEvent(ContactListEvent.OnPhoneNumberChanged(it))
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    onEvent(ContactListEvent.SaveContact)
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
private fun ContactTextField(
    value: String,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = placeholder)
        },
        isError = error != null,
        supportingText = if (error != null) {
            {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            null
        },
    )
}