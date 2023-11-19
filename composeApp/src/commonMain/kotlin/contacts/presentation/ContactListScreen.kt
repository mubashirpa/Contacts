package contacts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import contacts.domain.Contact
import contacts.presentation.components.AddContactSheet
import contacts.presentation.components.ContactDetailSheet
import contacts.presentation.components.ContactListItem
import contacts.presentation.components.RecentlyAddedContacts
import core.presentation.ImagePicker

@Composable
fun ContactListScreen(
    state: ContactListState,
    newContact: Contact?,
    onEvent: (ContactListEvent) -> Unit,
    imagePicker: ImagePicker
) {
    imagePicker.registerPicker { imageBytes ->
        onEvent(ContactListEvent.OnPhotoPicked(imageBytes))
    }
    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ContactListEvent.OnAddNewContactClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.PersonAdd,
                    contentDescription = "Add contact"
                )
            }
        }
    ) { innerPadding ->
        val contentPadding = PaddingValues(
            start = innerPadding.calculateStartPadding(layoutDirection),
            top = innerPadding.calculateTopPadding(),
            end = innerPadding.calculateEndPadding(layoutDirection),
            bottom = innerPadding.calculateBottomPadding() + 88.dp
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            item {
                RecentlyAddedContacts(
                    contacts = state.recentlyAddedContacts,
                    onClick = {
                        onEvent(ContactListEvent.SelectContact(it))
                    }
                )
            }
            item {
                Text(
                    text = "My contacts (${state.contacts.size})",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            items(state.contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    modifier = Modifier.clickable {
                        onEvent(ContactListEvent.SelectContact(contact))
                    }
                )
            }
        }
    }

    AddContactSheet(
        state = state,
        newContact = newContact,
        isOpen = state.isAddContactSheetOpen,
        onEvent = { event ->
            if (event is ContactListEvent.OnAddPhotoClicked) {
                imagePicker.pickImage()
            }
            onEvent(event)
        },
    )

    ContactDetailSheet(
        isOpen = state.isSelectedContactSheetOpen,
        selectedContact = state.selectedContact,
        onEvent = onEvent
    )
}