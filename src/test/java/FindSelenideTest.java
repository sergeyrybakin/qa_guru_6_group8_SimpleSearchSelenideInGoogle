import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class FindSelenideTest
{
    @Disabled
    @Test
    void findSelenideInGoogle() {
        Selenide.open("https://google.com");
        $("[name=q]").click();
        $("[name=q]").val("selenide").submit();
        $$("#res .g").shouldHave(sizeGreaterThan(1));
        $("#res .g").shouldHave(text("selenide.org"));
    }

    @Disabled
    @Test
    void findSelenideInYa() {
        Selenide.open("https://ya.ru");
        $("#text").val("Selenide").submit();
        $$("#search-result li").shouldHave(sizeGreaterThan(1));
        $("#search-result li").shouldHave(text("selenide.org"));
        screenshot("scrShot.jpg");
    }

    @Test
    void findNamesOnSites() {
        try (Scanner scanner = new Scanner(new File("src/test/resources/sites.txt")))
        {
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                String splittedStr[] = line.split(";");
                System.out.println(
                        "###Site: " + splittedStr[0] + "    Search locator: " + splittedStr[1] + "    Result locator: "
                                + splittedStr[2]);
                //open URL in browser
                Selenide.open(splittedStr[0]);

                try (Scanner scannerNames = new Scanner(new File("src/test/resources/names.txt")))
                {
                    while (scannerNames.hasNext())
                    {
                        String searchedName = scannerNames.nextLine();
                        System.out.println("######Name: " + searchedName);
                        //type the name into search line and press <Enter>
                        $(splittedStr[1]).val(searchedName).submit();
                        if($$(splittedStr[2]).size()>0) { //something is found
                            String screenShotFileName = "screenshots/" + splittedStr[0] + "_" + searchedName + ".jpg";
                            screenshot(screenShotFileName);
                            sleep(1000);
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
