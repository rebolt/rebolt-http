import io.rebolt.http.HttpForm;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class Test_Common {
  @Test
  public void test_HttpForm() {
    final HttpForm httpForm = HttpForm.create().add("A", "a").add("B", "b");
    final String formString = httpForm.toFormString();
    final HttpForm httpForm2 = HttpForm.parse(formString);

    assertTrue(httpForm.equals(httpForm2));
  }
}
