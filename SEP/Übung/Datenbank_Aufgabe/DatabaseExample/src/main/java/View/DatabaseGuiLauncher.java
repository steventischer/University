package View;

import Model.Student;
import Model.Teacher;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.table.TableUtils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseGuiLauncher extends Application {

    private String db_url = "jdbc:mysql://localhost/studentdatabase";
    private String user_name = "user";
    private String password = "123";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createInitScene(stage));
        stage.setScene(scene);
        stage.show();
    }


    private List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(db_url,user_name,password)) {
            System.out.print("Connected");


            String query = "SELECT * FROM Student";

            Statement getData = con.createStatement();
            ResultSet RS = getData.executeQuery(query);
            while(RS.next()) {
                String test = RS.getString("studentID") + ","  + RS.getString("name") + "," + RS.getString("matrNumber") + "," + RS.getString("degree");

                Student s = new Student(RS.getString("studentID"), RS.getString("name"), RS.getString("matrNumber"), RS.getString("degree"));
                students.add(s);

            }
            /*
            System.out.println(query);
            Statement Insert = con.createStatement();
            Insert.execute(query);

             */
            getData.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //TODO get DB-Connection
        //TODO create and execute SQL-Query to select all students from table "student"
        //TODO return them as list


        return students;
    }

    private void insertStudent(Student s){
        //TODO get DB-Connection
        try(Connection con = DriverManager.getConnection(db_url,user_name,password)) {
            String query = "INSERT INTO student(name,matrNumber,degree) VALUES ('" + s.getName() + "','" + s.getMatrNumber() + "','" + s.getDegree() + "')";
            System.out.println(query);
            Statement Insert = con.createStatement();
            Insert.execute(query);
            Insert.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //TODO create and execute SQL-Statement to insert Student into DB
    }

    private List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        String url = "jdbc:mysql://localhost/studentdatabase";
        String user = "user";
        String password = "123";

        try(JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(url,user,password)) {
            TableUtils.createTable(connectionSource, Teacher.class);

            Dao<Teacher, Integer> TeacherDao = DaoManager.createDao(connectionSource, Teacher.class);

        }

        //TODO get DB-Connection
        //TODO create DAO (Data Access Object) to read all teachers from table "Teacher"
        //TODO return list of all teachers

        return  teachers;
    }

    private void insertTeacher(Teacher t){
        //TODO get DB-Connection
        //TODO create DAO (Data Access Object) to read all teachers from table "teacher"
        //TODO return list of all teachers
    }

    private VBox createInitScene(Stage stage) {
        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        TableView table = new TableView();
        table.setEditable(false);

        final HBox insertArea = new HBox();
        insertArea.setAlignment(Pos.CENTER);
        TextField x = new TextField();
        TextField y = new TextField();
        TextField z = new TextField();
        Button insertBttn = new Button("insert");
        insertArea.getChildren().addAll(x,y,z,insertBttn);

        Button b1 = new Button("Students");
        b1.setOnAction(actionEvent -> {
            table.getItems().clear();

            TableColumn a = new TableColumn("studentId");
            a.setCellValueFactory(new PropertyValueFactory<>("studentId"));

            TableColumn b = new TableColumn("name");
            b.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn c = new TableColumn("matrNumber");
            c.setCellValueFactory(new PropertyValueFactory<>("matrNumber"));

            TableColumn d = new TableColumn("degree");
            d.setCellValueFactory(new PropertyValueFactory<>("degree"));


            table.getColumns().clear();
            table.getColumns().addAll(a,b,c,d);

            table.getItems().addAll(getAllStudents());


            x.setPromptText("name");
            y.setPromptText("matrNumber");
            z.setPromptText("degree");


            insertBttn.setOnAction(actionEvent1 -> {
                if (x.getText() != "" && y.getText() != "" && z.getText() != ""){
                    Student s = new Student(x.getText(), y.getText(),z.getText());
                    insertStudent(s);
                    x.clear();
                    y.clear();
                    z.clear();
                    table.getItems().clear();
                    table.getItems().addAll(getAllStudents());
                }
            });
        });

        Button b2 = new Button("Teachers");
        b2.setOnAction(actionEvent -> {
            table.getItems().clear();

            TableColumn a = new TableColumn("teacherId");
            a.setCellValueFactory(new PropertyValueFactory<>("teacherId"));

            TableColumn b = new TableColumn("name");
            b.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn c = new TableColumn("mail");
            c.setCellValueFactory(new PropertyValueFactory<>("mail"));

            TableColumn d = new TableColumn("office");
            d.setCellValueFactory(new PropertyValueFactory<>("office"));

            x.setPromptText("name");
            y.setPromptText("mail");
            z.setPromptText("office");


            insertBttn.setOnAction(actionEvent1 -> {
                if (x.getText() != "" && y.getText() != "" && z.getText() != ""){
                    Teacher t = new Teacher(x.getText(), y.getText(),z.getText());
                    insertTeacher(t);
                    x.clear();
                    y.clear();
                    z.clear();
                    table.getItems().clear();
                    table.getItems().addAll(getAllTeachers());

                }
            });

            table.getColumns().clear();
            table.getColumns().addAll(a,b,c,d);

            table.getItems().addAll(getAllTeachers());
        });

        final HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().setAll(b1,b2);

        vbox.getChildren().setAll(buttons, table, insertArea);

        return vbox;
    }

}
