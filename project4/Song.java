public class Song{
    // TODO Task 2
    // define any fields needed
    // to represent a song
    // and the getters, setters and constructors
    
    int date;
    int listenedToNTimes;
    int likedLTimes;
    int popularity;
    String name;
    
    public Song (int date, int listenedToNTimes, int likedLTimes, int popularity, String name) {
        this.date = date;
        this.listenedToNTimes = listenedToNTimes;
        this.likedLTimes = likedLTimes;
        this.popularity = popularity;
        this.name = name;
    }
}
