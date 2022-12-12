import org.example.CustomClassLoader;
import org.junit.Assert;
import org.junit.Test;

public class CustomClassLoaderTest {

    @Test
    public void checkLoadingClass(){
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class foundClass = customClassLoader.findClass("org.example.classes.Human");
        Assert.assertEquals(foundClass.getName(), "org.example.classes.Human");
    }

    @Test
    public void checkSelfClass(){
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class foundClass = customClassLoader.findClass("org.example.CustomClassLoader");
        Assert.assertEquals(foundClass.getName(), "org.example.CustomClassLoader");
    }

}