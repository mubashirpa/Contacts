package di

import android.content.Context
import contacts.data.ContactDataSourceImpl
import contacts.domain.ContactDataSource
import core.data.DatabaseDriverFactory
import core.data.ImageStorage

actual class AppModule(
    private val context: Context
) {
    actual val contactDataSource: ContactDataSource by lazy {
        ContactDataSourceImpl(
            driver = DatabaseDriverFactory(context).createDriver(),
            imageStorage = ImageStorage(context)
        )
    }
}