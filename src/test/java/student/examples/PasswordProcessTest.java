package student.examples;

import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Listeners(PasswordTestListener.class)
public class PasswordProcessTest {
    //TODO: verify pass strength
    @BeforeTest
    public void setup() {

    }

    @DataProvider (name = "providePassword")
    public Iterator<String> providePasswords() throws FileNotFoundException {
        List<String> passwords = new ArrayList<>();
        try (
                InputStream is = Files.newInputStream(new File("C:\\Users\\denisa\\IdeaProjects\\ahoo-threads-test\\src\\test\\resources\\data.xlsx").toPath());
                Workbook workbook = StreamingReader.builder()
                        .rowCacheSize(100)
                        .bufferSize(4096)
                        .open(is)
        ){
            for (Sheet sheet : workbook){
                for (Row r : sheet) {
                    passwords.add(r.getCell(0).getStringCellValue());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passwords.iterator();
    }

    @Test(dataProvider = "providePassword")
    public void testPasswordStrength(String password) {
//  At least 8 characters long.
//  Contains both uppercase and lowercase letters.
//  Includes at least one numerical digit.
//  Contains at least one special character (e.g., @, #, $, etc.).
        Pattern pattern = Pattern.compile(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        Assert.assertTrue(matcher.matches());
    }

    @AfterClass
    public void createReports(ITestContext context) {
//        System.out.println("AAAA" + context.getSuite().getResults().size());
        context.getSuite().getResults().forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
    }
}
