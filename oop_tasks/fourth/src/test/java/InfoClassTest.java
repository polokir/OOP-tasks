import org.example.InfoClass;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InfoClassTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void printInfoHuman(){
        InfoClass infoClass = new InfoClass("org.example.classes.Human");
        infoClass.showClassInfo();
        Assert.assertEquals("The class is from org.example.classes.Human\n" +
                "The modifiers of the class: public abstract interface\n" +
                "It doesn't extend any class.\n" +
                "It does not implement any interfaces.\n" +
                "No fields in this class. \n" +
                "Methods of this class: \n" +
                "   public abstract void say\n" +
                "      no parameters\n" +
                "   public abstract void eat\n" +
                "      no parameters\n" +
                "--------------------------------------------------\n", outContent.toString());
    }

    @Test
    public void printInfoTeacher(){
        InfoClass infoClass = new InfoClass("org.example.classes.Teacher");
        infoClass.showClassInfo();
        Assert.assertEquals("The class is from org.example.classes.Teacher\n" +
                "The modifiers of the class: public\n" +
                "It extends: java.lang.Object\n" +
                "It implements these interfaces: \n" +
                "   public abstract interface org.example.classes.Human\n" +
                "Fields of this class: \n" +
                "   private int age\n" +
                "   private java.lang.String name\n" +
                "Methods of this class: \n" +
                "   public void say\n" +
                "      no parameters\n" +
                "   public void eat\n" +
                "      no parameters\n" +
                "   public int countAge\n" +
                "      no parameters\n" +
                "--------------------------------------------------\n", outContent.toString());
    }

    @Test
    public void printInfoLecturer(){
        InfoClass infoClass = new InfoClass("org.example.classes.Lecturer");
        infoClass.showClassInfo();
        Assert.assertEquals("The class is from org.example.classes.Lecturer\n" +
                "The modifiers of the class: public\n" +
                "It extends: org.example.classes.Teacher\n" +
                "It does not implement any interfaces.\n" +
                "Fields of this class: \n" +
                "   private java.lang.String workPlace\n" +
                "   private java.lang.String name\n" +
                "   private int age\n" +
                "Methods of this class: \n" +
                "   public void learn\n" +
                "      no parameters\n" +
                "--------------------------------------------------\n", outContent.toString());
    }

}