import java.io.Serializable;

public class Verb implements Serializable {
    private String value;
    private String time;
    private int knowlage;
    private String translation;

    public Verb(String value, String time, int knowlage, String translation) {
        this.value = value;
        this.time = time;
        this.knowlage = knowlage;
        this.translation = translation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void DecKnowlage(){
        knowlage--;
    }

    public void IncKnowlage(){
        knowlage++;
    }

    public int getKnowlage() {
        return knowlage;
    }

    public void setKnowlage(int knowlage) {
        this.knowlage = knowlage;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "Verb{" +
                "value='" + value + '\'' +
                ", time='" + time + '\'' +
                ", knowlage=" + knowlage +
                ", translation='" + translation + '\'' +
                '}';
    }
}
