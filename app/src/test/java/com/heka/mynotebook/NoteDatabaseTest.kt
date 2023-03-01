package com.heka.mynotebook






import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest : TestCase(){
    private lateinit var notesDao: NotesDao
    private lateinit var db: NoteDatabase

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java
        ).build()
        notesDao = db.getNotesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeNoteSuccessfully() = runBlocking {
        val  note = Note("note title","note description","0103 time stamp")
    //todo: fix error

        // var notesBefore = notesDao.getAllNotes().blockingObserve()
        // notesDao.insert(note)
        // var notesAfter = notesDao.getAllNotes().blockingObserve()

    }
}

private fun <T> LiveData<T>.blockingObserve(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { t ->
        value = t
        latch.countDown()
    }
    observeForever(observer)

    latch.await(2, TimeUnit.SECONDS)
    return value
}


