package contacts.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import contacts.domain.Contact

@Composable
fun ContactListItem(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(text = "${contact.firstName} ${contact.lastName}")
        },
        modifier = modifier,
        leadingContent = {
            ContactPhoto(
                contact = contact,
                modifier = Modifier.size(48.dp)
            )
        }
    )
}