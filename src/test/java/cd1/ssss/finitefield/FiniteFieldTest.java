package cd1.ssss.finitefield;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FiniteFieldTest {

    @Resource(name = "extensionField256")
    FiniteField finiteField;

    /**
     * get result of finite field operation from online finite field calculator
     * <a href="http://www.ece.unb.ca/cgi-bin/tervo/calc2.pl">Galois Field GF(2m) Calculator</a>
     *
     * @param a        first value
     * @param b        second value
     * @param operator one of + - * / operator
     * @return answer
     */
    private int onlineFiniteFieldCalculator(int a, int b, char operator) {
        int ans = -1;
        try {
            char f = switch (operator) {
                case '+' -> 'a';
                case '-' -> 's';
                case '*' -> 'm';
                case '/' -> 'd';
                default -> throw new IllegalStateException();
            };
            /*
             * the query parameter of http://www.ee.unb.ca/cgi-bin/tervo/calc2.pl:
             * num, den is two integer
             * f specify the type of calculator:
             * a = add; s = subtract; m = multiply; d = divide
             * p specify the incredible polynomial, in our project it is 100011011 : P[x] = x^8+x^4+x^3+x+1
             * and p=36 will set the incredible polynomial = x^8+x^4+x^3+x+1
             */

            String url = String.format("http://www.ee.unb.ca/cgi-bin/tervo/calc2.pl?num=%d&den=%d&f=%s&p=36", a, b, f);
            Connection web = Jsoup.connect(url);
            Document htmlDoc = Jsoup.parse(web.get().html());
            // get calculation result from htmlparser provided by Jsoup
            if (operator == '/') {
                Elements elements = htmlDoc.select("div > table > tbody > tr > td").select(".tdr");
                ans = Integer.parseInt(Objects.requireNonNull(elements.first()).html());
            } else {
                Elements elements = htmlDoc.select("div > table > tbody > tr > td");
                ans = Integer.parseInt(Objects.requireNonNull(elements.last()).html());
            }
            return ans;
        } catch (IOException e) {
            return ans;
        }
    }

    @Test
    void addTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 251; j < 256; j++) {
                assertEquals(finiteField.add(i, j), onlineFiniteFieldCalculator(i, j, '+'));
            }
        }
    }

    @Test
    void minusTest() {
        for (int i = 251; i < 256; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(finiteField.minus(i, j), onlineFiniteFieldCalculator(i, j, '-'));
            }
        }
    }

    @Test
    void multiplyTest() {
        for (int i = 50; i < 60; i += 2) {
            for (int j = 150; j < 160; j += 2) {
                assertEquals(finiteField.multiply(i, j), onlineFiniteFieldCalculator(i, j, '*'));
            }
        }
    }

    @Test
    void divisionTest() {
        for (int i = 200; i < 210; i += 2) {
            for (int j = 10; j < 20; j += 2) {
                assertEquals(finiteField.divide(i, j), onlineFiniteFieldCalculator(i, j, '/'));
            }
        }
    }

    @Test
    void combineTest() {
        Random random = ThreadLocalRandom.current();
        int randA = random.nextInt(256);
        int randB = random.nextInt(256);
        int randC = random.nextInt(256);
        assertEquals(finiteField.minus(finiteField.add(randA, randB), randB), randA);
        assertEquals(finiteField.minus(finiteField.add(randB, randC), randC), randB);
        assertEquals(finiteField.divide(finiteField.multiply(randA, randB), randB), randA);
        assertEquals(finiteField.divide(finiteField.multiply(randB, randC), randC), randB);
    }
}
