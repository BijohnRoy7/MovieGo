package invenz.movie.go.moviego1.models;

public class Catagory {

    private String catagoryName;
    private int catagoryImage;

    public Catagory() {
    }

    public Catagory(String catagoryName, int catagoryImage) {
        this.catagoryName = catagoryName;
        this.catagoryImage = catagoryImage;
    }


    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public int getCatagoryImage() {
        return catagoryImage;
    }

    public void setCatagoryImage(int catagoryImage) {
        this.catagoryImage = catagoryImage;
    }
}
