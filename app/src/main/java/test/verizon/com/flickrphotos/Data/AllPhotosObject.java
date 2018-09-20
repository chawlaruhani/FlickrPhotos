package test.verizon.com.flickrphotos.Data;

import java.util.ArrayList;
import java.util.List;

public class AllPhotosObject {
    int page;
    int pages;
    int perPage;
    String total;
    List<PhotoObject> photos = new ArrayList<PhotoObject>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<PhotoObject> getPhotos() {
        return photos;
    }

    public void putPhoto(PhotoObject photo) {
        this.photos.add(photo);
    }

}
