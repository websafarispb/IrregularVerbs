import java.io.Serializable;
public class IrrVerb implements Serializable {
    private Verb V1;
    private Verb V2;
    private Verb V3;
    private String translation;
    private int knowlage;

    public IrrVerb(String v1, String v2, String v3, String translation) {
        V1 = new Verb(v1,"V1",0,translation);
        V2 = new Verb(v2,"V2",0,translation);
        V3 = new Verb(v3,"V3",0,translation);
        this.translation = translation;
        this.knowlage = 0;
    }
    public String getTime(int time){
        switch (time){
            case 0 : return V1.getValue();
            case 1 : return V2.getValue();
            case 2 : return V3.getValue();
            default: return "ERROR";

        }

    }

    public int getKnowlage() {
        return knowlage;
    }

    public void setKnowlage(int knowlage) {
        this.knowlage = knowlage;
    }

    public void IncKnowlage() {
        this.knowlage++;
    }

    public void DecKnowlage() {
        this.knowlage--;
    }

    public Verb getV1() {
        return V1;
    }

    public Verb getV2() {
        return V2;
    }

    public Verb getV3() {
        return V3;
    }

    public String getTranslation() {
        return translation;
    }

    public void setV1(String v1) { V1.setValue(v1);}

    public void setV2(String v2) {
        V2.setValue(v2);
    }

    public void setV3(String v3) {
        V3.setValue(v3);
    }

    public void setTranslation(String translation) {
        this.translation = translation;
        this.V1.setTranslation(translation);
        this.V2.setTranslation(translation);
        this.V3.setTranslation(translation);
    }

    @Override
    public String toString() {
        return V1.getValue() + " | " + V1.getKnowlage() + " | " + V2.getValue() + " | " + V2.getKnowlage() + " | " + V3.getValue() + " | " + V3.getKnowlage() + " | "+ translation + " | " + knowlage;
    }
}
