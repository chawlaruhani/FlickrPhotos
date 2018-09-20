package test.verizon.com.flickrphotos.Data;

import android.databinding.BaseObservable;
import android.graphics.Bitmap;


public class PhotoObject extends BaseObservable{

    String id;
    String owner;
    String server;
    String secret;
    int farm;
    String title;
    int isPublic;
    int isFriend;
    int isFamily;
    Bitmap image;

    public PhotoObject(String id, String owner, String server, int farm, String title, int isPublic, int isFriend, int isFamily, String secret) {
        this.id = id;
        this.owner = owner;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.isPublic = isPublic;
        this.isFriend = isFriend;
        this.isFamily = isFamily;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int isPublic() {
        return isPublic;
    }

    public void setPublic(int aPublic) {
        isPublic = aPublic;
    }

    public int isFriend() {
        return isFriend;
    }

    public void setFriend(int friend) {
        isFriend = friend;
    }

    public int isFamily() {
        return isFamily;
    }

    public void setFamily(int family) {
        isFamily = family;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
       // notifyPropertyChanged(BR.image);

    }

}
