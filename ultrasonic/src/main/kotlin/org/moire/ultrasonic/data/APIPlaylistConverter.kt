// Converts Playlist entity from [org.moire.ultrasonic.api.subsonic.SubsonicAPIClient]
// to app domain entities.
@file:JvmName("APIPlaylistConverter")
package org.moire.ultrasonic.data

import org.moire.ultrasonic.domain.MusicDirectory
import org.moire.ultrasonic.domain.Playlist
import java.text.SimpleDateFormat
import kotlin.LazyThreadSafetyMode.NONE
import org.moire.ultrasonic.api.subsonic.models.Playlist as APIPlaylist

internal val playlistDateFormat by lazy(NONE) { SimpleDateFormat.getInstance() }

fun APIPlaylist.toMusicDirectoryDomainEntity(): MusicDirectory = MusicDirectory().apply {
    name = this@toMusicDirectoryDomainEntity.name
    addAll(this@toMusicDirectoryDomainEntity.entriesList.map { it.toDomainEntity() })
}

fun APIPlaylist.toDomainEntity(): Playlist = Playlist(this.id.toString(), this.name, this.owner,
        this.comment, this.songCount.toString(),
        this.created?.let { playlistDateFormat.format(it.time) },
        public.toString())

fun List<APIPlaylist>.toDomainEntitiesList(): List<Playlist> = this.map { it.toDomainEntity() }
