package edu.neu.madscourse.numad22su_haowenxue;

public class ALink {

    private final String linkName;
    private final String linkURL;

    public ALink(String linkName, String linkURL){
        this.linkName = linkName;
        this.linkURL = linkURL;
    }

    public String getLinkName() {return linkName; }
    public String getLinkURL()  {return linkURL;  }
}
