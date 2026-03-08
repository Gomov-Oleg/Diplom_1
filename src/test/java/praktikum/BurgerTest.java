package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    Burger burger;

    @Mock
    Bun bun;

    @Mock
    Ingredient ingredient1;

    @Mock
    Ingredient ingredient2;

    // Перед выполнением каждого теста создаём бургер
    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsTest() {
        // Выполняем метод, который устанавливает булочку для бургера
        burger.setBuns(bun);
        // Проверяем, что булочка, переданная в методе, установилась на бургер.
        assertEquals("Булочка не та, которую ожидаем", bun, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        // Выполняем метод, который добавляет ингредиент к бургеру
        burger.addIngredient(ingredient1);
        // Проверяем, что ингредиент, переданный в методе, добавился к бургеру
        assertEquals("Необходимый ингредиент не добавлен", List.of(ingredient1), burger.ingredients);
    }

    @Test
    public void removeIngredientTest() {
        // Добавляем ингредиент к бургеру
        burger.ingredients.add(ingredient1);
        // Выполняем метод, который удаляет ингредиент
        burger.removeIngredient(0);
        // Проверяем, что после выполнения метода список ингредиентов пуст
        assertTrue("Ингредиент не удалён", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientTest() { // Тест проверяет, что ингредиенты могут перемещаться
        // Добавляем ингредиенты к бургеру
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);
        // Выполняем метод, который перемещает ингредиенты (в данном случае меняет их местами)
        burger.moveIngredient(1, 0);
        // Проверяем, что после выполнения метода ингредиент, который был в конце списка, стал в начале
        assertEquals("Поменять местами ингредиенты не удалось", ingredient2, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientAfterMoveTest() { // Тест проверяет, что при перемещении "лишний" ингредиент удаляется
        // Добавляем ингредиенты к бургеру
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);
        // Выполняем метод, который перемещает ингредиенты (он же и удаляет "продублированный" ингредиент)
        burger.moveIngredient(1, 0);
        // Проверяем, что после выполнения метода в списке остаётся 2 ингредиента (в данном случае)
        assertEquals("Количество ингредиентов не совпадает", 2, burger.ingredients.size());
    }

    @Test
    public void getReceiptTest() {
        // Создаём переменные для теста (в случае необходимости, их можно будет легко заменить)
        String ingredient1Type = "filling";
        String ingredient2Type = "sauce";
        String bunName = "Галактическая";
        String ingredient1Name = "Котлета с Юпитера";
        String ingredient2Name = "Сосиска с Сатурна";
        float price = 150;
        String finalPrice = String.format("%f", price);

        // Устанавливаем булочку для бургера
        burger.bun = bun;
        // Добавляем ингредиенты к бургеру
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);

        // Создаём стабы, которые будут использовать переменные, описанные выше
        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(ingredient1.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(ingredient2.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient1.getName()).thenReturn(ingredient1Name);
        Mockito.when(ingredient2.getName()).thenReturn(ingredient2Name);
        Mockito.when(burger.getPrice()).thenReturn(price);

        // Создаём ожидаемый результат
        String expected = "(==== " + bunName + " ====)\r\n"
                + "= " + ingredient1Type + " " + ingredient1Name + " =\r\n"
                + "= " + ingredient2Type + " " + ingredient2Name + " =\r\n"
                + "(==== " + bunName + " ====)\r\n"
                + "\r\nPrice: " + finalPrice + "\r\n";

        // Фактический результат отражаем в переменной actual
        String actual = burger.getReceipt();
        // Сверяем ожидаемый и фактические результаты
        assertEquals("Ожидаемый и фактический результаты не совпадают", expected, actual);
    }
}