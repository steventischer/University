package Model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

//TODO Add ORM Notation

public class Teacher {
    private String teacherId;
    private String name;
    private String mail;
    private String office;


    //Note: empty Constructor is necessary for orm
    public Teacher(){
    }

    public Teacher(String name, String mail, String office) {
        this.teacherId = UUID.randomUUID().toString();
        this.name = name;
        this.mail = mail;
        this.office = office;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
