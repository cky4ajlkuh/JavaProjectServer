import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Sosiska {
    int number;
    char value;

    public Sosiska() {

    }

    Sosiska(int number, char value) {
        this.number = number;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Номер " + number + "/n" + "Значение " + value;
    }
}
