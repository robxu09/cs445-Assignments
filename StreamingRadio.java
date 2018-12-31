package cs445.a4;

/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * ratings that users assign to songs.
 */
public interface StreamingRadio {

    /**
     * The abstract methods below are declared as void methods with no
     * parameters. You need to expand each declaration to specify a return type
     * and parameters, as necessary. You also need to include a detailed comment
     * for each abstract method describing its effect, its return value, any
     * corner cases that the client may need to consider, any exceptions the
     * method may throw (including a description of the circumstances under
     * which this will happen), and so on. You should include enough details
     * that a client could use this data structure without ever being surprised
     * or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new song to the system. If the song is already in the system
     * nothing happens. If the song is not currently in the system, add it to
     * a data structure of songs.
     *
     * @param theSong song that is to be added to the system
     * @throws NullPointerException if the song is null
     */
    public void addSong(Song theSong)
    throws NullPointerException;

    /**
     * Removes an existing song from the system. If the song is in the system,
     * the song is removed from the record as if it was never added. If the song
     * is not in the system, then this method will throw an IllegalArgumentException.
     *
     * @param theSong song that is to be removed from the system
     * @throws IllegalArgumentException if the song is not currently in the
     * system
     * @throws NullPointerException if the song is null
     */
    public void removeSong(Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Adds an existing song to the playlist for an existing radio station.
     * If the song is already in the station, nothing happens. If the song does not
     * exist in the system, then this method will throw an IllegalArgumentException.
     * If the song does exist in the system and does not exist in the existing radio
     * station's playlist, the song is added to its playlist.
     *
     * @param theSong song that is to be added to an existing station's playlist
     * @param theStation station that the song is to be added to
     * @throws IllegalArgumentException if the song does not exist
     * @throws NullPointerException if either the song or station is null
     */
    public void addToStation(Song theSong, Station theStation)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Removes a song from the playlist for a radio station. If the song does not
     * exist in the system or is not in the station's playlist, then this method will
     * throw an IllegalArgumentException. If the song is in the station's playlist, it
     * is removed from the playlist as if the song was never there.
     *
     * @param theSong song that is to be removed
     * @param theStation station from which the song is to be removed
     * @throws IllegalArgumentException if the song does not exist in the system
     * or the song is not in the station
     * @throws NullPointerException if either the song or the station is null
     */
    public void removeFromStation(Song theSong, Station theStation)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Sets a user's rating for a song, as a number of stars from 1 to 5. If this
     * user has rated this song already, update the previous rating to be the 
     * new rating. If the user does not currently have a rating for the song,
     * assign the user's rating of the song to be the rating. If the song is not
     * in the system or the rating is not between 1 and 5 inclusive, then this
     * method throws an IllegalArgumentException.
     *
     * @param theUser user whose rating for a song will be set
     * @param theSong song of which the user will give a rating
     * @param rating rating as a number of stars from 1 to 5 that the user will
     * give a song
     * @throws IllegalArgumentException if the song is not in the system or
     * the rating is not between 1 and 5 inclusive
     * @throws NullPointerException if either the user or the song is null
     */
    public void rateSong(User theUser, Song theSong, int rating)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Clears a user's rating on a song. If this user has rated this song and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this user has not rated this song,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theSong song from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * rating on record for the song
     * @throws NullPointerException if either the user or the song is null
     */
    public void clearRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Predicts the rating a user will assign to a song that they have not yet
     * rated, as a number of stars from 1 to 5. If the song is not in the
     * system or the user already has a rating for the song, this method will
     * throw an IllegalArgumentException. The method returns an integer
     * between 1 and 5 inclusive that represents a rating with that number of
     * stars.
     *
     * @param theUser user whose rating for a song will be predicted
     * @param theSong song for which a predicted rating will be given for a
     * user
     * @throws IllegalArgumentException if the user already has a rating for
     * the song or the song does not exist in the system
     * @throws NullPointerException if either the user or the song is null
     * @return the rating as an integer number of stars between 1 and 5 inclusive
     */
    public int predictRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Suggests a song for a user that they are predicted to like. This method
     * returns a song that the user does not have a rating for. If there are
     * no songs in the system that the user does not have a rating for, this
     * method throws a NoAvailableSongsException.
     *
     * @param theUser user of whom a song will be suggested
     * @throws NoAvailableSongsException if there are no songs in the system
     * that the user does not have a rating for
     * @throws NullPointerException if the user is null
     * @return a song in the system that the user does not have a rating for
     */
    public Song suggestSong(User theUser)
    throws NoAvailableSongsException, NullPointerException;

}

