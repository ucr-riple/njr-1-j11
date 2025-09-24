package mp1401.examples.misterx.model.gameitems.impl.collections;

import static org.fest.assertions.Assertions.assertThat;
import mp1401.examples.misterx.model.factory.GameItemFactory;
import mp1401.examples.misterx.model.factory.GameItemFactoryImpl;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.enums.DetectiveType;

import org.junit.Test;

public class GameItemListImplTest {

  private static final GameItemFactory FACTORY = GameItemFactoryImpl.getInstance();

  @Test
  public void test() {
    final GameItemListImpl<Detective> detectives = new GameItemListImpl<Detective>();
    detectives.add(FACTORY.createDetective(DetectiveType.BLUE));
    assertThat(detectives.size()).isEqualTo(1);
  }

}
