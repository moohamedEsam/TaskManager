package com.example.taskmanager.data.local.room

import androidx.room.*
import com.example.taskmanager.data.models.reminder.ReminderEntity
import com.example.taskmanager.data.models.reminder.ReminderWithTagCrossRef
import com.example.taskmanager.data.models.reminder.ReminderWithTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Transaction
    @Query("select * from reminder order by isPinned, date, createdAt desc")
    fun getReminders(): Flow<List<ReminderWithTagEntity>>

    @Transaction
    @Query("select * from reminder where reminderId = :id")
    fun getReminderById(id: String): Flow<ReminderWithTagEntity?>

    @Insert
    suspend fun insert(reminderEntity: ReminderEntity)

    @Update
    suspend fun update(reminderEntity: ReminderEntity)


    @Delete
    suspend fun delete(reminderEntity: ReminderEntity)

    @Insert
    suspend fun addReminderTag(reminderWithTagCrossRef: ReminderWithTagCrossRef)


    @Query("delete from reminderWithTagCrossRef where reminderId = :reminderId")
    suspend fun deleteReminderTags(reminderId: String)

    @Query("update reminder set isPinned=:isPinned where reminderId=:reminderId")
    suspend fun markReminderAsPinned(reminderId: String, isPinned: Boolean)

    @Query("update reminder set isArchived=:isArchived where reminderId=:reminderId")
    suspend fun markReminderAsArchived(reminderId: String, isArchived: Boolean)

    @Query("update reminder set isDeleted=:isDeleted where reminderId=:reminderId")
    suspend fun markReminderAsDeleted(reminderId: String, isDeleted: Boolean)

    @Query("update reminder set isFavorite=:isFavorite where reminderId=:reminderId")
    suspend fun markReminderAsFavorite(reminderId: String, isFavorite: Boolean)

}