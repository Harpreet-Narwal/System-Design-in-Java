package Behavioural_Design_Patter.IteratorPatttern;

import java.util.*;

// Let's say we are building a Youtube Playlist system. We want to store a list of videos and print their titles one buy one. Let's look at the inital code setup;

// class Video{
//     String title;

//     public Video(String title){
//         this.title = title;
//     }

//     public String getTitle(){
//         return title;
//     }
// }

// class YoutubePlayList{
//     private List<Video> videos = new ArrayList<>();

//     public void addVideo(Video video){
//         videos.add(video);
//     }

//     public List<Video> getVideos(){
//         return videos;
//     }

// }

// public class Main {
//     public static void main(String[] args) {
//         YoutubePlayList playlist = new YoutubePlayList();
//         playlist.addVideo(new Video("LLD Tutorial"));
//         playlist.addVideo(new Video("System Design Basics"));

//         for(Video v : playlist.getVideos()){
//             System.out.println(v.getTitle());
//         }
//     }

    // Issues: There are several Design level concerns:

    // Exposes internal Structure
    // Tight coupling with underlying structure
    // No control over traversal
    // Difficult to support multiple independent traversals.
// }

// Solution:


// class Video{
//     private String title;

//     public Video(String title){
//         this.title = title;
//     }

//     public String getTitle(){
//         return title;
//     }
// }

// class YoutubePlaylist{
//     private List<Video> videos = new ArrayList<>();

//     public void addVideo(Video video){
//         videos.add(video);
//     }

//     public List<Video> getVideos(){
//         return videos;
//     }
// }

// interface PlaylistIterator{
//     boolean hasNext();
//     Video next();
// }


// class YoutubePlaylistIterator implements PlaylistIterator{
//     private List<Video> videos;
//     private int position;

//     public YoutubePlaylistIterator(List<Video> videos){
//         this.videos = videos;
//         this.position = 0;
//     }

//     @Override
//     public boolean hasNext(){
//         return position < videos.size();
//     }

//     @Override 
//     public Video next(){
//         return hasNext() ? videos.get(position++) : null;
//     }
// }


// public class Main{
//     public static void main(String[] args) {
//         YoutubePlaylist playlist = new YoutubePlaylist();
//         playlist.addVideo(new Video("LLD Tutorials"));
//         playlist.addVideo(new Video("System Design Basics"));

//         PlaylistIterator iterator = new YoutubePlaylistIterator(playlist.getVideos());

//         while(iterator.hasNext()){
//             System.out.println(iterator.next().getTitle());
//         }
//     }
//     // One Major Issue Still Remains...Even though we’ve abstracted the traversal logic into an iterator class, the client is still responsible for creating and using the iterator, which is not ideal. The goal of true encapsulation would be to hide even the creation of the iterator, something we’ll address now with a more refined approach in the next section


// }

import java.util.*;

// ========== Video class representing a single video ==========
class Video {
    private String title;

    public Video(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// ================ Playlist interface ================
// (acts as a contract for collections that are iterable) 
interface Playlist {
    // Method to return an iterator for the collection
    PlaylistIterator createIterator();
}

// ========== YouTubePlaylist class (Aggregate) ==========
// Implements Playlist to guarantee it provides an iterator
class YouTubePlaylist implements Playlist {
    private List<Video> videos = new ArrayList<>();

    // Method to add a video to the playlist
    public void addVideo(Video video) {
        videos.add(video);
    }

    // Instead of exposing the list, return an iterator
    @Override
    public PlaylistIterator createIterator() {
        return new YouTubePlaylistIterator(videos);
    }
}

// ========== Iterator interface (defines traversal contract) ==========
interface PlaylistIterator {
    boolean hasNext();   // Checks if more elements are left
    Video next();        // Returns the next element
}

// ========== Concrete Iterator class ==========
// Implements the actual logic for traversing the YouTubePlaylist
class YouTubePlaylistIterator implements PlaylistIterator {
    private List<Video> videos;
    private int position;

    // Constructor takes the collection to iterate over
    public YouTubePlaylistIterator(List<Video> videos) {
        this.videos = videos;
        this.position = 0;
    }

    // Check if more videos are left
    @Override
    public boolean hasNext() {
        return position < videos.size();
    }

    // Return the next video in the playlist
    @Override
    public Video next() {
        return hasNext() ? videos.get(position++) : null;
    }
}

// ========== Main method (Client code) ==========
public class Main {
    public static void main(String[] args) {
        // Create a playlist and add videos to it
        YouTubePlaylist playlist = new YouTubePlaylist();
        playlist.addVideo(new Video("LLD Tutorial"));
        playlist.addVideo(new Video("System Design Basics"));

        // Client simply asks for an iterator — no access to internal data structure
        PlaylistIterator iterator = playlist.createIterator();

        // Iterate through the playlist using the provided interface
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getTitle());
        }
    }
}
