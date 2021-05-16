import com.uec.con1.Con1App;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Con1App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Con1Test {

    @Test
    void test() {
        String a = "hello";
        assertEquals("hello", a);
    }
}