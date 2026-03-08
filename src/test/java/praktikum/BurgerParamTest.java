package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerParamTest {

    private final float bunPrice;
    private final float ingredient1Price;
    private final float ingredient2Price;

    public BurgerParamTest(float bunPrice, float ingredient1Price, float ingredient2Price) {
        this.bunPrice = bunPrice;
        this.ingredient1Price = ingredient1Price;
        this.ingredient2Price = ingredient2Price;
    }

    @Mock
    Bun bun;

    @Mock
    Ingredient ingredient1;

    @Mock
    Ingredient ingredient2;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Parameterized.Parameters(name = "price = {0}")
    public static Object[][] getPriceBurger(){
        return new Object[][]{
                {1f, 2f, 3f},
                {10f, 20f, 30f},
                {100f, 200f, 300f},
                {1000f, 2000f, 3000f},
                {100.5f, 200.5f, 300.5f}
        };
    }

    @Test
    public void getPriceTest(){
        // Создаём бургер
        Burger burger = new Burger();
        // Устанавливаем булочку для бургера
        burger.bun = bun;
        // Добавляем ингредиенты для бургера
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);

        // Создаём стабы, возвращающие цену булочки и каждого ингредиента
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(ingredient1.getPrice()).thenReturn(ingredient1Price);
        Mockito.when(ingredient2.getPrice()).thenReturn(ingredient2Price);

        // Создаём ожидаемый результат
        float expected = bunPrice * 2 + ingredient1Price + ingredient2Price;
        // Фактический результат отражаем в переменной actual
        float actual = burger.getPrice();
        // Сверяем ожидаемый и фактический результаты
        assertEquals("Ожидаемый и фактический результаты не совпадают", expected, actual, 0);
    }
}
