package di

import contacts.data.ContactDataSourceImpl
import contacts.domain.ContactDataSource
import core.data.DatabaseDriverFactory
import core.data.ImageStorage

actual class AppModule {
    actual val contactDataSource: ContactDataSource by lazy {
        ContactDataSourceImpl(
            driver = DatabaseDriverFactory().createDriver(),
            imageStorage = ImageStorage()
        )
    }
}