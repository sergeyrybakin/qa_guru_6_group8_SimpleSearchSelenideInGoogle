import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FindSelenideTest
{
    @Test
    void findSelenideInGoogle() {
        Selenide.open("https://google.com");
        $("[name=q]").click();
        $("[name=q]").val("selenide").submit();
        $$("#res .g").shouldHave(sizeGreaterThan(1));
        $("#res .g").shouldHave(text("selenide.org"));
    }
}
